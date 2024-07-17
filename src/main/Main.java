package main;

import basic.Edge;
import basic.Node2D;
import java.util.List;
import paintGraph.GraphWithPoints;
import paintGraph.LetterGenerator;
import utils.utilMethods;

public class Main {
    public static void main(String[] args) {
        
        List<Node2D> nodes = utilMethods.rndNodesGenerator2D(20, 400);
        
        System.out.println("generated:");
        System.out.println(nodes.toString());

        List<Edge<Node2D>> edges = JarvisMarch.calcConvexHull(nodes);

        GraphWithPoints frame = new GraphWithPoints(nodes, edges);
        frame.setVisible(true);
    }

    public static void test() {
        LetterGenerator lg = new LetterGenerator();
        for (int i = 0; i < 100; i++) {
            System.out.println(lg.getNext());
        }
        
    }
}
