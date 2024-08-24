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
   *
   * @param heuristics list of heuristics to test
   */
  public static void iterationStatistics(List<Heuristic> heuristics) {
    Map<Heuristic, Double> jaccardIndexes = new HashMap<>();
    Map<Heuristic, Long> timing = new HashMap<>();
    Map<Heuristic, Integer> exceptions = new HashMap<>();

    List<Polygon> polygons = List.of(
        Shapes.RECTANGLE.getPolygon(),
        Shapes.SQUARE.getPolygon(),
        Shapes.TRIANGLE.getPolygon()
    );

    // per ogni poligono nella lista di poligoni crea una lista di sample sample di punti
    List<TestCase> testCases = new ArrayList<>();
    for (Polygon polygon : polygons) {
      testCases.addAll(getPolygonSample(polygon)); // ogni lista creata è un sample
    }

    for (Heuristic h : heuristics) {
      // storing the info
      for (ReportData r : processSample(h, testCases)) {
        exceptions.put(h, exceptions.getOrDefault(h, 0) + (r.exception() ? 1 : 0));
        timing.put(h, timing.getOrDefault(h, 0L) + (r.time() == null ? 0 : r.time()));
        jaccardIndexes.put(h, jaccardIndexes.getOrDefault(h, 0.0) + (r.jaccardIndex() == null ? 0 : r.jaccardIndex()));
      }
    }


    System.out.println("\n-----------    jaccardIndex over " + testCases.size() + " iterations    ---------------");
    for (Map.Entry<Heuristic, Double> e : jaccardIndexes.entrySet()) {
      // index must be divided with the number of times the algorithm has worked succesfully
      double index = jaccardIndexes.get(e.getKey()) / (testCases.size() - exceptions.getOrDefault(e.getKey(), 0));
      long time = timing.get(e.getKey()) / (testCases.size() - exceptions.getOrDefault(e.getKey(), 0));

      System.out.println(e.getKey().getClass().getName() + " \t: " +
          String.format("%.3f", index) +  // Formatta index a 3 cifre decimali
          "\t average time: " + time +
          "\t exceptions: " + exceptions.get(e.getKey()));
    }
  }

  /**
   * test the given heuristic onto a set of given test case
   */
  private static List<ReportData> processSample(Heuristic heuristic, List<TestCase> testCases) {
    List<ReportData> reportList = new ArrayList<>();

    int iteration = 1;
    for (TestCase testCase : testCases) {
      System.out.println("\n-------------- heuristic : "+heuristic+" iteration : " + iteration + "\n");
      iteration++;

      ReportData report = singleHeuristicApplication(heuristic, testCase);
      reportList.add(report);

      if (report.exception()) {
        System.out.println("exception in " + heuristic.getClass());
        displayHeurisitc(List.of(heuristic), testCase.sample(), testCase.DesiredEdges());
        break;
      }
    }
    return reportList;
  }

  private static ReportData singleHeuristicApplication(Heuristic heuristic, TestCase testCase) {
    // initialize the heuristic with the new data
    if (heuristic instanceof FromCH)
      ((FromCH) heuristic).newData(testCase.hullEdges());
    if (heuristic instanceof FromPoints)
      ((FromPoints) heuristic).newData(getG(testCase.hullNodes()), testCase.sample());

    try {

      long startTime = System.nanoTime();
      System.out.println("solving " + heuristic.getClass().getName() + "\n...");

      heuristic.calcConvexHull(testCase.DesiredEdges());

      long endTime = System.nanoTime();
      System.out.println("time " + heuristic.getClass().getCanonicalName() + ": " + (endTime - startTime) + "\n");
      return new ReportData(heuristic,
          testCase.sample().size(),
          jaccardIndex(testCase.hullNodes(), heuristic.getHullNodes()),
          (endTime - startTime),
          false);
    } catch (Exception e) {

      e.printStackTrace();
      return new ReportData(
          heuristic,
          testCase.sample().size(),
          null,
          null,
          true);
    }
  }

  /**
   * displays a list of heuristical algoritms result in a window
   *
   * @param heuristics   list of algorithms to display
   * @param points       sample of point onto which execute the algorithm
   * @param desiredEdges number of desired edges
   */
  public static void displayHeurisitc(List<Heuristic> heuristics, List<Point2D> points, int desiredEdges) {
    if (points == null) {
      System.out.println("randomly generating 30 points for display");
      points = rndNodesGenerator2D(30);
    }

    JarvisMarch jm = new JarvisMarch(points);
    List<Edge> convexHull = jm.getHullEdges();

    for (Heuristic h : heuristics) {
      if (h instanceof FromCH fromCH) fromCH.newData(convexHull);
      if (h instanceof FromPoints fromPoints) fromPoints.newData(getG(jm.getHullNodes()), points);
      h.calcConvexHull(desiredEdges);
    }

    GraphPanel graph = new GraphPanel(points, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
  }

  /**
   * given a polygon generates a list of sample points
   *
   * @param p polygon from which generate samples
   * @return a list of samples for the testing of heuristics
   */
  public static List<TestCase> getPolygonSample(Polygon p) {
    List<TestCase> samples = new ArrayList<>();

    for (int nPoints : new int[]{10, 50, 100})
      for (int i = 0; i < 30; i++){
        List<Point2D> sample = p.getSample(nPoints);
        JarvisMarch jm = new JarvisMarch(sample);
        samples.add(new TestCase(sample, p.getEdgeNumber(), jm.getHullEdges(), jm.getHullNodes()));
      }

    return samples;
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

    System.out.println("jaccard: "+intersectionArea / unionArea);
    return intersectionArea / unionArea;// Jaccard index
  }

  /**
   * returns the center of mass of the given ConvexHull
   *
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
