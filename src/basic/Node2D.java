package basic;

import java.util.List;

public class Node2D extends Node {

  public Node2D(int index, int xPos, int yPos) {
    super(List.of(xPos, yPos), index);
  }

  public Node2D(int index, int bound) throws IllegalArgumentException {
    super(2, bound, index);
  }

  public int getX() {
    return coordinates.get(0);
  }

  public int getY() {
    return coordinates.get(1);
  }
}
