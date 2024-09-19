package shapes;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import main.JarvisMarch;
import utils.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {
  protected CircularList<Point2D> vertices;
  protected CircularList<Edge> edges;

  public Polygon (List<Point2D> nodes) {
    JarvisMarch jm = new JarvisMarch(nodes);
    vertices = new CircularList<>(jm.getHullNodes());
    edges = new CircularList<>(jm.getHullEdges());
  }

  /*public static Polygon fromEdges(List<Edge> edges) {
    CircularList<Point2D> vertices = new CircularList<>();
    for (int i = 0; i <= edges.size(); i++) {
      vertices.add(edges.get(i).calcIntersectionWithLine(
          edges.get(i+1).getLineParameters()[0],
          edges.get(i+1).getLineParameters()[1],
          edges.get(i+1).getLineParameters()[2]));
    }
    Polygon polygon = new Polygon(vertices);
    return polygon;
  }*/

  // creates a deep copy of the polygon
  public Polygon(Polygon p) {
    vertices = new CircularList<>(p.getVertices());
    edges = new CircularList<>(p.getEdges());
  }

  /**
   * generates a sample of n nodes inside the shape
   *
   * @param n numbers of node to generate
   * @return a list of nodes
   */
  public List<Point2D> getSample(int n) {
    List<Triangle> triangles = triangulate();
    List<Point2D> points = new ArrayList<>();
    Random rand = new Random();

    double[] areas = new double[triangles.size()];
    double totalArea = 0;

    // Calcola l'area di ogni triangolo e la somma totale
    for (int i = 0; i < triangles.size(); i++) {
      areas[i] = triangles.get(i).area();
      totalArea += areas[i];
    }

    // Genera i punti
    for (int i = 0; i < n; i++) {
      // Seleziona un triangolo in base alla sua area
      double r = rand.nextDouble() * totalArea;
      int triangleIndex = -1;
      for (int j = 0; j < areas.length; j++) {
        if (r <= areas[j]) {
          triangleIndex = j;
          break;
        }
        r -= areas[j];
      }

      // Genera un punto casuale nel triangolo selezionato
      Triangle t = triangles.get(triangleIndex);
      points.add(generateRandomPointInTriangle(i, t, rand));
    }

    return points;
  }

  private Point2D generateRandomPointInTriangle(int index, Triangle t, Random rand) {
    double r1 = Math.sqrt(rand.nextDouble());
    double r2 = rand.nextDouble();
    int x = (int) ((1 - r1) * t.a.getX() + (r1 * (1 - r2)) * t.b.getX() + (r1 * r2) * t.c.getX());
    int y = (int) ((1 - r1) * t.a.getY() + (r1 * (1 - r2)) * t.b.getY() + (r1 * r2) * t.c.getY());
    return new Point2D(index, x, y);
  }

  private List<Triangle> triangulate() {
    List<Triangle> triangles = new ArrayList<>();
    for (int i = 1; i < vertices.size() - 1; i++) {
      triangles.add(new Triangle(vertices.get(0), vertices.get(i), vertices.get(i + 1)));
    }
    return triangles;
  }

  /**calculates the point of intersection between this polygon and a segment
   *
   * @param edge line from which calculate the intersection
   * @return list of edges and the corrispective point of intersection
   */
  public List<Pair<Edge, Point2D>> intersections(Edge edge) {
    List<Pair<Edge, Point2D>> intersections = new ArrayList<>();

    for(Edge e : edges){
      int[] params = edge.getLineParameters();
      // equazione di intersezione di una retta e un segmento
      int x1 = e.n1().getX(), y1 = e.n1().getY(), x2 = e.n2().getX(), y2 = e.n2().getY();

      double denominator = params[0] * (x2 - x1) + params[1] * (y2 - y1);
      if(denominator != 0){
        double t = -(params[0] * x1 + params[1] * y1 + params[2]) / denominator;

        if (t >= 0 && t <= 1) {
          double xIntersect = x1 + t * (x2 - x1);
          double yIntersect = y1 + t * (y2 - y1);
          intersections.add(new Pair<>(e, new Point2D(0, (int) xIntersect, (int) yIntersect)));
        }
      }
    }
    return intersections;
  }

  /**
   * cuts out a part of the polygon
   * @param e hyperplane from which to cut
   * @return the external part of the polygon
   */
  public Polygon cutOut(Edge e) {
    List<Point2D> vert = new ArrayList<>();

    // aggiunta dei punti di intersezione
    List<Pair<Edge, Point2D>> inters = intersections(e);
    for(Pair<Edge, Point2D> i : inters) vert.add(i.getValue());

    // calcolo di tutti i punti a destra dell'edge
    System.out.println("external nodes: ");
    for(Point2D p : vertices) {
      if (e.isPointRight(p)) {
        vert.add(p);

      System.out.println(p);}
    }

    // costruzione dei poligono
    return new Polygon(vert);
  }

  /**
   * cuts out a part of the polygon
   * @param e hyperplane from which to cut
   * @return the internal part of the polygon
   */
  public Polygon cutIn(Edge e) {
    List<Point2D> vert = new ArrayList<>();

    // aggiunta dei punti di intersezione
    List<Pair<Edge, Point2D>> inters = intersections(e);
    for(Pair<Edge, Point2D> i : inters) vert.add(i.getValue());

    // calcolo di tutti i punti a destra dell'edge
    for(Point2D p : vertices)
      if(!e.isPointRight(p)) vert.add(p);

    System.out.println("internal nodes: "+ vert.size());
    // costruzione dei poligono
    return new Polygon(vert);
  }

  public void draw(Graphics g) {
    drawEdges((Graphics2D) g);
    drawNodes(g);
  }

  /**
   * draws all the edges of the convex hull
   * @param g graphics to use
   */
  public void drawEdges(Graphics2D g){
    g.setStroke(new BasicStroke(2));
    for (Edge e : edges) e.draw(g);
    g.setStroke(new BasicStroke(1));
  }

  /**
   * draws all the points interested in the calculation of the convex hull
   * @param g graphics to use
   */
  public void drawNodes(Graphics g) {
    for (Edge e : edges)
      e.n1().draw(g, false);
  }

  static class Triangle {
    Point2D a, b, c;

    Triangle(Point2D a, Point2D b, Point2D c) {
      this.a = a;
      this.b = b;
      this.c = c;
    }

    double area() {
      return 0.5 * Math.abs(a.getX() * (b.getY() - c.getY()) +
          b.getX() * (c.getY() - a.getY()) +
          c.getX() * (a.getY() - b.getY()));
    }
  }

  /**
   * gauss formula for calculating the area of a polygon
   * @return the area
   */
  public double calcArea() {
    List<Point2D> points = vertices;
    int n = vertices.size();
    double area = 0;

    for (int i = 0; i < n; i++) {
      Point2D current = points.get(i);
      Point2D next = points.get((i + 1) % n); // Next point, wrapping around to the first point at the end
      area += current.getX() * next.getY() - current.getY() * next.getX();
    }

    return Math.abs(area) / 2.0;
  }

  public int getEdgeNumber() {
    return edges.size();
  }

  public CircularList<Point2D> getVertices(){
    return new CircularList<>(vertices);
  }

  public CircularList<Edge> getEdges(){
    return edges;
  }

  /*public String toPolymake(){
    StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
    for (Point2D n: vertices) {
      sb.append(n.toPolymakeVert()).append(",\n");
    }
    return sb.substring(0,sb.length()-2) + "]);";
  }*/

}
