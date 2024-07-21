package basic;

import java.util.List;

public class Node2D extends Node {

  public Node2D(int index, int xPos, int yPos) {
    super(List.of(xPos, yPos), index);
  }

  public Node2D(int index, int bound) throws IllegalArgumentException {
    super(2, bound, index);
  }

  // Copy constructor
  public Node2D (Node2D node) {
    super(List.of(node.getX(), node.getY()), node.getIndex());
  }

  /**
   * given two points and the line that intersect them
   * tells the angle between p1-p2-p3
   */
  public double angleBetweenNodes(Node2D A, Node2D C) {
    double a = Math.sqrt(
        Math.pow((A.getX() - getX()), 2) + Math.pow( (A.getY() - getY()), 2));
    double b = Math.sqrt(
        Math.pow((getX() - C.getX()), 2) + Math.pow( (getY() - C.getY()), 2));
    double c = Math.sqrt(
        Math.pow( (C.getX() - A.getX()), 2) + Math.pow( (C.getY() - A.getY()), 2));

    return Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b));
  }

  public int getX() {
    return coordinates.get(0);
  }

  public int getY() {
    return coordinates.get(1);
  }
}
