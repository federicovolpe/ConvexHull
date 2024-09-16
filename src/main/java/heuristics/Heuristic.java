package heuristics;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;

/**
 * a heuristical algorithm can be executed , drawn or recalculated
 * after being setted with a new set of data
 * every heuristical algorithm needs a heuristic, specified in its constructor
 * could be:
 * a point (center of mass)
 * a convex hull (for cutting algoritms)
 */
public abstract class Heuristic {

    protected CircularList<Edge> convexHull;
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
        System.out.println("printing...");
        g.setColor(c);
        drawEdges((Graphics2D) g);
        drawNodes(g);
    }

    /**
     * draws all the edges of the convex hull
     * @param g graphics to use
     */
    public void drawEdges(Graphics2D g){
        g.setStroke(new BasicStroke(2));
        for (Edge e : convexHull) e.draw(g);
        g.setStroke(new BasicStroke(1));
    }

    /**
     * draws all the points interested in the calculation of the convex hull
     * @param g graphics to use
     */
    public void drawNodes(Graphics g) {
        for (Edge e : convexHull)
            e.n1().draw(g, false);
    }

    /**
     * @return the vertices of the convex hull
     */
    public CircularList<Point2D> getHullNodes(){
        CircularList<Point2D> nodes = new CircularList<>();
        for (Edge e : convexHull)
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
}
