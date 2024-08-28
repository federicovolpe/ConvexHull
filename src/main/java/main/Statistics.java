package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import basic.Point2D;
import heuristics.Heuristic;
import heuristics.fromConvexHull.FromCH;
import heuristics.fromPoints.FromPoints;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import shapes.Polygon;
import shapes.PolygonGenerator;

public class Statistics {

  /**
   * each of the heuristic is applied to a set of test cases, the results are accumulated and
   * printed at the end of the execution
   *
   * @param heuristics list of heuristics to test
   */
  public static void iterationStatistics(List<Heuristic> heuristics) {
    /*Map<Heuristic, Double> jaccardIndexes = new HashMap<>();
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
    }*/
  }

  /**
   * each of the heuristic is applied to a set of test cases, the results are written in a file
   *    if a case generates an exception, a window with a graphical visualization of the error is shown
   * @param heuristics
   */
  public static void fileReportStatistics(List<Heuristic> heuristics){

    File file = new File("report.csv");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("heuristic,points,hyperplane_budget,jaccard_index,time,exceptions\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(int sideNumber = 4; sideNumber < 30; sideNumber++){
      Iterator<Polygon> it = new PolygonGenerator().iterator(sideNumber, 5);
      while(it.hasNext()){
        Polygon testPolygon = it.next();
        for(int budget = 3; budget < sideNumber; budget ++){
          TestCase test = new TestCase(
              testPolygon.getVertices(),
              budget,
              testPolygon.getEdges(),
              testPolygon.getVertices());

          try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            //displayHeurisitc(heuristics, testPolygon, test.DesiredEdges());
            //for (ReportData r : processSample(heuristics, test)) writer.write(r.toCsv());
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
      System.out.println("Report written to " + file.getAbsolutePath());
  }


  /**
   * applies the given heuristic to a list of testCase
   * @param heuristics list of heuristics to test
   * @param testCase specific testcase
   * @return a list of reports of all the applications
   */
  private static List<ReportData> processSample(List<Heuristic> heuristics, TestCase testCase) {
    List<ReportData> reportList = new ArrayList<>();

    int iteration = 1;
    for (Heuristic h : heuristics) {
      System.out.println("\n-------------- heuristic : " + h.getClass().getSimpleName() + " with budget: : " + testCase.DesiredEdges() + "\n");
      iteration++;

      ReportData report = singleHeuristicApplication(h, testCase);
      reportList.add(report);

      if (report.exception()) {
        System.out.println("exception in " + h.getClass());
        //displayHeurisitc(List.of(h), testCase.sample(), testCase.DesiredEdges());
      }
    }
    return reportList;
  }

  /**
   * simply applies the given heuristic to a given test case and returns a report containing the results
   * @param heuristic heuristic to apply
   * @param testCase specific test case to which apply the heuristic
   * @return a Report data containing the result of the application
   */
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

      return new ReportData(heuristic,
          testCase.sample().size(),
          testCase.DesiredEdges(),
          jaccardIndex(testCase.hullNodes(), heuristic.getHullNodes()),
          (endTime - startTime),
          false);
    } catch (Exception e) {

      //e.printStackTrace();
      return new ReportData(
          heuristic,
          testCase.DesiredEdges(),
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
   * @param p starting convex hull
   * @param desiredEdges number of desired edges
   */
  public static void displayHeurisitc(List<Heuristic> heuristics, Polygon p, int desiredEdges) {

    for (Heuristic h : heuristics) {
      if (h instanceof FromCH fromCH) fromCH.newData(p.getEdges());
      if (h instanceof FromPoints fromPoints) fromPoints.newData(getG(p.getVertices()), p.getVertices());
      h.calcConvexHull(desiredEdges);
    }

    GraphPanel graph = new GraphPanel(p, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
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
