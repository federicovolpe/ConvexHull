package heuristics.fromConvexHull.cuttingNodes;

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
    int indexToModify = -1;   // indice del lato da modificare
    double angle = Double.MAX_VALUE;

    for (int i = 0; i < convexHull.size(); i++) {
      if(convexHull.get(i).calcAngle(convexHull.get(i+1)) < angle){
        angle = convexHull.get(i).calcAngle(convexHull.get(i+1));
        indexToModify = i;
      }
    }

    return indexToModify;
  }

}
