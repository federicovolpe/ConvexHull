package heuristics.fromConvexHull.cuttingNodes;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import java.awt.*;
import java.util.List;

/**
 * algorithms which uses the cutting nodes strategies to select a set of vertices
 * and then apply a traslatory transformation to the resulting convex hull with the purpuse to
 * include all the feasible points
 */
public abstract class EdgeExtension extends CuttingNodes {

  public EdgeExtension (Color c) {
    super(c);
  }

  /**
   * select a set of vertices one by one and for each applies the traslation of the resulting edge
   */
  protected void applyCut(){
    CircularList<Edge> edges = poly.getEdges();
    //------------------------------------    selecting the correct vertex      -----------------------------------
    int selected = selectAngle();
    Edge lowestA  = edges.get(selected);
    Point2D cuttedNode = lowestA.n2();
    Edge lowestB = edges.get(selected+1);

    //------------------------------------    cutting the node      -----------------------------------
    //System.out.println("selected node: " + convexHull.get(selected).n2());
    edges.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    Edge selectedEdge = edges.get(selected);
    //System.out.println("new edge : "+ selectedEdge);

    edges.remove(lowestB);
    int selectedEdgeIndex = edges.indexOf(selectedEdge);

    //------------------------------------    traslating the resulting edge      -----------------------------------
    //System.out.println("traslating "+ selectedEdge);
    selectedEdge.traslate(cuttedNode);

    //------------------------------------    extending the adjacent edges      -----------------------------------
    //System.out.println("extending edges: "+ convexHull.get(selectedEdgeIndex-1) +" "+convexHull.get(selectedEdgeIndex+1));
    extendEdges(edges.get(selectedEdgeIndex-1), edges.get(selectedEdgeIndex), edges.get(selectedEdgeIndex+1));
  }

  /**
   * extends the edjes adjacent to the middle one
   * @param prev previous edges (extending the second node)
   * @param toExtend edge to which extend the other two
   * @param succ successor edges (extending the starting node)
   */
  protected void extendEdges (Edge prev, Edge toExtend, Edge succ){
    prev.extendEdgeN2(toExtend);
    succ.extendEdgeN1(toExtend);
  }

}
