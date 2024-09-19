package heuristics.fromConvexHull;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import heuristics.fromPoints.FromPoints;
import shapes.Polygon;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GreedyPolymake extends FromPoints {
  private Polygon polygon;
  private List<Point2D> sample;

  public GreedyPolymake(Color c) {
    super(c);
  }

  public void calcConvexHull(int n) {
    // output in un file di testo dei punti generati e delle equazioni dei lati
    File file = new File("amplhull.dat");
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write("param nv := "+polygon.getVertices().size()+";\n");
      writer.write("param: xv\t  yv:=\n");
      int i = 0;
      for(Point2D v : polygon.getVertices()) {
        writer.write(i +"   "+ v.getX() +"    "+ v.getY() + "\n");
        i++;
      }
      writer.write(";\n");

      i = 0;
      writer.write("param np := "+sample.size()+";\n");
      writer.write("param : xp\t yp :=\n");
      for(Point2D p : sample){
        writer.write(i +"   "+p.getX() +"  "+p.getY() + "\n");
        i++;
      }
      writer.write(";\n");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private List<Point2D> calcSample() {
    // calcolo del baricentro1

    // generazione dei punti dal baricentro con raggio max d
    double maxd = maxDistance(centerOfMass, polygon.getVertices());
    return pointsInCircle(maxd, centerOfMass.getX(), centerOfMass.getY());
  }


  private Point2D getG(List<Point2D> convexHull) {
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

  private double maxDistance(Point2D c, List<Point2D> vertices) {
    double largestDistance = 0;
    for(Point2D v : vertices){
      double distance = c.calcDistance(v);
      largestDistance = Math.max(distance, largestDistance);

    }
    return largestDistance;
  }

    public List<Point2D> pointsInCircle(double r, int X, int Y) {
      List<Point2D> punti = new ArrayList<>();
      int R = (int) r;

      for (int x = -R + X; x <= R + X; x += 15) {
        for (int y = -R + Y; y <= R + Y; y += 15) {
          // Controlla se il punto (x, y) si trova all'interno del cerchio
          if ((x - X) * (x - X) + (y - Y) * (y - Y) <= R * R) {
              punti.add(new Point2D(0,x, y));  // Aggiungi il punto alla lista
          }
        }
      }
      return punti;

    }

  public boolean isPointInPolygon(Point2D point) {
    boolean inside = false;

    // Itera su ogni lato del poligono
    for (Edge e : polygon.getEdges()) {
      // Controlla se il raggio orizzontale interseca un lato del poligono
      if ((e.n1().getY() > point.getY()) != (e.n2().getY() > point.getY()) &&

          point.getX() < (e.n2().getX() - e.n1().getX()) * (point.getY() - e.n1().getY()) /
              (e.n2().getY() - e.n1().getY()) + e.n1().getX()) {
        inside = !inside;
      }
    }
    return inside;
  }

  @Override
  public void draw(Graphics g) {
    g.setColor(Color.black);
    for(Point2D p : sample)
      p.draw(g,false);
  }

  @Override
  public void newData(Polygon p) {
    this.polygon = p;

    // calcolo del baricentro per poter calcolare il centro geometrico
    this.centerOfMass = getG(p.getVertices());
    List<Point2D> insidePoints = calcSample().stream()
        .filter(this::isPointInPolygon).toList();

    // calcolo del centro geometrico calcolato come il baricentro di tutti
    // i punti del sampling interno
    this.centerOfMass = getG(insidePoints);

    this.sample = calcSample().stream()
        .filter(point -> !isPointInPolygon(point)).toList();
  }

}
