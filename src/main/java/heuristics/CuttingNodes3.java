package heuristics;

import basic.Edge;
import basic.Node2D;
import java.util.List;

public class CuttingNodes3 extends CuttingNodes2 {

  /**
   * constructor which instanciates all the computation
   * @param n number of desired edges
   * @param convexHull official convex hull
   * @param nodes all of the nodes
   */
  public CuttingNodes3 (int n, final List<Edge> convexHull, final List<Node2D> nodes) {
    super(n, convexHull, nodes);
  }

  /**
   * from all the nodes in the convex hull remove the one with the most acute angle
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Edge lowestB = convexHull.get(selected+1);


    Node2D centerOfMass = lowestA.getCenterOfMass(lowestB);

    //cutting the node
    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);

    // traslating the new edge
    System.out.println("traslating "+ convexHull.get(selected));
    convexHull.get(selected).traslate(centerOfMass);

    // extending the edges according tho the center of mass
    extendEdges(convexHull.get(selected -1), convexHull.get(selected), convexHull.get(selected+1));
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
