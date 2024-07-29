package shapes;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;

import java.util.ArrayList;
import java.util.List;

public abstract class Shape {

  protected CircularList<Node2D> vertices;
  protected CircularList<Edge> edges;

  protected double getPerimeter(){
    double sum = 0;
    for (Edge edge : edges) sum += edge.getLength();
    return sum;
  }

  public List<Node2D> getSample(int n) {
    if(n < vertices.size()) throw new IllegalArgumentException("need atleast "+vertices.size()+ " points for a sample");
      List<Node2D> nodes = new ArrayList<>();
      for (Edge e: edges) {
        int samplen = (int)(n * e.getLength() / getPerimeter());
        nodes.addAll(e.getSample(samplen));
      }
    return nodes;
  }

}
