package heuristics;

import basic.Edge;
import basic.Node;
import basic.Node2D;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.Constants.GraphConstants.*;

/**
 * given a convex hull and a set of nodes this heuristic based algorithm tries to find an approximation of the
 * convex hull of n edges by selecting the most outer nodes of the convex hull from the center of mass
 */
public class CenterOfMass implements Heuristic{
    Node2D centerOfMass;

    /**
     * returns the center of mass of the given ConvexHull
     * @param convexHull list of edges of the convexhull
     */
    public CenterOfMass(List<Edge> convexHull) {
        List<Node2D> nodes = getNodes(convexHull);

        int x = 0;
        int y = 0;

        for (Node2D n : nodes) {
            x += n.getX();
            y += n.getY();
        }
        x /= convexHull.size();
        y /= convexHull.size();

        //List<Integer> coord = new ArrayList<>(coordinates);
        centerOfMass = new Node2D(100, x, y);
    }



    /**
     * gets all the nodes from the edge representation of the convexHull
     * @param edges edges of the figure
     * @return the list of nodes
     */
    private List<Node2D> getNodes(List<Edge> edges){
        Set<Node2D> nodes = new HashSet<>();
        for(Edge e : edges) {
            nodes.add(e.n1());
            nodes.add(e.n2());
        }
        System.out.println("nodes of the ch:");
        for (Node n: nodes) {
            System.out.println(n);
        }
        return nodes.stream().toList();
    }

    // about drawing the heuristic
    @Override
    public void draw(Graphics g) {
        drawCenterOfMass(g);
    }

    //TODO: complete with drawing the polytope
    private void drawCenterOfMass(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(ORIGIN_X + centerOfMass.getX() - POINT_DIM / 2,
            ORIGIN_Y - centerOfMass.getY() - POINT_DIM / 2,
            POINT_DIM,
            POINT_DIM);
    }

}
