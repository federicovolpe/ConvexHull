package heuristics;

import basic.Edge;
import basic.Node2D;
import java.util.List;

public class CuttingSmallerAngle2 extends CuttingSmallerAngle {

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   */
  public CuttingSmallerAngle2(int n, final List<Edge> convexHull) {
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
    prev.extendEdgeN2(toExtend);
    succ.extendEdgeN1(toExtend);
  }
  @Override
  protected int selectAngle() {
    int indexToModify = -1;   // indice del lato da modificare
    double angle = 0;

    for (int i = 0; i < convexHull.size(); i++) {
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) > angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }
    }

    return indexToModify;
  }
}
