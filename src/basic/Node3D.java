package basic;

public class Node3D {
    private int index, xPos, yPos, zPos;

  public Node3D(int index, int xPos, int yPos, int zPos) {
    this.index = index;
    this.xPos = xPos;
    this.yPos = yPos;
    this.zPos = zPos;
  }
  

  @Override
  public String toString() {
    return "node : " + index + "(" + xPos + ", " + yPos + ")";
  }

  public int getIndex() {
    return index;
  }

  public double calcDistance(Node3D other) {
    return Math.sqrt(
        Math.pow((double) (other.xPos - this.xPos), 2) + 
        Math.pow((double) (other.yPos - yPos), 2) +
        Math.pow((double) (other.zPos - zPos), 2));
  }

  public boolean equals(Node3D other) {
    return this.index == other.index;
  }

  public int getxPos() {
    return xPos;
  }

  public int getyPos() {
    return yPos;
  }

  public int getzPos() {
    return zPos;
  }
}
