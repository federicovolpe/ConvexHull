package main;

import basic.Edge;
import basic.Node2D;
import java.util.List;
import heuristics.CenterOfMass;
import heuristics.Heuristic;
import paintGraph.GraphPanel;
import paintGraph.GraphWithPoints;
import utils.utilMethods;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        
        List<Node2D> nodes = utilMethods.rndNodesGenerator2D(20, 400);
        
        System.out.println("generated:");
        System.out.println(nodes);

        List<Edge<Node2D>> edges = JarvisMarch.calcConvexHull(nodes);

        Heuristic h = new CenterOfMass();
        Node2D heurNode = h.GetHeuristic(edges);
        System.out.println("heuristic found node: "+ heurNode);
        GraphPanel graph = new GraphPanel(nodes, edges, heurNode);
        JFrame frame = new GraphWithPoints(graph);
        frame.setVisible(true);
    }
}
