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
  private final List<Node2D> whithinBorders;

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
    System.out.println("copy : "+ this.convexHull);
    System.out.println("applying cut");
        applyCut();
    }
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  private void applyCut(){
    Edge lowestA = null, lowestB = null; // edges formed by the nodes a-b-c
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < convexHull.size(); i++) {
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) < angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
        lowestA = convexHull.get(i);
        lowestB = convexHull.get(i+1);
      }
    }

    Node2D centerOfMass = lowestA.getCenterOfMass(lowestB);

    //cutting the node
    convexHull.set(indexToModify, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);

    // traslating the new edge
    System.out.println("traslating "+ convexHull.get(indexToModify));
    convexHull.get(indexToModify).traslate(centerOfMass);

    // extending the edges according tho the center of mass
    extendEdges(convexHull.get(indexToModify -1), convexHull.get(indexToModify), convexHull.get(indexToModify+1));
  }

  private void extendEdges (Edge prev, Edge toExtend, Edge succ){
    System.out.println("extending "+ prev);
    Node2D intersectionPrev = toExtend.calcIntersectionWithLine(prev.getLineParameters()[0],
        prev.getLineParameters()[1],
        prev.getLineParameters()[2]);
    prev.setn2(intersectionPrev);
    toExtend.setn1(intersectionPrev);

    System.out.println("extending "+ succ);
    Node2D intersectionSucc = toExtend.calcIntersectionWithLine(succ.getLineParameters()[0],
        succ.getLineParameters()[1],
        succ.getLineParameters()[2]);
    succ.setn1(intersectionSucc);
    toExtend.setn2(intersectionSucc);
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
}
