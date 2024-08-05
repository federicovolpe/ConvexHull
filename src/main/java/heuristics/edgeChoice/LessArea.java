package heuristics.edgeChoice;

import basic.CircularList;
import basic.Edge;
import heuristics.Heuristic;

import java.awt.*;
import java.util.List;

/*
 * considering all the edges, the chosen are the one which if prolungated
 * create the smallest outside area from the polygon
 */
public class LessArea extends Heuristic {

  public LessArea(List<Edge> convexHull, Color c) {
    super(c);
    this.convexHull = new CircularList<>(convexHull);
  }

  @Override
  public void calcConvexHull(int n) {
    //extend(selectEdges(n));
  }

  private void extendEdges (List<Edge> edges) {

  }
}
