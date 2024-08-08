package heuristics.fromConvexHull.cuttingNodes;

import basic.Edge;
import heuristics.fromConvexHull.FromCH;
import java.awt.*;
import java.util.List;

/**
 * group of algorithms which uses a criteria based on selecting a certain set of vertices of the
 * given polygon
 */
public abstract class CuttingNodes extends FromCH {

  protected CuttingNodes(final List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  public void calcConvexHull(int n){
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    while(this.convexHull.size() > n) applyCut();
  }

  /**
   * select a single vertex of the polygon and proceed to make a new
   * edges that cuts out the selected v.
   */
  protected void applyCut(){
    int selected = selectAngle();
    Edge lowestA  = convexHull.get(selected);
    Edge lowestB = convexHull.get(selected+1);

    convexHull.set(selected, new Edge(lowestA.n1(), lowestB.n2()));
    convexHull.remove(lowestB);
  }

  /**
   * select the most promising point according to the heuristic class
   * @return a single vertex of the convex hull
   */
  protected abstract int selectAngle();

}
