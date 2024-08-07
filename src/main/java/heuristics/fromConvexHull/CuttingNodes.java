package heuristics.fromConvexHull;

import basic.Edge;
import heuristics.FromCH;
import java.awt.*;
import java.util.List;

public abstract class CuttingNodes extends FromCH {

  protected CuttingNodes(final List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  public void calcConvexHull(int n){
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    while(this.convexHull.size() > n) applyCut();
  }

  protected abstract void applyCut();
  protected abstract int selectAngle();

}
