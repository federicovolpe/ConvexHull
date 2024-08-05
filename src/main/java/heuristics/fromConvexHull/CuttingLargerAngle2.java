package heuristics.fromConvexHull;

import basic.Edge;

import java.awt.*;
import java.util.List;

public class CuttingLargerAngle2 extends EdgeExtension {

  public CuttingLargerAngle2(final List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  @Override
  protected int selectAngle() {
    int indexToModify = -1;   // indice del lato da modificare
    double angle = 0;

    for (int i = 0; i < convexHull.size(); i++)
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) > angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }

    return indexToModify;
  }


}
