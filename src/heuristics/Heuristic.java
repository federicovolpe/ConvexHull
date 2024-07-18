package heuristics;

import basic.Node;
import java.util.List;

public interface Heuristic {
    public Node GetHeuristic(List<Node> nodes);
}
