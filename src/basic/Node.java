package basic;

public class Node {

  private int index, xPos, yPos;

  public Node(int index, int xPos, int yPos) {
    this.index = index;
    this.xPos = xPos;
    this.yPos = yPos;
  }

  @Override
  public String toString() {
    return "node : " + index + "(" + xPos + ", " + yPos + ")";
  }

  public int getIndex() {
    return index;
  }

  public double calcDistance(Node other) {
    return Math.sqrt(Math.pow((double) (other.xPos - this.xPos), 2) + Math.pow((double) (other.yPos - yPos), 2));
  }

  public boolean equals(Node other) {
    return this.index == other.index;
  }

  public int getxPos() {
    return xPos;
  }

  public int getyPos() {
    return yPos;
  }
}
