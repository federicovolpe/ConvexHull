package heuristics;

import basic.Edge;
import basic.Node2D;

import java.util.List;

public interface Heuristic {
    Node2D GetHeuristic(List<Edge<Node2D>> nodes);
}
