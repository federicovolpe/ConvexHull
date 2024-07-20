
package main;

import basic.Edge;
import basic.Node2D;
import java.util.LinkedList;
import java.util.List;

public class JarvisMarch {
    public static List<Edge> calcConvexHull(List<Node2D> nodes) {

        List<Edge> convexHull = new LinkedList<>();

        Node2D lowest = findLowest(nodes);
        Node2D current = lowest;
        Node2D previous = new Node2D(0, current.getX() - 1, current.getY());
        Node2D best;

        do {
            best = previous;
            double bestAngle = 0;
            for (Node2D n : nodes) {
                if (!n.equals(current))
                    if (current.angleBetweenNodes(previous, n) > bestAngle) {
                        best = n;
                        bestAngle = current.angleBetweenNodes(previous, n);
                    }
            }
            convexHull.add(new Edge(current, best));
            previous = current;
            current = best;
            best = previous;
        } while (!current.equals(lowest));

        System.out.println("completed convex hull : ");
        for (Edge edge : convexHull) {
            System.out.println(edge);
        }
        return convexHull;
    }

    private static Node2D findLowest(List<Node2D> nodes) {
        Node2D lowest = nodes.get(0);
        for (Node2D n : nodes) {
            lowest = (n.getY() < lowest.getY()) ? n : lowest;
        }
        return lowest;
    }
}
