package paintGraph;

import basic.Edge;
import basic.Node2D;
import java.util.List;
import javax.swing.*;

public class GraphWithPoints extends JFrame {
    public GraphWithPoints(final List<Node2D> nodes, final List<Edge<Node2D>> edges) {
        setTitle("Graph with Points");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        GraphPanel panel = new GraphPanel(nodes, edges);
        add(panel);
    }

}