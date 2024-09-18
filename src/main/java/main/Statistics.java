package main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import basic.CircularList;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import basic.Point2D;
import heuristics.Heuristic;
import heuristics.fromConvexHull.FromCH;
import heuristics.fromPoints.FromPoints;
import paintGraph.presenter.GraphPresenter;
import paintGraph.presenter.HeuristicPresenter;
import paintGraph.presenter.InputPresenter;
import paintGraph.view.ButtonPanel;
import paintGraph.view.GraphPanel;
import paintGraph.view.GraphWithPoints;
import paintGraph.Model;
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
    /*for(Heuristic h : heuristics){
      for(TestCase t : tests) {
        displayHeurisitc(List.of(h), new Polygon(t.hullNodes()), t.DesiredEdges());

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
    }*/
  }

  /**
   * each of the heuristic is applied to a set of test cases, the results are written in a file
   *    if a case generates an exception, a window with a graphical visualization of the error is shown
   * @param heuristics
   */
  public static void fileReportStatistics(List<Heuristic> heuristics){

    File file = new File("reportREG.csv");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("heuristic,points,hyperplane_budget,jaccard_index,time,exceptions,regularity\n");
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(int sideNumber = 4; sideNumber < 30; sideNumber++){
      Iterator<Polygon> it = new PolygonGenerator().iterator(sideNumber, 5);
      while(it.hasNext()){
        Polygon testPolygon = it.next();
        for(int budget = 3; budget < sideNumber; budget ++){
          TestCase test = new TestCase(
              testPolygon,
              budget
          );

          try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            //displayHeurisitc(heuristics, testPolygon, test.DesiredEdges());
            for (ReportData r : processSample(heuristics, test)) writer.write(r.toCsv());
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
    heuristic.newData(new Polygon(testCase.p()));

    try {

      long startTime = System.nanoTime();
      System.out.println("solving " + heuristic.getClass().getName() + "\n...");

      heuristic.calcConvexHull(testCase.DesiredEdges());

      long endTime = System.nanoTime();

      return new ReportData(heuristic,
          testCase.hullNodes().size(),
          testCase.DesiredEdges(),
          jaccardIndex(testCase.hullNodes(), heuristic.getHullNodes()),
          (endTime - startTime),
          false,
          testCase.regularity());
    } catch (Exception e) {

      e.printStackTrace();
      return new ReportData(heuristic,
          testCase.hullNodes().size(),
          testCase.DesiredEdges(),
          null,
          null,
          true,
          testCase.regularity());
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
      h.newData(new Polygon(p));  // passing a copy of the polygon
      h.calcConvexHull(desiredEdges);
    }

    // model creation
    Model model = new Model(p, heuristics);
    // view creation
    GraphPanel graph = new GraphPanel(p, heuristics);
    ButtonPanel bp = new ButtonPanel(heuristics);
    GraphWithPoints frame = new GraphWithPoints(bp, graph);

    // presenter creation
    InputPresenter inputPresenter = new HeuristicPresenter(bp, model, heuristics);
    GraphPresenter graphPanel = new GraphPresenter(frame, model);

    model.notifyObservers();
    frame.setVisible(true);
    for(Heuristic h : heuristics)
      System.out.println(h.getClass().getSimpleName() + " -> "+ jaccardIndex(p.getVertices(), h.getHullNodes()));
  }

  public static double jaccardIndex(List<Point2D> hullA, List<Point2D> hullB) {
    GeometryFactory geometryFactory = new GeometryFactory();

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

    return intersectionArea / unionArea;// Jaccard index
  }


}