package main;

import basic.Edge;
import basic.Facet;
import basic.Node2D;
import basic.Node3D;
import java.util.List;

public class JarvisMarch3D {

    public static List<Edge<Node3D>> calcConvexHull(List<Node3D> nodes) {

        List<Edge<Node3D>> convexHull = new LinkedList<>();

        Node3D lowest = findLowest(nodes);
        Facet currentF = lowest;
        Node3D previous = new Node2D(0, current.getX() - 1, current.getY());
        Node2D best;

        do {
            best = previous;
            double bestAngle = 0;
            for (Node2D n : nodes) {
                if (!n.equals(current))
                    if (angleBetweenLines(previous, current, n) > bestAngle) {
                        best = n;
                        bestAngle = angleBetweenLines(previous, current, n);
                    }
            }
            System.out.println("best successor to " + current + " : " + best + " with angle: " + bestAngle);
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

    private Facet findFirstFacet(List<Node3D> nodes) {
        Node3D lowest = findLowest(nodes);
        Node3D previous = new Node3D(0, current.getX() - 1, current.getY(), current.getZ());
        Node3D best = previous;
        double bestAngle = 0;
        for (Node3D n : nodes) {
            if (!n.equals(lowest))
                if (angleBetweenLines(previous, lowest, n) > bestAngle) {
                    best = n;
                    bestAngle = angleBetweenLines(previous, lowest, n);
                }
        }

    }

    /**
     * calculates the distance between a plane and a point
     * @param f facet representing the plane
     * @param n node
     */
    private static calcPlanePointDistance(Facet f, Node3D n) {

    }

    private static Node3D findLowest(List<Node3D> nodes) {
        Node3D lowest = nodes.getFirst();
        for (Node3D n : nodes) {
            lowest = (n.getY() < lowest.getY()) ? n : lowest;
        }
        return lowest;
    }

    /**
     * given two points and the line that intersect them
     * tells the angle between p1-p2 and p2p3
     */
    private static double angleBetweenLines(Node3D A, Node3D B, Node3D C) {
        double a = Math.sqrt(
                Math.pow((double) (A.getX() - B.getX()), 2) + Math.pow((double) (A.getY() - B.getY()), 2));
        double b = Math.sqrt(
                Math.pow((double) (B.getX() - C.getX()), 2) + Math.pow((double) (B.getY() - C.getY()), 2));
        double c = Math.sqrt(
                Math.pow((double) (C.getX() - A.getX()), 2) + Math.pow((double) (C.getY() - A.getY()), 2));

        double angle = Math.acos((Math.pow(a, 2) + Math.pow(b, 2) - Math.pow(c, 2)) / (2 * a * b));

        return angle;
    }
}
