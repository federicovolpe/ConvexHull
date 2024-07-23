package heuristics;

import basic.Node2D;
import java.util.*;
import java.awt.Graphics;

public abstract class Heuristic {

    // drawing different aspects generated by the heuristic
    public abstract void draw(Graphics g);

    public abstract List<Node2D> getHullNodes() ;
}
