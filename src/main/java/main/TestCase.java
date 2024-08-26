package main;

import basic.Edge;
import basic.Point2D;
import java.util.List;

public record TestCase(List<Point2D> sample, int DesiredEdges, List<Edge> hullEdges, List<Point2D> hullNodes) {
}
