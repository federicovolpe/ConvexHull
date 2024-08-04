
package main;

import basic.Edge;
import basic.Point2D;

import java.util.ArrayList;
import java.util.List;

public class JarvisMarch {
    private final List<Edge> convexHull = new ArrayList<>();

    public JarvisMarch(List<Point2D> points) {
        Point2D lowest = findLowest(points);
        Point2D current = lowest;
        Point2D previous = new Point2D(0, current.getX() - 1, current.getY());
        Point2D best;

        do {
            best = previous;
            double bestAngle = 0;
            for (Point2D n : points) {
                if (!n.equals(current))
                    if (current.angleBetweenNodes(previous, n) > bestAngle) {
                        best = n;
                        bestAngle = current.angleBetweenNodes(previous, n);
                    }
            }
            convexHull.add(new Edge(current, best));
            previous = current;
            current = best;
        } while (!current.equals(lowest));

        /*System.out.println("completed convex hull : ");
        for (Edge edge : convexHull) {
            System.out.println(edge);
        }*/
    }

    public List<Edge> getHullEdges(){
        return convexHull;
    }

    public List<Point2D> getHullNodes() {
        List<Point2D> nodes = new ArrayList<>();
        for (Edge e : convexHull)
            nodes.add(e.n1());
        return nodes;
    }

    private static Point2D findLowest(List<Point2D> nodes) {
        Point2D lowest = nodes.get(0);
        for (Point2D n : nodes) {
            lowest = (n.getY() < lowest.getY()) ? n : lowest;
        }
        return lowest;
    }
}
