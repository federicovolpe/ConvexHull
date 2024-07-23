package main;

import basic.Edge;
import basic.Node2D;

import java.util.ArrayList;
import java.util.List;

import heuristics.*;
import heuristics.Heuristic;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import utils.utilMethods;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    int iterations = 10;
    List<Node2D> nodes = utilMethods.rndNodesGenerator2D(20);

    // System.out.println("generated:");
    // System.out.println(nodes);

    JarvisMarch jm = new JarvisMarch(nodes);
    List<Edge> convexHull = jm.getHullEdges();

    //Heuristic h = new CuttingNodes(5, convexHull);
    // Heuristic h = new CuttingNodes2(5, convexHull);
     Heuristic h = new CuttingNodes3(5, convexHull);
    // Heuristic h = new DistanceFromG(5, jm.getHullNodes(), nodes);
    GraphPanel graph = new GraphPanel(nodes, convexHull, h);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);

    double jaccard = 0;
    for (int i = 0; i < 10; i++) {
      jaccard += jaccardIndex(jm.getHullNodes(), h.getHullNodes());
    }
    System.out.println("jaccardIndex over " + iterations + " iterations: " + jaccard/ iterations);
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
