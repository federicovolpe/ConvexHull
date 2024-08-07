package heuristics;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import java.awt.*;
import java.util.List;

public abstract class FromCH extends Heuristic {

  public FromCH(List<Edge> convexHull, Color c) {
    super(c);
    this.convexHull = new CircularList<>(convexHull);
  }

  public void newData(List<Edge> newConvexHull){
    this.convexHull = new CircularList<>(newConvexHull);
  }
}
