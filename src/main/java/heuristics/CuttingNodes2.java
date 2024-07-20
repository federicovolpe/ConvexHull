package heuristics;

import basic.CircularList;
import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import static utils.Constants.GraphConstants.*;

public class CuttingNodes2 implements Heuristic{
  private final CircularList<Edge> convexHull;
  private List<Node2D> whithinBorders;

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   * @param nodes all of the nodes
   */
  public CuttingNodes2 (int n, final List<Edge> convexHull, final List<Node2D> nodes) {
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    this.convexHull = new CircularList<>(convexHull);
    this.whithinBorders = nodes;


    while(this.convexHull.size() > n){
        applyCut();
        System.out.println("applying cut");
      }
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  private void applyCut(){
    Edge lowestA = null, lowestB = null; // edges formed by the nodes a-b-c
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;
    List<Edge> chPlusOne = new ArrayList<>(convexHull);
    chPlusOne.add(convexHull.getFirst());

    for (int i = 0; i < chPlusOne.size()-1; i++) {
      if(chPlusOne.get(i).calcAngle(chPlusOne.get(i+1)) < angle){
        angle = chPlusOne.get(i).calcAngle(chPlusOne.get(i+1));
        indexToModify = i;
        lowestA = chPlusOne.get(i);
        lowestB = chPlusOne.get(i+1);
      }
    }
    //System.out.println("lowest angle formed by "+lowestA.n1()+"-"+lowestA.n2()+"-"+lowestB.n2()+" with angle "+angle);


    Node2D centerOfMass = lowestA.getCenterOfMass(lowestB);

    //cutting the node
    convexHull.set(indexToModify, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);

    // extending the edges according tho the center of mass
    extendEdges(convexHull.get(indexToModify -1), convexHull.get(indexToModify), convexHull.get(indexToModify+2), centerOfMass);
  }

  private void extendEdges (Edge previous, Edge toExtend, Edge successor, Node2D centerOfMass){
    System.out.println("traslating "+toExtend);
    toExtend.traslate(centerOfMass);

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
