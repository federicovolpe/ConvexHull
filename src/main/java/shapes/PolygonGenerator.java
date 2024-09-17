package shapes;

import basic.Point2D;
import main.JarvisMarch;

import java.util.*;

import static utils.Constants.GraphConstants.*;

public class PolygonGenerator {

  private static final Random RAND = new Random();

  /**
   * generates a random simple polygon by choosing random points in a circomference
   * of a setted radius
   * @param n number of vertices of the polygon
   */
  public Polygon generateRandomRegularPolygon(int n){
    if (n < 3) throw new IllegalArgumentException("Polygon must have at least 3 vertices.");

    Random random = new Random();

    // Set a radius for the circle
    double radius = GRAPH_BOUND * .70; // Adjust as needed
    double totalArea = Math.pow(radius, 2 ) * Math.PI;
    double polygonArea;
    JarvisMarch jm;
    do {
      // Generate random angles for the vertices
      List<Double> angles = new ArrayList<>(n);
      for (int i = 0; i < n; i++) angles.add(random.nextDouble() * 2 * Math.PI);

      // Calculate the coordinates of the vertices
      List<Point2D> vertices = new ArrayList<>(n);
      for (Double angle : angles) {
        double x = radius * Math.cos(angle);
        double y = radius * Math.sin(angle);
        vertices.add(new Point2D(angles.indexOf(angle), (int) x, (int) y));
      }

      // riordinamento dei vertici randomici per creare il guscio convesso
      jm = new JarvisMarch(vertices);
      polygonArea = jm.calcArea();
    }while(polygonArea / totalArea < .3);

    return new Polygon(jm.getHullNodes());
  }

  public Polygon generateRegularPolygon(int sides) {
    List<Point2D> vertices = new ArrayList<>();
    for(int i = 0; i < sides; i++){
      Point2D v = new Point2D(i, (int)(100 * Math.cos(i * 2*Math.PI / 5)), (int) (100 * Math.sin(i * 2*Math.PI / 5)) );
      vertices.add(v);
    }
    return new Polygon(vertices);
  }

  private static Polygon valtrAlgorithm(int n) {
    // Generate two lists of random X and Y coordinates
    List<Double> xPool = new ArrayList<>(n);
    List<Double> yPool = new ArrayList<>(n);

    for (int i = 0; i < n; i++) {
      xPool.add(RAND.nextDouble());
      yPool.add(RAND.nextDouble());
    }

    // Sort them
    Collections.sort(xPool);
    Collections.sort(yPool);

    // Isolate the extreme points
    Double minX = xPool.get(0);
    Double maxX = xPool.get(n - 1);
    Double minY = yPool.get(0);
    Double maxY = yPool.get(n - 1);

    // Divide the interior points into two chains & Extract the vector components
    List<Double> xVec = new ArrayList<>(n);
    List<Double> yVec = new ArrayList<>(n);

    double lastTop = minX, lastBot = minX;

    for (int i = 1; i < n - 1; i++) {
      double x = xPool.get(i);

      if (RAND.nextBoolean()) {
        xVec.add(x - lastTop);
        lastTop = x;
      } else {
        xVec.add(lastBot - x);
        lastBot = x;
      }
    }

    xVec.add(maxX - lastTop);
    xVec.add(lastBot - maxX);

    double lastLeft = minY, lastRight = minY;

    for (int i = 1; i < n - 1; i++) {
      double y = yPool.get(i);

      if (RAND.nextBoolean()) {
        yVec.add(y - lastLeft);
        lastLeft = y;
      } else {
        yVec.add(lastRight - y);
        lastRight = y;
      }
    }

    yVec.add(maxY - lastLeft);
    yVec.add(lastRight - maxY);

    // Randomly pair up the X- and Y-components
    Collections.shuffle(yVec);

    // Combine the paired up components into vectors
    List<java.awt.geom.Point2D.Double> vec = new ArrayList<>(n);

    for (int i = 0; i < n; i++) {
      vec.add(new java.awt.geom.Point2D.Double(xVec.get(i), yVec.get(i)));
    }

    // Sort the vectors by angle
    vec.sort(Comparator.comparingDouble(v -> Math.atan2(v.getY(), v.getX())));

    // Lay them end-to-end
    double x = 0, y = 0;
    double minPolygonX = 0;
    double minPolygonY = 0;
    List<java.awt.geom.Point2D.Double> points = new ArrayList<>(n);

    for (int i = 0; i < n; i++) {
      points.add(new java.awt.geom.Point2D.Double(x, y));

      x += vec.get(i).getX();
      y += vec.get(i).getY();

      minPolygonX = Math.min(minPolygonX, x);
      minPolygonY = Math.min(minPolygonY, y);
    }

    // Move the polygon to the original min and max coordinates
    double xShift = minX - minPolygonX;
    double yShift = minY - minPolygonY;

    for (int i = 0; i < n; i++) {
      java.awt.geom.Point2D.Double p = points.get(i);
      points.set(i, new java.awt.geom.Point2D.Double(p.x + xShift, p.y + yShift));
    }

    List<basic.Point2D> TwoDimPOints = new ArrayList<>();
    int index = 0;
    for (java.awt.geom.Point2D p : points){
      System.out.println(p);
      TwoDimPOints.add(new basic.Point2D(index, (int) (p.getX()* 500)-300, (int) (p.getY() * 500)-300));
      index ++;
    }
    System.out.println();
    return new Polygon(TwoDimPOints);
  }

  /**
   * generates a certain number of polygons with the same number of sides using valtrAlgorithm
   * @param sideNumber number of the sides of the polygon
   * @param polygonAmount number of total polygons to generate
   * @return iterable of polygons
   */
  public Iterator iterator(int sideNumber,  int polygonAmount) {
    return new Iterator<>() {
      int iteration = 0;

      @Override
      public boolean hasNext() {
        return iteration < polygonAmount;
      }

      @Override
      public Polygon next() {
        if (!hasNext()) throw new NoSuchElementException();

        Polygon generated = valtrAlgorithm(sideNumber);
        iteration ++;
        return generated;
      }
    };
  }
}
