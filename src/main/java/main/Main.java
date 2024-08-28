package main;

import java.awt.Color;
import static java.awt.Color.*;
import java.util.ArrayList;
import java.util.List;

import basic.Point2D;
import heuristics.Heuristic;
import heuristics.fromConvexHull.cuttingEdges.CuttingEdges;
import heuristics.fromConvexHull.cuttingNodes.CuttingLargerAngle;
import heuristics.fromConvexHull.cuttingNodes.CuttingLargerAngle2;
import heuristics.fromConvexHull.cuttingNodes.CuttingSmallerAngle;
import heuristics.fromConvexHull.cuttingNodes.CuttingSmallerAngle2;
import heuristics.fromConvexHull.edgeChoice.LessArea;
import heuristics.fromPoints.DistanceFromG;
import heuristics.fromPoints.DistanceFromG2;
import shapes.PolygonGenerator;

import static main.Statistics.displayHeurisitc;
import static main.Statistics.fileReportStatistics;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<Heuristic> heuristics = List.of(
        new CuttingSmallerAngle(new ArrayList<>(), GREEN),
        new CuttingSmallerAngle2(new ArrayList<>(), BLUE),
        new CuttingLargerAngle(new ArrayList<>(), RED),
        new CuttingLargerAngle2(new ArrayList<>(), YELLOW),
        //new DistanceFromG(null, new ArrayList<>(), ORANGE),
        new CuttingEdges(new ArrayList<>(), BLACK),
        //new LessArea(new ArrayList<>(), GREEN),
        new DistanceFromG2(null, new ArrayList<>(), Color.CYAN));

    displayHeurisitc(heuristics,
        new PolygonGenerator().generateRandomRegularPolygon(6), 4);
    //displayHeurisitc(heuristics, rndNodesGenerator2D(10), 5);
    //displayHeurisitc(heuristics, rndNodesGenerator2D(30), 5);
    //displayHeurisitc(heuristics, rndNodesGenerator2D(50), 5);

    //iterationStatistics(heuristics);
    //fileReportStatistics(heuristics);

  }

  /**
   * converts a list of points into a readable polymake polytope
   * @param points list of the points
   * @return a command for generating a polymake polytope
   */
  private static String toPolymake(List<Point2D> points){
      StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
      for (Point2D n: points) {
        sb.append(n.toPolymakeVert()).append(",\n");
      }
      return sb.substring(0,sb.length()-2)+ "]);";
  }
}
