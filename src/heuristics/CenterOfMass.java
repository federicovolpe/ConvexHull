package heuristics;

import basic.Edge;
import basic.Node;
import basic.Node2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * given a convex hull and a set of nodes this heuristic based algorithm tries to find an approximation of the
 * convex hull of n edges
 */
public class CenterOfMass implements Heuristic{

    /**
     * returns the center of mass of the given ConvexHull
     * @param convexHull list of edges of the convexhull
     * @return a node corrisponding to the center of mass of the convex hull
     */
    @Override
    public Node2D GetHeuristic(List<Edge<Node2D>> convexHull) {
        List<Node2D> nodes = getNodes(convexHull);

        int x = 0;
        int y = 0;

        for (Node2D n : nodes) {
            x += n.getX();
            y += n.getY();
        }
        x /= convexHull.size();
        y /= convexHull.size();

        //List<Integer> coord = new ArrayList<>(coordinates);
        return new Node2D(100, x, y);
    }

    /**
     * gets all the nodes from the edge representation of the convexHull
     * @param edges edges of the figure
     * @return the list of nodes
     */
    private List<Node2D> getNodes(List<Edge<Node2D>> edges){
        Set<Node2D> nodes = new HashSet<>();
        for(Edge<Node2D> e : edges) {
            nodes.add(e.n1());
            nodes.add(e.n2());
        }
        System.out.println("nodes of the ch:");
        for (Node n: nodes) {
            System.out.println(n);
        }
        return nodes.stream().toList();
    }

}
