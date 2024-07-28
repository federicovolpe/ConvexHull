package main;

import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.ORANGE;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import basic.Edge;
import basic.Node2D;
import heuristics.CuttingLargerAngle;
import heuristics.CuttingLargerAngle2;
import heuristics.CuttingSmallerAngle;
import heuristics.CuttingSmallerAngle2;
import heuristics.DistanceFromG;
import heuristics.Heuristic;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import utils.utilMethods;

public class Main {
  public static void main(String[] args) {
    /*List<Node2D> nodes = utilMethods.rndNodesGenerator2D(10);
    JarvisMarch jm = new JarvisMarch(nodes);
    List<Edge> convexHull = jm.getHullEdges();

    List<Heuristic> heuristics = List.of(
      // new CuttingSmallerAngle(5, convexHull, GREEN),
      // new CuttingSmallerAngle2(5, convexHull, BLUE),
      //new CuttingLargerAngle(5, convexHull, RED),
      new CuttingLargerAngle2(5, convexHull, GREEN));
      //new DistanceFromG(5, jm.getHullNodes(), nodes, ORANGE));

    GraphPanel graph = new GraphPanel(nodes, convexHull, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);

    nodes = utilMethods.rndNodesGenerator2D(30);
    jm = new JarvisMarch(nodes);
    convexHull = jm.getHullEdges();

    heuristics = List.of(
      // new CuttingSmallerAngle(5, convexHull, GREEN),
      // new CuttingSmallerAngle2(5, convexHull, BLUE),
      //new CuttingLargerAngle(5, convexHull, RED),
      new CuttingLargerAngle2(5, convexHull, GREEN));
      //new DistanceFromG(5, jm.getHullNodes(), nodes, ORANGE));

    graph = new GraphPanel(nodes, convexHull, heuristics);
    frame = new GraphWithPoints(graph);
    frame.setVisible(true);

    nodes = utilMethods.rndNodesGenerator2D(50);
    jm = new JarvisMarch(nodes);
    convexHull = jm.getHullEdges();

    heuristics = List.of(
    // new CuttingSmallerAngle(5, convexHull, GREEN),
    // new CuttingSmallerAngle2(5, convexHull, BLUE),
    //new CuttingLargerAngle(5, convexHull, RED),
    new CuttingLargerAngle2(5, convexHull, GREEN));
    //new DistanceFromG(5, jm.getHullNodes(), nodes, ORANGE));

    graph = new GraphPanel(nodes, convexHull, heuristics);
    frame = new GraphWithPoints(graph);
    frame.setVisible(true);*/

    iterationStatistics();
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
          new CuttingSmallerAngle(5, convexHull, GREEN),
          new CuttingSmallerAngle2(5, convexHull, BLUE),
          new CuttingLargerAngle(5, convexHull, RED),
          new CuttingLargerAngle2(5, convexHull, YELLOW),
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
    Polygon polygonA = geometryFactory.createPolygon(pa);

    Coordinate[] pb = polygonBCoords.toArray(new Coordinate[0]);
    Polygon polygonB = geometryFactory.createPolygon(pb);

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
