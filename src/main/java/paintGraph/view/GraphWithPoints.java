package paintGraph.view;

import javax.swing.*;
import java.awt.*;

import static utils.Constants.GraphConstants.*;
public class GraphWithPoints extends JFrame implements OutputView {
    JPanel mainPanel = new JPanel(new BorderLayout());
    JPanel buttonPanel;
    JPanel graphPanel;
    public GraphWithPoints(JPanel buttonPanel, JPanel graph) {
        setTitle("Graph with Points");
        setResizable(false);

        // Set the window size based on panel dimensions
        setSize(WINDOW_SIZE + 200, WINDOW_SIZE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


        // Add button panel to the left (WEST) and graph panel to the center or east
        this.buttonPanel = buttonPanel;
        this.graphPanel = graph;
        mainPanel.add(this.buttonPanel, BorderLayout.WEST);
        mainPanel.add(this.graphPanel, BorderLayout.CENTER);  // Changed to CENTER

        // Add the main panel to the frame
        add(mainPanel);

        setVisible(true);
    }

    @Override
    public void set(GraphPanel g) {
        // Remove the old graphPanel if necessary
        if (this.graphPanel != null) {
            mainPanel.remove(this.graphPanel);
        }

        // Set the new graph panel
        this.graphPanel = g;

        // Add the new graph panel to the main panel (or wherever it should be placed)
        mainPanel.add(this.graphPanel, BorderLayout.CENTER); // Adjust the layout position as needed

        // Revalidate and repaint to update the UI
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}