package heuristics;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.GraphConstants.*;
import static utils.Constants.GraphConstants.POINT_DIM;

/**
 * this strategy, starting from the convex hull tries to find an approximation by cutting the
 * nodes which generate the most acute internal angle
 *
 * for each cut the resulting convex hull is always smaller than the previous one
 * when there is a lot of difference between the number of desired edges and the actual edges the
 * resulting hull tends to be a bad approximation of the starting one
 */
public class CuttingNodes extends Heuristic {
  protected final List<Edge> convexHull;

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   */
  public CuttingNodes (int n, final List<Edge> convexHull) {
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    this.convexHull = new CircularList<>(convexHull);

    while(this.convexHull.size() > n){
      System.out.println("applying cut");
      applyCut();
    }
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Edge lowestB = convexHull.get(selected+1);

    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);
  }

  protected int selectAngle() {
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < convexHull.size(); i++) {
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) < angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }
    }

    return indexToModify;
  }

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
