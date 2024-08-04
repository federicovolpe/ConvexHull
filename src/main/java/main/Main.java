package main;

import static java.awt.Color.*;
import static utils.utilMethods.rndNodesGenerator2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;

import heuristics.*;
import org.locationtech.jts.geom.*;
import basic.Edge;
import basic.Node2D;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;

public class Main {
  public static void main(String[] args) {
    List<Heuristic> heuristics = List.of(
        new CuttingSmallerAngle(new ArrayList<>(), GREEN),
        new CuttingSmallerAngle2(new ArrayList<>(), BLUE),
        new CuttingLargerAngle(new ArrayList<>(), RED),
        new CuttingLargerAngle2(new ArrayList<>(), YELLOW),
        new DistanceFromG(null, new ArrayList<>(), ORANGE));

    //displayHeurisitc(heuristics, rndNodesGenerator2D(20), 5);

    iterationStatistics(heuristics, 4, 100, 100);
  }
  private static String toPolymake(List<Node2D> nodes){
    StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
    for (Node2D n: nodes) {
      sb.append(n.toPolymakeVert()).append(",\n");
    }
    return sb.substring(0,sb.length()-2)+ "]);";
  }

  /**
   * displays a list of heuristical algoritms result in a window
   * @param heuristics list of algorithms to display
   * @param nodes sample of point onto which execute the algorithm
   * @param desiredEdges number of desired edges
   */
  public static void displayHeurisitc(List<Heuristic> heuristics, List<Node2D> nodes, int desiredEdges){
    if(nodes == null){
      System.out.println("randomly generating 30 points for display");
      nodes = rndNodesGenerator2D(30);
    }

    JarvisMarch jm = new JarvisMarch(nodes);
    List<Edge> convexHull = jm.getHullEdges();

    for(Heuristic h : heuristics) {
      if(h instanceof CuttingNodes) ((CuttingNodes) h).newData(convexHull);
      if(h instanceof PointHeuristic) ((PointHeuristic) h).newData(getG(jm.getHullNodes()), nodes);
      h.calcConvexHull(desiredEdges);
    }

    GraphPanel graph = new GraphPanel(nodes, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
  }

  public static void iterationStatistics(List<Heuristic> heuristics, int desiredEdges, int pointsAmount, int iterations){
    // init the map
    Map<Heuristic, Double> jaccardIndexes = new HashMap<>();
    Map<Heuristic, Integer> exceptions = new HashMap<>();

    for (int i = 0; i < iterations; i++) {
      System.out.println("-------------- iteration : " + i);
      List<Node2D> nodes = rndNodesGenerator2D(pointsAmount);
      JarvisMarch jm = new JarvisMarch(nodes);
      List<Edge> convexHull = jm.getHullEdges();

      for (Heuristic h: heuristics) {
        try {
          // init the heuristic
          if(h instanceof CuttingNodes) ((CuttingNodes) h).newData(convexHull);
          if(h instanceof PointHeuristic) ((PointHeuristic) h).newData(getG(jm.getHullNodes()), nodes);

          h.calcConvexHull(desiredEdges);

          jaccardIndexes.put(h, jaccardIndexes.getOrDefault(h,0.) + jaccardIndex(jm.getHullNodes(), h.getHullNodes()));

        } catch (Exception e) {
          // displayHeurisitc(List.of(h), nodes, desiredEdges);
          System.out.println("exception in "+h.getClass());
          exceptions.put(h, exceptions.getOrDefault(h,0) + 1);
          break;
        }
      }
    }

    System.out.println("\n-----------    jaccardIndex over " + iterations + " iterations with "+ pointsAmount +" nodes    ---------------");
    for (Map.Entry<Heuristic, Double> e : jaccardIndexes.entrySet()) {
      // index must be divided with the number of times the algorithm has worked succesfully
      double index = jaccardIndexes.get(e.getKey()) / (iterations - exceptions.getOrDefault(e.getKey(), 0));

      System.out.println(e.getKey().getClass().getName() + " \t: " + index +
          "\t exceptions: " + exceptions.get(e.getKey()));
    }
  }

  public static double jaccardIndex(List<Node2D> hullA, List<Node2D> hullB) {
    GeometryFactory geometryFactory = new GeometryFactory();

    // System.out.println("hullA: " + hullA);
    // Define coordinates for the first polygon
    List<Coordinate> polygonACoords = new ArrayList<>();
    for (Node2D n : hullA) polygonACoords.add(new Coordinate(n.getX(), n.getY()));
    polygonACoords.add(new Coordinate(hullA.get(0).getX(), hullA.get(0).getY()));

    // Define coordinates for the second polygon
    List<Coordinate> polygonBCoords = new ArrayList<>();
    for (Node2D n : hullB) polygonBCoords.add(new Coordinate(n.getX(), n.getY()));
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
  private static Node2D getG(List<Node2D> convexHull) {
    int x = 0;
    int y = 0;
    for (Node2D n : convexHull) {
      x += n.getX();
      y += n.getY();
    }
    x /= convexHull.size();
    y /= convexHull.size();

    //List<Integer> coord = new ArrayList<>(coordinates);
    return new Node2D(100, x, y);
  }

}
