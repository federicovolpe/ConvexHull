
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
    }

    public List<Edge> getHullEdges(){
        return convexHull;
    }

    /**
     * @return a list containing all the convex hull vertices
     */
    public List<Point2D> getHullNodes() {
        List<Point2D> nodes = new ArrayList<>();
        for (Edge e : convexHull)
            nodes.add(e.n1());
        return nodes;
    }

    /**
     * finds the point of the given set which has the lower Y coordinate
     * @param points list of points to check
     * @return single point with the lowest Y coor
     */
    private static Point2D findLowest(List<Point2D> points) {
        Point2D lowest = points.get(0);
        for (Point2D n : points) {
            lowest = (n.getY() < lowest.getY()) ? n : lowest;
        }
        return lowest;
    }

    /**
     * gauss formula for calculating the area of a polygon
     * @return the area
     */
    public double calcArea() {
        List<Point2D> points = getHullNodes();
        int n = convexHull.size();
        double area = 0;

        for (int i = 0; i < n; i++) {
            Point2D current = points.get(i);
            Point2D next = points.get((i + 1) % n); // Next point, wrapping around to the first point at the end
            area += current.getX() * next.getY() - current.getY() * next.getX();
        }

        return Math.abs(area) / 2.0;
    }
}
