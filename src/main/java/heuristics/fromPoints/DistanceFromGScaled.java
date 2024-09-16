package heuristics.fromPoints;

import basic.Point2D;

import java.awt.*;
import java.util.List;

public class DistanceFromGScaled extends DistanceFromG {

  public DistanceFromGScaled(Color c) {
    super(c);
  }

  @Override
  public void calcConvexHull(int n) {
    super.calcConvexHull(n);
    scale();
  }

  public void scale() {
    // calcolo della massima distanza fra c1 e p2
    //d1_2 = maxDistance(centerOfMass,)
    // calcolo della massima distanza fra c1 e p1
    // scaling ai vertici del convex hull
  }

  /** calcola la massima distanza fra un punto e una lista di punti
   *
   * @param c center of the first polygon
   * @param vertices vertices of the second polygon
   * @return the greatest distance
   */
  private double maxDistance(Point2D c, List<Point2D> vertices) {
    double largestDistance = 0;
    for(Point2D v : vertices){
      double distance = c.calcDistance(v);
      largestDistance = (distance > largestDistance) ? distance : largestDistance;
    }
    return largestDistance;
  }

}
