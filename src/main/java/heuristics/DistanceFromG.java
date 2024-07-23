package heuristics;

import basic.CircularList;
import basic.Node2D;
import main.JarvisMarch;

import java.awt.*;
import java.util.*;
import java.util.List;
import static utils.Constants.GraphConstants.*;

/**
 * given a convex hull and a set of nodes this heuristic based algorithm tries to find an approximation of the
 * convex hull of n edges by selecting the most outer nodes of the convex hull from the center of mass
 */
public class DistanceFromG extends Heuristic{
    private final Node2D centerOfMass;
    private final List<Node2D> convexHull ;

    public DistanceFromG(int n, List<Node2D> convexHull, List<Node2D> allNodes){
        centerOfMass = getG(convexHull);
        List<Node2D> chosenNodes = selectNodes(n, allNodes);
        this.convexHull = new CircularList<>(new JarvisMarch(chosenNodes).getHullNodes());
    }

    private List<Node2D> selectNodes(int n, List<Node2D> allNodes){
        List<Node2D> chosenNodes = new ArrayList<>();
        allNodes.sort(Collections.reverseOrder(Comparator.comparing((Node2D node) -> node.calcDistance(centerOfMass))));

        int i = 0;
        while (chosenNodes.size() < n){
            if(i >= allNodes.size()) return chosenNodes;
            if(chosenNodes.size() > 2 ){
                if(!isNodeContained(allNodes.get(i), chosenNodes)) {
                    chosenNodes.add(allNodes.get(i));
                    System.out.println("choosing node " + allNodes.get(i));
                    eliminateInsideNodes(chosenNodes);
                }
            } else{
                chosenNodes.add(allNodes.get(i));
            System.out.println("choosing node " + allNodes.get(i));}
            i++;
        }
        return chosenNodes;
    }

    private boolean isNodeContained(Node2D n, List<Node2D> nodes){
        for (int i = 0; i < nodes.size(); i++)
            for (int j = 0; j < nodes.size(); j++)
                if(j != i)
                    if(n.isContained(nodes.get(i), nodes.get(j), centerOfMass)) return true;
        return false;
    }

    /**
     * secure internal node removal
     * @param nodes nodes of the convex hull
     */
    private void eliminateInsideNodes(List<Node2D> nodes){
        Iterator<Node2D> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Node2D n = iterator.next();
            if (isNodeContained(n, nodes)) {
                System.out.println("removing inside node : " + n);
                iterator.remove();
            }
        }
    }


    /**
     * returns the center of mass of the given ConvexHull
     * @param convexHull list of edges of the convexhull
     */
    private Node2D getG(List<Node2D> convexHull) {
        int x = 0;
        int y = 0;
        for (Node2D n : convexHull) {
            x += n.getX();
            y += n.getY();
        }
        x /= convexHull.size();
        y /= convexHull.size();

        //List<Integer> coord = new ArrayList<>(coordinates);
        return new Node2D(100, x, y);
    }

    // about drawing the heuristic
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        for (Node2D n : convexHull) {
            int x = ORIGIN_X + n.getX() - (int)(POINT_DIM * .3);
            int y = ORIGIN_Y - n.getY() - (int)(POINT_DIM * .3);
            g.fillOval(x, y, (int) (POINT_DIM * .7), (int) (POINT_DIM * .7));
        }
        g.setColor(Color.BLACK);
        int x = ORIGIN_X + centerOfMass.getX() - POINT_DIM /2;
        int y = ORIGIN_Y - centerOfMass.getY() - POINT_DIM /2;
        g.fillOval(x, y, POINT_DIM, POINT_DIM);
        drawEdges(g);
    }

    protected void drawEdges(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;  // Cast to Graphics2D
        g2.setColor(Color.GREEN);

        for (Node2D n : convexHull) {
            //System.out.println("drawing line : " + e);
            g.drawLine(n.getX() + ORIGIN_X,
                ORIGIN_Y - n.getY(),
                convexHull.get(convexHull.indexOf(n) +1).getX() + ORIGIN_X,
                ORIGIN_Y - convexHull.get(convexHull.indexOf(n) +1).getY());
        }
    }

    @Override
    public List<Node2D> getHullNodes() {
        return convexHull;
    }

}
