package heuristics;

import basic.Edge;

import java.util.List;

public class CuttingLargerAngle extends CuttingSmallerAngle {

  /**
   * constructor which instanciates all the computation
   *
   * @param n          number of desired edges
   * @param convexHull official convex hull
   */
  public CuttingLargerAngle(int n, List<Edge> convexHull) {
    super(n, convexHull);
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
