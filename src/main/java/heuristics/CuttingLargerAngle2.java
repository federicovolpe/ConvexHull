package heuristics;

import basic.Edge;
import basic.Node2D;

import java.awt.*;
import java.util.List;

public class CuttingLargerAngle2 extends CuttingLargerAngle {

  public CuttingLargerAngle2(final List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  /**
   * from all the nodes in the convex hull remove the one with the most obtuse angle
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Node2D cuttedNode = lowestA.n2();
    Edge lowestB = convexHull.get(selected+1);

    //cutting the node
    // System.out.println("selected node: " + convexHull.get(selected).n2());
    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    Edge selectedEdge = convexHull.get(selected);
    // System.out.println("new edge : "+ selectedEdge);

    convexHull.remove(lowestB);
    int selectedEdgeIndex = convexHull.indexOf(selectedEdge);

    // traslating the new edge
    // System.out.println("traslating "+ selectedEdge);
    selectedEdge.traslate(cuttedNode);

    // extending the edges according tho the center of mass
    // System.out.println("extending edges: "+ convexHull.get(selectedEdgeIndex-1) +" "+convexHull.get(selectedEdgeIndex+1));
    extendEdges(convexHull.get(selectedEdgeIndex-1), convexHull.get(selectedEdgeIndex), convexHull.get(selectedEdgeIndex+1));
  }


  protected void extendEdges (Edge prev, Edge toExtend, Edge succ){
    prev.extendEdgeN2(toExtend);
    succ.extendEdgeN1(toExtend);
  }
}
