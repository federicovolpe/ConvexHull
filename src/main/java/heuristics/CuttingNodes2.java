package heuristics;

import basic.Edge;
import basic.Node2D;
import java.util.List;

public class CuttingNodes2 extends CuttingNodes{

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   */
  public CuttingNodes2 (int n, final List<Edge> convexHull) {
    super(n, convexHull);
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Edge lowestB = convexHull.get(selected+1);

    //cutting the node
    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);

    // traslating the new edge
    Node2D centerOfMass = lowestA.getCenterOfMass(lowestB);
     System.out.println("traslating "+ convexHull.get(selected));
    convexHull.get(selected).traslate(centerOfMass);

    // extending the edges according tho the center of mass
    extendEdges(convexHull.get(selected -1), convexHull.get(selected), convexHull.get(selected+1));
  }


  protected void extendEdges (Edge prev, Edge toExtend, Edge succ){
    // System.out.println("extending "+ prev);
    Node2D intersectionPrev = toExtend.calcIntersectionWithLine(prev.getLineParameters()[0],
        prev.getLineParameters()[1],
        prev.getLineParameters()[2]);
    prev.setn2(intersectionPrev);
    toExtend.setn1(intersectionPrev);

    // System.out.println("extending "+ succ);
    Node2D intersectionSucc = toExtend.calcIntersectionWithLine(succ.getLineParameters()[0],
        succ.getLineParameters()[1],
        succ.getLineParameters()[2]);
    succ.setn1(intersectionSucc);
    toExtend.setn2(intersectionSucc);
  }

}
