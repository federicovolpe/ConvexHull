package heuristics;

import basic.Edge;
import basic.Node2D;
import java.util.List;

public class CuttingLargerAngle2 extends CuttingLargerAngle {

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   */
  public CuttingLargerAngle2(int n, final List<Edge> convexHull) {
    super(n, convexHull);
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Node2D cuttedNode = lowestA.n2();
    Edge lowestB = convexHull.get(selected+1);

    //cutting the node
    System.out.println("selected node: " + convexHull.get(selected).n2());
    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    Edge selectedEdge = convexHull.get(selected);
    System.out.println("new edge : "+ selectedEdge);

    convexHull.remove(lowestB);
    int selectedEdgeIndex = convexHull.indexOf(selectedEdge);

    // traslating the new edge
    System.out.println("traslating "+ selectedEdge);
    selectedEdge.traslate(cuttedNode);

    // extending the edges according tho the center of mass
    System.out.println("extending edges: "+ convexHull.get(selectedEdgeIndex-1) +" "+convexHull.get(selectedEdgeIndex+1));
    extendEdges(convexHull.get(selectedEdgeIndex-1), convexHull.get(selectedEdgeIndex), convexHull.get(selectedEdgeIndex+1));
  }


  protected void extendEdges (Edge prev, Edge toExtend, Edge succ){
    Node2D intersectionPrev = toExtend.calcIntersectionWithLine(prev.getLineParameters()[0],
        prev.getLineParameters()[1],
        prev.getLineParameters()[2]);
    prev.setn2(intersectionPrev);
    toExtend.setn1(intersectionPrev);

    Node2D intersectionSucc = toExtend.calcIntersectionWithLine(succ.getLineParameters()[0],
        succ.getLineParameters()[1],
        succ.getLineParameters()[2]);
    succ.setn1(intersectionSucc);
    toExtend.setn2(intersectionSucc);
  }

}
