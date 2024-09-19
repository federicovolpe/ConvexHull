package heuristics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import shapes.Polygon;

/**
 * a heuristical algorithm can be executed , drawn or recalculated
 * after being setted with a new set of data
 * every heuristical algorithm needs a heuristic, specified in its constructor
 * could be:
 * a point (center of mass)
 * a convex hull (for cutting algoritms)
 */
public abstract class Heuristic {
    protected Polygon poly;
    protected Color c;

    public Heuristic(Color c){
        this.c = c;
    }

    /**
     * calculate the convex hull of the given set of data
     * @param n number of desired edges
     */
    public abstract void calcConvexHull(int n);

    /**
     * drawing the nodes and the edges calculated by the heuristic
     */
    public void draw(Graphics g) {
        g.setColor(c);
        poly.draw(g);
    }

    /**
     * @return the vertices of the convex hull
     */
    public CircularList<Point2D> getHullNodes(){
        CircularList<Point2D> nodes = new CircularList<>();
        for (Edge e : poly.getEdges())
            nodes.add(e.n1());
        return nodes;
    }

    /**
     * return a list of nodes (vertices of the polygon)
     * @param hull list of all the edges of the polygon
     * @return list of vertices
     */
    protected List<Point2D> getHullNodes(List<Edge> hull) {
        List<Point2D> nodes = new ArrayList<>();
        for (Edge e : hull)
            nodes.add(e.n1());
        return nodes;
    }

    public void newData(Polygon p){
        this.poly = new Polygon(p);
    }
}
