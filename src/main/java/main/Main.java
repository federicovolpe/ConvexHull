package main;

import static java.awt.Color.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import org.locationtech.jts.geom.*;
import basic.Edge;
import basic.Node2D;
import heuristics.CuttingLargerAngle2;
import heuristics.CuttingSmallerAngle2;
import heuristics.DistanceFromG;
import heuristics.Heuristic;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import shapes.Polygon;
import shapes.Shapes;
import utils.utilMethods;

public class Main {
  public static void main(String[] args) {

    demonstrateHeuristics(400, 5, Shapes.RECTANGLE.getPolygon());
    //demonstrateHeuristics(30, 5);
    //demonstrateHeuristics(50, 5);

    //iterationStatistics();
  }

  public static void demonstrateHeuristics(int nNodes, int desiredEdges, Polygon p){
    List<Node2D> nodes;
    if(p == null) nodes = utilMethods.rndNodesGenerator2D(nNodes);
    else nodes = p.getSample(nNodes);

    JarvisMarch jm = new JarvisMarch(nodes);
    List<Edge> convexHull = jm.getHullEdges();

    List<Heuristic> heuristics = List.of(
        //new CuttingSmallerAngle(desiredEdges, convexHull, GREEN),
        new CuttingSmallerAngle2(desiredEdges, convexHull, BLUE),
        //new CuttingLargerAngle(desiredEdges, convexHull, CYAN),
        new CuttingLargerAngle2(desiredEdges, convexHull, YELLOW),
        new DistanceFromG(desiredEdges, jm.getHullNodes(), nodes, ORANGE));

    GraphPanel graph = new GraphPanel(nodes, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
  }

  public static void iterationStatistics(){
    int iterations = 100;
    double nodesAmount = 20;

    double[] jaccardIndexes = new double[5]; // numero di euristiche

    for (int i = 0; i < iterations; i++) {
      System.out.println("-------------- iteration : " + i);
      List<Node2D> nodes = utilMethods.rndNodesGenerator2D(20);
      JarvisMarch jm = new JarvisMarch(nodes);
      List<Edge> convexHull = jm.getHullEdges();

      List<Heuristic> heuristics = List.of(
          //new CuttingSmallerAngle(5, convexHull, GREEN),
          //new CuttingSmallerAngle2(5, convexHull, BLUE),
          //new CuttingLargerAngle(5, convexHull, RED),
          //new CuttingLargerAngle2(5, convexHull, YELLOW),
          new DistanceFromG(5, jm.getHullNodes(), nodes, ORANGE));

      for (int h = 0; h < heuristics.size(); h++ ) {
        try {
          jaccardIndexes[h] += jaccardIndex(jm.getHullNodes(), heuristics.get(h).getHullNodes());

        } catch (Exception e) {
          GraphPanel problemGraph = new GraphPanel(nodes, convexHull, heuristics);
          JFrame problemFrame = new GraphWithPoints(problemGraph);
          problemFrame.setVisible(true);
          e.printStackTrace();
          break;
        }
      }
    }
    System.out.println("jaccardIndex over " + iterations + " iterations with "+ nodesAmount +" nodes: ");
    for (int i = 0; i < jaccardIndexes.length; i++) {
      System.out.println(i +" "+ jaccardIndexes[i]/ iterations);
    }
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

}
