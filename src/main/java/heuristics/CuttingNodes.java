package heuristics;

import basic.CircularList;
import basic.Edge;
import java.awt.*;
import java.util.List;

public abstract class CuttingNodes extends Heuristic{

  protected CuttingNodes(final List<Edge> convexHull, Color c) {
    super(c);
    this.convexHull = new CircularList<>(convexHull);
  }

  public void calcConvexHull(int n){
    if(n < 3) throw new IllegalArgumentException("number of edges must be greater than 3: "+ n);
    while(this.convexHull.size() > n){
      System.out.println("applying cut");
      applyCut();
    }
  }

  public void newData(List<Edge> newConvexHull){
    this.convexHull = new CircularList<>(newConvexHull);
  }
  protected abstract void applyCut();
  protected abstract int selectAngle();

}
