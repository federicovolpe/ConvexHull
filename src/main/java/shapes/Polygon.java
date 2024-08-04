package shapes;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {

  protected CircularList<Point2D> vertices;
  protected CircularList<Edge> edges;

  public Polygon(List<Point2D> nodes) {
    vertices = new CircularList<>(nodes);
  }

  protected double getPerimeter() {
    double sum = 0;
    for (Edge edge : edges) sum += edge.getLength();
    return sum;
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
      points.add(generateRandomPointInTriangle(t, rand));
    }

    return points;
  }

  private Point2D generateRandomPointInTriangle(Triangle t, Random rand) {
    double r1 = Math.sqrt(rand.nextDouble());
    double r2 = rand.nextDouble();
    int x = (int) ((1 - r1) * t.a.getX() + (r1 * (1 - r2)) * t.b.getX() + (r1 * r2) * t.c.getX());
    int y = (int) ((1 - r1) * t.a.getY() + (r1 * (1 - r2)) * t.b.getY() + (r1 * r2) * t.c.getY());
    return new Point2D(-1, x, y);
  }

  private List<Triangle> triangulate() {
    List<Triangle> triangles = new ArrayList<>();
    for (int i = 1; i < vertices.size() - 1; i++) {
      triangles.add(new Triangle(vertices.get(0), vertices.get(i), vertices.get(i + 1)));
    }
    return triangles;
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

  public String toPolymake(){
    StringBuilder sb = new StringBuilder("new Polytope(POINTS =>[");
    for (Point2D n: vertices) {
      sb.append(n.toPolymakeVert()).append(",\n");
    }
    return sb.substring(0,sb.length()-2) + "]);";
  }

}
