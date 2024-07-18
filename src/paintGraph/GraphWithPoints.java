package paintGraph;

import javax.swing.*;

public class GraphWithPoints extends JFrame {
    public GraphWithPoints(JPanel graph) {
        setTitle("Graph with Points");
        setResizable(false);
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(graph);
    }

}