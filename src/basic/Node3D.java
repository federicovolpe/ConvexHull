package basic;

import java.util.List;

public class Node3D extends Node {

  public Node3D(int index, int xPos, int yPos, int zPos) {
    super(List.of(xPos, yPos, zPos), index);
  }

  public Node3D(int index, int bound) throws IllegalArgumentException {
    super(3, bound, index);
  }

  public int getX() {
    return coordinates.get(0);
  }

  public int getY() {
    return coordinates.get(1);
  }

  public int getZ() {
    return coordinates.get(2);
  }
}
