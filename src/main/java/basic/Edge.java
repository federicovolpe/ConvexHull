package basic;

public class Edge {
  private Node2D n1;
  private Node2D n2;

  public Edge(Node2D n1, Node2D n2){
    this.n1 = n1;
    this.n2 = n2;
  }

  // Copy constructor
  public Edge(Edge edge) {
    this.n1 = new Node2D(edge.n1);
    this.n2 = new Node2D(edge.n2);
  }

  /**
   * calculates the angle generated by this edge and an adjacent one
   * @param other the adjacent edge
   * @return the angle
   */
  public double calcAngle(Edge other){
    if(!isConsecutive(other)) throw new IllegalArgumentException("edges are not successors n2 "+n2+" != "+other.n1);
    return n2.angleBetweenNodes(n1, other.n2);
  }

  /**
   * returns the center of mass of this edge and a subsequent one
   * @param other the adjacent one (n2 of this should be equal of n1 of other)
   * @return the center of mass
   */
  public Node2D getCenterOfMass(Edge other){
    if(!isConsecutive(other)) throw new IllegalArgumentException("edges are not successors n2 "+n2+" != "+other.n1);
    return new Node2D(-1,
        (n1.getX() + n2.getX() + other.n2.getX())/3,
        (n1.getY() + n2.getY() + other.n2.getY())/3);
  }

  /**
   * @return an array containing the parameters of the line corrisponding to this segment
   */
  public int[] getLineParameters() {
    int a = n1.getY() - n2.getY();
    int b = -(n1.getX() - n2.getX());
    int c = n1.getX()* n2.getY() - n1.getY() * n2.getX();
    return new int[]{a, b, c};
  }

  /**
   * generic mathematical formula for finding an intersection between two lines
   * @return the point of intersection of the tqo lines
   */
  public Node2D calcIntersectionWithLine(int a2, int b2, int c2){
    int[] param = getLineParameters();
    int a1 = param[0];
    int b1 = param[1];
    int c1 = param[2];
    double denominator = a1 * b2 - a2 * b1;
    double x = (b1 * c2 - b2 * c1) / denominator;
    double y = (a2 * c1 - a1 * c2) / denominator;

    return new Node2D(-1, (int)x, (int)y);
  }

  /**
   * traslate the edge in the direction of a point
   * the new center of the edges becomes the input node
   * the orientation remains the same
   * @param offsetNode the node to which traslate the edge
   */
  public void traslate (Node2D offsetNode){
    int middleX = (n1.getX() + n2.getX())/2;
    int middleY = (n1.getY() + n2.getY())/2;
    int offsetX = offsetNode.getX() - middleX;
    int offsetY = offsetNode.getY() - middleY;
    n1 = new Node2D(n1.index, n1.getX() + offsetX, n1.getY() + offsetY);
    n2 = new Node2D(n2.index, n2.getX() + offsetX, n2.getY() + offsetY);
  }
  public Node2D n1() {
    return n1;
  }
  public Node2D n2() {
    return n2;
  }
  private boolean isConsecutive(Edge other){
    return other.n1.equals(n2);
  }
  public void setn1(Node2D n1) {
    this.n1 = n1;
  }

  public void setn2(Node2D n2) {
    this.n2 = n2;
  }

  @Override
  public String toString() {
    return "edge: "+n1+" - "+n2;
  }

}
