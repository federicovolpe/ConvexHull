package heuristics.fromConvexHull;

import java.awt.Color;
import java.util.List;

import basic.CircularList;
import basic.Edge;
import heuristics.Heuristic;

public abstract class FromCH extends Heuristic {

  public FromCH(List<Edge> convexHull, Color c) {
    super(c);
    this.convexHull = new CircularList<>(convexHull);
  }

  /**
   * resets the current algorithm and initializes it with new set of data
   * @param newConvexHull new set of data
   */
  public void newData(List<Edge> newConvexHull){
    this.convexHull = new CircularList<>(newConvexHull);
  }
}
