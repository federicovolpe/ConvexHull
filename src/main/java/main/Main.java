package main;

import java.util.ArrayList;
import java.util.List;
import heuristics.fromConvexHull.cuttingEdges.CuttingEdges;
import heuristics.fromConvexHull.cuttingNodes.CuttingSmallerAngle2;
import heuristics.fromConvexHull.edgeChoice.LessArea;
import heuristics.fromPoints.DistanceFromG2;
import basic.Point2D;
import heuristics.Heuristic;
import heuristics.fromConvexHull.cuttingNodes.CuttingLargerAngle;
import heuristics.fromConvexHull.cuttingNodes.CuttingLargerAngle2;
import heuristics.fromConvexHull.cuttingNodes.CuttingSmallerAngle;
import heuristics.fromPoints.DistanceFromG;
import static java.awt.Color.*;
import static main.Statistics.*;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<Heuristic> heuristics = List.of(
        new CuttingSmallerAngle(new ArrayList<>(), GREEN),
        new CuttingSmallerAngle2(new ArrayList<>(), BLUE),
        new CuttingLargerAngle(new ArrayList<>(), RED),
        new CuttingLargerAngle2(new ArrayList<>(), YELLOW),
        new DistanceFromG(null, new ArrayList<>(), ORANGE),
        new CuttingEdges(new ArrayList<>(), BLACK),
        new LessArea(new ArrayList<>(), GREEN),
        new DistanceFromG2(null, new ArrayList<>(), GREEN));

    //displayHeurisitc(heuristics, Shapes.SQUARE.getPolygon().getSample(20), Shapes.SQUARE.getPolygon().getEdgeNumber());
    //displayHeurisitc(heuristics, rndNodesGenerator2D(10), 5);
    //displayHeurisitc(heuristics, rndNodesGenerator2D(30), 5);
    //displayHeurisitc(heuristics, rndNodesGenerator2D(50), 5);

    //iterationStatistics(heuristics);
    fileReportStatistics(heuristics);
  }

  private static String toPolymake(List<Point2D> points){
      StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
      for (Point2D n: points) {
        sb.append(n.toPolymakeVert()).append(",\n");
      }
      return sb.substring(0,sb.length()-2)+ "]);";
  }
}
