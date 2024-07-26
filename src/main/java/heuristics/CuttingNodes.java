package heuristics;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

public abstract class CuttingNodes implements Heuristic{
  protected final List<Edge> convexHull;

  protected CuttingNodes(int n, final List<Edge> convexHull) {
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
    g.setColor(Color.GREEN);
    for (Edge e : convexHull) {
      //System.out.println("drawing line : " + e);
      g.drawLine(e.n1().getX() + ORIGIN_X,
          ORIGIN_Y - e.n1().getY(),
          e.n2().getX() + ORIGIN_X,
          ORIGIN_Y - e.n2().getY());
    }
    drawNewNodes(g);
  }

  public void drawNewNodes(Graphics g) {
    g.setColor(Color.GREEN);
    for (Edge e : convexHull) {
      int x = ORIGIN_X + e.n1().getX() - (int)(POINT_DIM * .3);
      int y = ORIGIN_Y - e.n1().getY() - (int)(POINT_DIM * .3);
      g.drawString(e.n1().getIndex()+"", x - 2, y - 2);
      g.fillOval(x, y, (int) (POINT_DIM * .7), (int) (POINT_DIM * .7));
    }
  }

  @Override
  public List<Node2D> getHullNodes() {
    List<Node2D> nodes = new ArrayList<>();
    for (Edge e : convexHull)
      nodes.add(e.n1());
    return nodes;
  }
}
