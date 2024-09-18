package heuristics.fromConvexHull.cuttingNodes;

import basic.CircularList;
import basic.Edge;
import java.awt.*;
import java.util.List;

public class CuttingSmallerAngle2 extends EdgeExtension {

  public CuttingSmallerAngle2(Color c) {
    super(c);
  }

  @Override
  protected int selectAngle() {
    CircularList<Edge> edges = poly.getEdges();
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < edges.size(); i++)
      if(edges.get(i).calcAngle(edges.get(i+1)) < angle){
        angle = edges.get(i).calcAngle(edges.get(i+1));
        indexToModify = i;
      }

    return indexToModify;
  }


}
