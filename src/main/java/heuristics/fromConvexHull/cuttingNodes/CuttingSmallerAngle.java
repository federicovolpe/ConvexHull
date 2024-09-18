package heuristics.fromConvexHull.cuttingNodes;

import basic.CircularList;
import basic.Edge;

import java.awt.*;

/**
 * this strategy, starting from the convex hull tries to find an approximation by cutting the
 * nodes which generate the most acute internal angle
 *
 * for each cut the resulting convex hull is always smaller than the previous one
 * when there is a lot of difference between the number of desired edges and the actual edges the
 * resulting hull tends to be a bad approximation of the starting one
 */
public class CuttingSmallerAngle extends CuttingNodes {

  public CuttingSmallerAngle(Color c) {
    super(c);
  }

  @Override
  protected int selectAngle() {
    CircularList<Edge> edges = poly.getEdges();
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < edges.size(); i++) {
      if(edges.get(i).calcAngle(edges.get(i+1)) < angle){
        angle = edges.get(i).calcAngle(edges.get(i+1));
        indexToModify = i;
      }
    }

    return indexToModify;
  }

}
