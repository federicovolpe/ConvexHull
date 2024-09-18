package heuristics.fromPoints;

import basic.Point2D;
import heuristics.Heuristic;
import shapes.Polygon;

import java.awt.*;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

public abstract class FromPoints extends Heuristic {

  protected Point2D centerOfMass;
  protected List<Point2D> allNodes;

  public FromPoints(Color c) {
    super(c);
  }

  /**
   * resets the current algorithm and initializes it with new set of dat
   * @param p starting convex hull polygon
   */
  @Override
  public void newData(Polygon p) {
    this.centerOfMass = getG(p.getVertices());
    this.allNodes = p.getVertices();
  }

  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(new Color(248, 3, 252));
    int x = ORIGIN_X + centerOfMass.getX() - POINT_DIM /2;
    int y = ORIGIN_Y - centerOfMass.getY() - POINT_DIM /2;
    g.fillOval(x, y, POINT_DIM, POINT_DIM);
    g.setColor(Color.BLACK);
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
