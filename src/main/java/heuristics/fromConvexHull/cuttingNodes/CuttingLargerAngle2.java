package heuristics.fromConvexHull.cuttingNodes;

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
    int indexToModify = -1;
    double angle = 0;

    for (int i = 0; i < convexHull.size(); i++)
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) > angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }

    return indexToModify;
  }


}
