package heuristics.fromConvexHull.cuttingNodes;

import basic.CircularList;
import basic.Edge;
import java.awt.Color;
import java.util.List;

/**
 * direct evolution of CuttingLarger angle.
 * It allows the previous Algorithm to become acceptable by extending the edges adjacent to the cut
 */

public class CuttingLargerAngle2 extends EdgeExtension {

  public CuttingLargerAngle2(Color c) {
    super(c);
  }

  @Override
  protected int selectAngle() {
    CircularList<Edge> edges = poly.getEdges();
    int indexToModify = -1;
    double angle = 0;

    for (int i = 0; i < edges.size(); i++)
      if(edges.get(i).calcAngle(edges.get(i+1)) > angle){
        angle = edges.get(i).calcAngle(edges.get(i+1));
        indexToModify = i;
      }

    return indexToModify;
  }


}
