package main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
  public static void generateDemoImages (List<Heuristic> heuristics, List<TestCase> tests) {
    for(Heuristic h : heuristics){
      for(TestCase t : tests) {
        JPanel p = displayHeurisitc(List.of(h), new Polygon(t.hullNodes()), t.DesiredEdges());

        BufferedImage image = new BufferedImage(p.getWidth(), p.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        p.paint(g2d);

        g2d.dispose();

        // Salva l'immagine
        String path = "media/"+h.getClass().getSimpleName() +"/"+t.hullEdges().size()+"_"+t.DesiredEdges()+".png";
        try {
          ImageIO.write(image, "png", new File(path));
        } catch (FileNotFoundException e){
          System.out.println("not found path: "+ path);
        }catch (IOException e) {
          throw new RuntimeException(e);
        }
        System.out.println("JPanel salvato come immagine!");

      }
    }
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
      ((FromPoints) heuristic).newData(testCase.hullNodes());

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
  public static JPanel displayHeurisitc(List<Heuristic> heuristics, Polygon p, int desiredEdges) {
    for (Heuristic h : heuristics) {
      if (h instanceof FromCH fromCH) fromCH.newData(p.getEdges());
      if (h instanceof FromPoints fromPoints) fromPoints.newData(p.getVertices());
      h.calcConvexHull(desiredEdges);
    }

    GraphPanel graph = new GraphPanel(p, heuristics);
    JFrame frame = new GraphWithPoints(graph);
    frame.setVisible(true);
    // for making screenshots
    return graph;
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


}