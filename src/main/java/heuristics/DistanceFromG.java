package heuristics;

import basic.CircularList;
import basic.Point2D;
import main.JarvisMarch;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * given a convex hull and a set of nodes this heuristic based algorithm tries to find an approximation of the
 * convex hull of n edges by selecting the most outer nodes of the convex hull from the center of mass
 */
public class DistanceFromG extends FromPoints {
    public DistanceFromG(Point2D centerOfMass, List<Point2D> allNodes, Color c){
        super(centerOfMass, allNodes, c);
    }

    private List<Point2D> selectNodes(int n){
        List<Point2D> chosenNodes = new ArrayList<>();
        allNodes.sort(Collections.reverseOrder(Comparator.comparing((Point2D node) -> node.calcDistance(centerOfMass))));

        int i = 0;
        while (chosenNodes.size() < n){
            if(i >= allNodes.size()) return chosenNodes;
            if(chosenNodes.size() > 2 ){
                if(!isNodeContained(allNodes.get(i), chosenNodes)) {
                    chosenNodes.add(allNodes.get(i));
                    //System.out.println("choosing node " + allNodes.get(i));
                    eliminateInsideNodes(chosenNodes);
                }
            } else{
                chosenNodes.add(allNodes.get(i));
                //System.out.println("choosing node " + allNodes.get(i));
            }
            i++;
        }
        return chosenNodes;
    }

    private boolean isNodeContained(Point2D n, List<Point2D> nodes){
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
    private void eliminateInsideNodes(List<Point2D> nodes){
        Iterator<Point2D> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            Point2D n = iterator.next();
            if (isNodeContained(n, nodes)) {
                // System.out.println("removing inside node : " + n);
                iterator.remove();
            }
        }
    }

    public void calcConvexHull(int n) {
        List<Point2D> chosenNodes = selectNodes(n);
        convexHull = new CircularList<>(new JarvisMarch(chosenNodes).getHullEdges());
    }

}
