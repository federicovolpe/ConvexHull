package heuristics;

import basic.Point2D;
import java.awt.*;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

public abstract class PointHeuristic extends Heuristic {
  protected Point2D centerOfMass;
  protected List<Point2D> allNodes;

  public PointHeuristic(Point2D centerOfMass, List<Point2D> allNodes, Color c) {
    super(c);
    this.centerOfMass = centerOfMass;
    this.allNodes = allNodes;
  }

  public void newData(Point2D centerOfMass, List<Point2D> allNodes) {
    this.centerOfMass = centerOfMass;
    this.allNodes = allNodes;
  }

  // about drawing the heuristic
  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(Color.BLACK);
    int x = ORIGIN_X + centerOfMass.getX() - POINT_DIM /2;
    int y = ORIGIN_Y - centerOfMass.getY() - POINT_DIM /2;
    g.fillOval(x, y, POINT_DIM, POINT_DIM);
  }
}
