package heuristics;

import basic.Node2D;
import java.awt.*;
import java.util.List;

/**
 * a heuristical algorithm can be executed , drawn or recalculated
 * after being setted with a new set of data
 */
public abstract class Heuristic {

    protected Color c;
    protected boolean calculated = false;

    public Heuristic(Color c){
        this.c = c;
    }

    public abstract void calcConvexHull();
    public abstract void newData();

    /**
     * drawing the nodes and the edges calculated by the heuristic
     */
    public abstract void draw(Graphics g);

    public abstract List<Node2D> getHullNodes() ;
}
