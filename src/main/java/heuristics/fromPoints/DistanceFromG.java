package heuristics.fromPoints;

import basic.CircularList;
import basic.Point2D;
import main.JarvisMarch;
import shapes.Polygon;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * given a convex hull and a set of nodes this heuristic based algorithm tries to find an approximation of the
 * convex hull of n edges by selecting the most outer nodes of the convex hull from the center of mass
 */
public class DistanceFromG extends FromPoints {
    public DistanceFromG(Color c){
        super(c);
    }
    @Override
    public void calcConvexHull(int n) {
        List<Point2D> chosenNodes = selectNodes(n);
        poly = new Polygon(new JarvisMarch(chosenNodes).getHullNodes());
    }

    /**
     * select n furthest nodes from the center of mass
     * @param n number of nodes to select
     * @return list of the selected nodes
     */
    protected List<Point2D> selectNodes(int n){
        List<Point2D> chosenNodes = new ArrayList<>();
        sortPointsFromLargestDistance(centerOfMass);

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

    /**
     * sort the AllNodes list according to the furthest point from a given p
     * @param p point according to which sort the list
     */
    protected void sortPointsFromLargestDistance(Point2D p) {
        allNodes.sort(Collections.reverseOrder(Comparator.comparing((Point2D node) -> node.calcDistance(centerOfMass))));
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


}
