package basic;

import java.awt.*;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

public class Point2D extends Point {

  public Point2D(int index, int xPos, int yPos) {
    super(List.of(xPos, yPos), index);
  }

  public Point2D(int index, int bound) throws IllegalArgumentException {
    super(2, bound, index);
  }

  // Copy constructor
  public Point2D(Point2D node) {
    super(List.of(node.getX(), node.getY()), node.getIndex());
  }

  /**
   * given two points and the line that intersect them
   * tells the angle between p1-p2-p3
   */
  public double angleBetweenNodes(Point2D A, Point2D C) {
    double a = Math.sqrt(
        Math.pow((A.getX() - getX()), 2) + Math.pow( (A.getY() - getY()), 2));
    double b = Math.sqrt(
        Math.pow((getX() - C.getX()), 2) + Math.pow( (getY() - C.getY()), 2));
    double c = Math.sqrt(
        Math.pow( (C.getX() - A.getX()), 2) + Math.pow( (C.getY() - A.getY()), 2));

    return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b));
  }

  // if the node is contained between a triangle
  // if this is one of the vertex is not considered insides
  public boolean isContained(Point2D A, Point2D B, Point2D C){
    if(this.equals(A) || this.equals(B) || this.equals(C)) return false;
    double areaABC = triangleArea(A, B, C);
    double areaABP = triangleArea(A, B, this);
    double areaBCP = triangleArea(B, C, this);
    double areaCAP = triangleArea(C, A, this);

    return (areaABC == (areaABP + areaBCP + areaCAP));
  }

  private double triangleArea(Point2D A, Point2D B, Point2D C) {
    return Math.abs(A.getX() * (B.getY() - C.getY()) +
        B.getX() * (C.getY() - A.getY()) +
        C.getX() * (A.getY() - B.getY())) / 2.0;
  }

  public int getX() {
    return coordinates.get(0);
  }

  public int getY() {
    return coordinates.get(1);
  }

  public void draw(Graphics g, boolean drawIndex){
    int x = ORIGIN_X + getX() - (int)(POINT_DIM * .3);
    int y = ORIGIN_Y - getY() - (int)(POINT_DIM * .3);
    if(drawIndex) g.drawString(getIndex()+"", x - 2, y - 2);
    g.fillOval(x, y, (int) (POINT_DIM * .7), (int) (POINT_DIM * .7));
  }
}
