package main;

import static java.awt.Color.*;
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
import shapes.Polygon;
import utils.utilMethods;

public class Main {
  public static void main(String[] args) {
    //System.out.println(toPolymake(Shapes.TRIANGLE.getPolygon().getSample(60)));
    //System.out.println(Shapes.TRIANGLE.getPolygon().toPolymake());
    //demonstrateHeuristics(400, 5, Shapes.RECTANGLE.getPolygon());
    demonstrateHeuristics(30, 5, null);
    //demonstrateHeuristics(50, 5);

    iterationStatistics(List.of(
        new CuttingSmallerAngle(new ArrayList<>(), GREEN),
        new CuttingSmallerAngle2(new ArrayList<>(), BLUE),
        new CuttingLargerAngle(new ArrayList<>(), RED),
        new CuttingLargerAngle2(new ArrayList<>(), YELLOW),
        new DistanceFromG(null, new ArrayList<>(), ORANGE)),
        4, 20, 10);
  }
  private static String toPolymake(List<Node2D> nodes){
    StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
    for (Node2D n: nodes) {
      sb.append(n.toPolymakeVert()).append(",\n");
    }
    return sb.substring(0,sb.length()-2)+ "]);";
  }

  public static void demonstrateHeuristics(int nNodes, int desiredEdges, Polygon p){
    List<Node2D> nodes;
    if(p == null) nodes = utilMethods.rndNodesGenerator2D(nNodes);
    else nodes = p.getSample(nNodes);

    JarvisMarch jm = new JarvisMarch(nodes);
    List<Edge> convexHull = jm.getHullEdges();

    List<Heuristic> heuristics = List.of(
        //new CuttingSmallerAngle(convexHull, GREEN),
        new CuttingSmallerAngle2(convexHull, BLUE),
        //new CuttingLargerAngle(convexHull, CYAN),
        //new CuttingLargerAngle2(convexHull, YELLOW),
        new DistanceFromG(getG(jm.getHullNodes()), nodes, ORANGE));

    for(Heuristic h : heuristics) h.calcConvexHull(desiredEdges);

    GraphPanel graph = new GraphPanel(nodes, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
  }

  public static void iterationStatistics(List<Heuristic> heuristics, int desiredEdges, int pointsAmount, int iterations){
    // init the map
    Map<Heuristic, Double> jaccardIndexes = new HashMap<>();

    for (int i = 0; i < iterations; i++) {
      System.out.println("-------------- iteration : " + i);
      List<Node2D> nodes = utilMethods.rndNodesGenerator2D(pointsAmount);
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
          GraphPanel problemGraph = new GraphPanel(nodes, convexHull, heuristics);
          JFrame problemFrame = new GraphWithPoints(problemGraph);
          problemFrame.setVisible(true);
          e.printStackTrace();
          break;
        }
      }
    }
    //System.out.println("jaccardIndex over " + iterations + " iterations with "+ nodesAmount +" nodes: ");
    for (Map.Entry<Heuristic, Double> e : jaccardIndexes.entrySet())
      System.out.println(e.getKey().getClass().toString()+" : "+ jaccardIndexes.get(e.getKey())/ iterations);

  }

  public static double jaccardIndex(List<Node2D> hullA, List<Node2D> hullB) {
    GeometryFactory geometryFactory = new GeometryFactory();

    System.out.println("hullA: " + hullA);
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

    // Compute the symmetric difference
    //Geometry symmetricDifference = polygonA.symDifference(polygonB);

    Geometry intersection = polygonA.intersection(polygonB);
    Geometry union = polygonA.union(polygonB);
    double intersectionArea = intersection.getArea();
    double unionArea = union.getArea();

    // Calcola l'indice di Jaccard
    return intersectionArea / unionArea;
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
