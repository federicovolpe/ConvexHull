package main;

import basic.Edge;
import basic.Point2D;
import java.util.List;

/**
 * record for a test case for a single set of points associated to a polygon
 */
public record TestCase(List<Point2D> sample, int DesiredEdges, List<Edge> hullEdges, List<Point2D> hullNodes) {
}
