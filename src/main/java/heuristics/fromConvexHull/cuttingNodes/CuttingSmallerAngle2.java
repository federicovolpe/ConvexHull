package heuristics.fromConvexHull.cuttingNodes;

import basic.Edge;
import java.awt.*;
import java.util.List;

public class CuttingSmallerAngle2 extends EdgeExtension {

  public CuttingSmallerAngle2(Color c) {
    super(c);
  }

  @Override
  protected int selectAngle() {
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < convexHull.size(); i++)
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) < angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }

    return indexToModify;
  }


}
