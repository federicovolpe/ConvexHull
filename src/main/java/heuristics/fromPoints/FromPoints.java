package heuristics.fromPoints;

import basic.Point2D;
import heuristics.Heuristic;

import java.awt.*;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

public abstract class FromPoints extends Heuristic {

  protected Point2D centerOfMass;
  protected List<Point2D> allNodes;

  public FromPoints(Point2D centerOfMass, List<Point2D> allNodes, Color c) {
    super(c);
    this.centerOfMass = centerOfMass;
    this.allNodes = allNodes;
  }

  /**
   * resets the current algorithm and initializes it with new set of data
   * @param centerOfMass heuristic point (the center of mass)
   * @param allNodes all of the points for the ch calcuation
   */
  public void newData(Point2D centerOfMass, List<Point2D> allNodes) {
    this.centerOfMass = centerOfMass;
    this.allNodes = allNodes;
  }

  public void draw(Graphics g) {
    super.draw(g);
    g.setColor(Color.BLACK);
    int x = ORIGIN_X + centerOfMass.getX() - POINT_DIM /2;
    int y = ORIGIN_Y - centerOfMass.getY() - POINT_DIM /2;
    g.fillOval(x, y, POINT_DIM, POINT_DIM);
  }
}
