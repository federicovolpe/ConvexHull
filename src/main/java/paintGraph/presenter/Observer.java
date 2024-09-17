package paintGraph.presenter;

import heuristics.Heuristic;
import shapes.Polygon;

import java.util.List;

public interface Observer {
  void update (Polygon p, List<Heuristic> heuristics);
}
