package shapes;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;
import java.util.List;

public class Triangle extends Shape{

  public Triangle (Node2D A, Node2D B, Node2D C){
    vertices = new CircularList<>(List.of(A,B,C));
    edges = new CircularList<>();
    edges.add(new Edge(A, B));
    edges.add(new Edge(B, C));
    edges.add(new Edge(C, A));
  }

}
