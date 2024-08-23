package main;

import basic.Edge;
import basic.Point2D;
import heuristics.Heuristic;
import heuristics.fromConvexHull.FromCH;
import heuristics.fromPoints.FromPoints;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import shapes.Polygon;
import shapes.Shapes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utils.utilMethods.rndNodesGenerator2D;

public class Statistics {

  /**
   * for each polygon are extracted 10 samples with 10 points, 10 with 30, 10 with 50
   * @param heuristics list of heuristics to test
   */
  public static void iterationStatistics(List<Heuristic> heuristics){
    // init the map
    Map<Heuristic, Double> jaccardIndexes = new HashMap<>();
    Map<Heuristic, Long> timing = new HashMap<>();

    Map<Heuristic, Integer> exceptions = new HashMap<>();

    List<Polygon> polygons = List.of(
        Shapes.RECTANGLE.getPolygon(),
        Shapes.SQUARE.getPolygon(),
        Shapes.TRIANGLE.getPolygon()
    );

    int j = 1;
    it:
    for (Polygon p : polygons) {
      // generate samples
      List<List<Point2D>> samples = polygonSampleList(p);
      for (List<Point2D> sample : samples){
        System.out.println("\n-------------- iteration : " + j+"\n");
        j++;

        System.out.println("jarvis initing");
        JarvisMarch jm = new JarvisMarch(sample);
        System.out.println("jarvis calculating");
        List<Edge> convexHull = jm.getHullEdges();
        System.out.println("jarvis done");

        for (Heuristic h: heuristics) {
          try {
            // init the heuristic
            if (h instanceof FromCH) ((FromCH) h).newData(convexHull);
            if (h instanceof FromPoints) ((FromPoints) h).newData(getG(jm.getHullNodes()), sample);

            long startTime = System.nanoTime();
            System.out.println("solving " +h.getClass().getName()+ "\n...");
            h.calcConvexHull(p.getEdgeNumber());
            long endTime = System.nanoTime();
            System.out.println("time "+h.getClass().getCanonicalName()+": " + (endTime - startTime) + "\n");
            // Calcolo del tempo trascorso in millisecondi
            timing.put(h, timing.getOrDefault(h,0l) + (endTime - startTime)); // Converti da nanosecondi a millisecondi

            jaccardIndexes.put(h, jaccardIndexes.getOrDefault(h, 0.) + jaccardIndex(jm.getHullNodes(), h.getHullNodes()));

          } catch (Exception e) {
            displayHeurisitc(List.of(h), sample, p.getEdgeNumber());
            System.out.println("exception in " + h.getClass());
            exceptions.put(h, exceptions.getOrDefault(h, 0) + 1);
            e.printStackTrace();
            break it;
          }
        }
      }
    }

    System.out.println("\n-----------    jaccardIndex over " + j + " iterations    ---------------");
    for (Map.Entry<Heuristic, Double> e : jaccardIndexes.entrySet()) {
      // index must be divided with the number of times the algorithm has worked succesfully
      double index = jaccardIndexes.get(e.getKey()) / (j - exceptions.getOrDefault(e.getKey(), 0));
      long time = timing.get(e.getKey()) / (j - exceptions.getOrDefault(e.getKey(), 0));

      System.out.println(e.getKey().getClass().getName() + " \t: " +
          String.format("%.3f", index) +  // Formatta index a 3 cifre decimali
          "\t average time: " + time +
          "\t exceptions: " + exceptions.get(e.getKey()));
    }
  }

  /**
   * displays a list of heuristical algoritms result in a window
   * @param heuristics list of algorithms to display
   * @param points sample of point onto which execute the algorithm
   * @param desiredEdges number of desired edges
   */
  public static void displayHeurisitc(List<Heuristic> heuristics, List<Point2D> points, int desiredEdges){
    if(points == null){
      System.out.println("randomly generating 30 points for display");
      points = rndNodesGenerator2D(30);
    }

    JarvisMarch jm = new JarvisMarch(points);
    List<Edge> convexHull = jm.getHullEdges();

    for(Heuristic h : heuristics) {
      if(h instanceof FromCH fromCH) fromCH.newData(convexHull);
      if(h instanceof FromPoints fromPoints) fromPoints.newData(getG(jm.getHullNodes()), points);
      h.calcConvexHull(desiredEdges);
    }

    GraphPanel graph = new GraphPanel(points, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
  }

  /**
   * given a polygon generates a list of sample points
   * @param p polygon from which generate samples
   * @return a list of samples for the testing of heuristics
   */
  public static List<List<Point2D>> polygonSampleList(Polygon p){
    List<List<Point2D>> sample = new ArrayList<>();

    for (int nPoints : new int[] { 10, 50, 100})
      for(int i = 0; i < 30; i ++) sample.add(p.getSample(nPoints));

    return sample;
  }

  public static double jaccardIndex(List<Point2D> hullA, List<Point2D> hullB) {
    GeometryFactory geometryFactory = new GeometryFactory();

    // System.out.println("hullA: " + hullA);
    // Define coordinates for the first polygon
    List<Coordinate> polygonACoords = new ArrayList<>();
    for (Point2D n : hullA) polygonACoords.add(new Coordinate(n.getX(), n.getY()));
    polygonACoords.add(new Coordinate(hullA.get(0).getX(), hullA.get(0).getY()));

    // Define coordinates for the second polygon
    List<Coordinate> polygonBCoords = new ArrayList<>();
    for (Point2D n : hullB) polygonBCoords.add(new Coordinate(n.getX(), n.getY()));
    polygonBCoords.add(new Coordinate(hullB.get(0).getX(), hullB.get(0).getY()));

    // Create Polygon objects
    Coordinate[] pa = polygonACoords.toArray(new Coordinate[0]);
    org.locationtech.jts.geom.Polygon polygonA = geometryFactory.createPolygon(pa);

    Coordinate[] pb = polygonBCoords.toArray(new Coordinate[0]);
    org.locationtech.jts.geom.Polygon polygonB = geometryFactory.createPolygon(pb);

    Geometry intersection = polygonA.intersection(polygonB);
    Geometry union = polygonA.union(polygonB);
    double intersectionArea = intersection.getArea();
    double unionArea = union.getArea();

    return intersectionArea / unionArea;// Jaccard index
  }

  /**
   * returns the center of mass of the given ConvexHull
   * @param convexHull list of edges of the convexhull
   */
  private static Point2D getG(List<Point2D> convexHull) {
    int x = 0;
    int y = 0;
    for (Point2D n : convexHull) {
      x += n.getX();
      y += n.getY();
    }
    x /= convexHull.size();
    y /= convexHull.size();

    //List<Integer> coord = new ArrayList<>(coordinates);
    return new Point2D(100, x, y);
  }
}
