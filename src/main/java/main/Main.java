package main;

import static java.awt.Color.*;
import static main.Statistics.*;
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
import shapes.Polygon;
import shapes.PolygonGenerator;

public class Main {
  public static void main(String[] args) throws InterruptedException {
    List<Heuristic> heuristics = List.of(
        new CuttingSmallerAngle(GREEN),
        new CuttingSmallerAngle2(BLUE),
        new CuttingLargerAngle(YELLOW),
        new CuttingLargerAngle2(ORANGE),
        new DistanceFromG(DARK_GRAY),
        new CuttingEdges(BLACK),
        new LessArea(PINK),
        new DistanceFromG2(CYAN));

    //displayHeurisitc(heuristics,
    //    new PolygonGenerator().generateRegularPolygon(5), 3);
    displayHeurisitc(heuristics,
      new PolygonGenerator().generateRandomRegularPolygon(7), 5);
    //displayHeurisitc(heuristics,
       // new PolygonGenerator().generateRandomRegularPolygon(10), 5);

    /*PolygonGenerator pg =  new PolygonGenerator();
    Polygon p = pg.generateRandomRegularPolygon(4);
    TestCase t0 = new TestCase(null,3, p.getEdges(), p.getVertices());
    p = pg.generateRandomRegularPolygon(7);
    TestCase t1 = new TestCase(null,5, p.getEdges(), p.getVertices());
    p = pg.generateRandomRegularPolygon(10);
    TestCase t2 = new TestCase(null,7, p.getEdges(), p.getVertices());
    generateDemoImages(heuristics, List.of(t0, t1, t2));*/
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
