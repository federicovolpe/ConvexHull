package heuristics;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class CuttingNodes extends Heuristic{
  protected final List<Edge> convexHull;

  protected CuttingNodes(int n, final List<Edge> convexHull, Color c) {
    super(c);
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    this.convexHull = new CircularList<>(convexHull);

    while(this.convexHull.size() > n){
      System.out.println("applying cut");
      applyCut();
    }
  }

  protected abstract void applyCut();
  protected abstract int selectAngle();

  @Override
  public void draw(Graphics g) {
    g.setColor(c);
    drawEdges(g);
    drawNodes(g);
  }

  public void drawEdges(Graphics g){
    for (Edge e : convexHull) e.draw(g);
  }

  public void drawNodes(Graphics g) {
    for (Edge e : convexHull)
      e.n1().draw(g, false);
  }

  @Override
  public List<Node2D> getHullNodes() {
    List<Node2D> nodes = new ArrayList<>();
    for (Edge e : convexHull)
      nodes.add(e.n1());
    return nodes;
  }
}
