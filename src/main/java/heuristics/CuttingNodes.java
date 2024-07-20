package heuristics;

import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.*;
import java.util.List;

import static utils.Constants.GraphConstants.ORIGIN_X;
import static utils.Constants.GraphConstants.ORIGIN_Y;

/**
 * this strategy, starting from the convex hull tries to find an approximation by cutting the
 * nodes which generate the most acute internal angle
 *
 * for each cut the resulting convex hull is always smaller than the previous one
 * when there is a lot of difference between the number of desired edges and the actual edges the
 * resulting hull tends to be a bad approximation of the starting one
 */
public class CuttingNodes implements Heuristic {
  private final List<Edge> convexHull;
  private List<Node2D> whithinBorders;

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   * @param nodes all of the nodes
   */
  public CuttingNodes (int n, final List<Edge> convexHull, final List<Node2D> nodes) {
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    this.convexHull = new ArrayList<>(convexHull);
    this.whithinBorders = nodes;


    while(true){System.out.println("convexHull : "+ this.convexHull.size() +" > "+n+" ?");
    if(this.convexHull.size() > n) {
      applyCut();
      System.out.println("applying cut");
    }else break;}
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  private void applyCut(){
    Edge lowestA = null, lowestB = null; // edges formed by the nodes a-b-c
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;
    List<Edge> chPlusOne = new ArrayList<>(convexHull);
    chPlusOne.add(convexHull.get(0));

    for (int i = 0; i < chPlusOne.size()-1; i++) {
      if(chPlusOne.get(i).calcAngle(chPlusOne.get(i+1)) < angle){
        angle = chPlusOne.get(i).calcAngle(chPlusOne.get(i+1));
        indexToModify = i;
        lowestA = chPlusOne.get(i);
        lowestB = chPlusOne.get(i+1);
      }
    }
    //System.out.println("lowest angle formed by "+lowestA.n1()+"-"+lowestA.n2()+"-"+lowestB.n2()+" with angle "+angle);
    convexHull.set(indexToModify, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);
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
  }
}
