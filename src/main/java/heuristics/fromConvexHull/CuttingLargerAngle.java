package heuristics.fromConvexHull;

import basic.Edge;

import java.awt.*;
import java.util.List;

public class CuttingLargerAngle extends CuttingNodes {

  public CuttingLargerAngle(List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Edge lowestB = convexHull.get(selected+1);

    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);
  }

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
