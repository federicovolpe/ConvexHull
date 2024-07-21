package main;

import basic.Edge;
import basic.Node2D;
import java.util.List;
import heuristics.CuttingNodes2;
import heuristics.Heuristic;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import utils.utilMethods;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        List<Node2D> nodes = utilMethods.rndNodesGenerator2D(50);
        
        System.out.println("generated:");
        System.out.println(nodes);

        List<Edge> edges = JarvisMarch.calcConvexHull(nodes);

        //Heuristic h = new CenterOfMass(edges);
        //Heuristic h = new CuttingNodes(5, edges, nodes);
        Heuristic h = new CuttingNodes2(5, edges, nodes);
        GraphPanel graph = new GraphPanel(nodes, edges, h);
        JFrame frame = new GraphWithPoints(graph);
        frame.setVisible(true);
    }
}
