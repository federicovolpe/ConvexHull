package paintGraph;

import javax.swing.*;
import static utils.Constants.GraphConstants.*;
public class GraphWithPoints extends JFrame {
    public GraphWithPoints(JPanel graph) {
        setTitle("Graph with Points");
        setResizable(false);
        setSize(WINDOW_SIZE, WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(graph);
        setVisible(true);
    }

}