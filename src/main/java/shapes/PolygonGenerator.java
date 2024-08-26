package shapes;

import basic.Point2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.Constants.GraphConstants.*;

public class PolygonGenerator {
  /**
   * generates a random simple polygon by choosing random points in a circomference
   * of a setted radius
   * @param n number of vertices of the polygon
   */
  public Polygon generateRandomRegularPolygon(int n){
    if (n < 3) throw new IllegalArgumentException("Polygon must have at least 3 vertices.");

    Random random = new Random();

    // Set a radius for the circle
    double radius = GRAPH_BOUND * .7; // Adjust as needed

    // Generate random angles for the vertices
    List<Double> angles = new ArrayList<>(n);
    for (int i = 0; i < n; i++) angles.add(random.nextDouble() * 2 * Math.PI);

    // Calculate the coordinates of the vertices
    List<Point2D> vertices = new ArrayList<>(n);
    for (Double angle : angles) {
      double x = radius * Math.cos(angle);
      double y = radius * Math.sin(angle);
      vertices.add(new Point2D(angles.indexOf(angle), (int) x, (int) y));
      System.out.println("new vertice " + vertices.get(vertices.size() -1));
    }

    return new Polygon(vertices);
  }
}
