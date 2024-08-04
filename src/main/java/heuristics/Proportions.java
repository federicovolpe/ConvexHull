package heuristics;

import basic.CircularList;
import basic.Node2D;
import main.JarvisMarch;

import java.awt.*;
import java.util.List;

public class Proportions extends Heuristic {

  private final Node2D centerOfMass;
  private final List<Node2D> convexHull ;

  public Proportions (int n, List<Node2D> convexHull, List<Node2D> allNodes, Color c){
    super(c);
    centerOfMass = null;
    List<Node2D> chosenNodes = null;
    this.convexHull = new CircularList<>(new JarvisMarch(chosenNodes).getHullNodes());
  }
  @Override
  public void draw(Graphics g) {

  }

  @Override
  public List<Node2D> getHullNodes() {
    return null;
  }
}
