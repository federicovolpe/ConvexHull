package heuristics;

import basic.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CenterOfMass implements Heuristic{

    @Override
    public Node GetHeuristic(List<Node> nodes) {
        int dim = nodes.getFirst().getDim();
        Integer[] coordinates = new Integer[dim];

        for (Node n : nodes) {
            for (int d = 0; d < dim; d++) {
                coordinates[d] += n.getCoor(d);
            }
        }
        // average of all the coordinates
        for (int i = 0; i < dim; i++) {
            coordinates[i] /= dim;
        }
         = new ArrayList<>(Arrays.asList(coordinates);
        return new Node(, nodes.size());
    }

}
