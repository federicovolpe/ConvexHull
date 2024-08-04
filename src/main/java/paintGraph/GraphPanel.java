package paintGraph;

import basic.Edge;
import basic.Point2D;
import heuristics.Heuristic;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import static utils.Constants.GraphConstants.*;

public class GraphPanel extends JPanel {
  private final List<Point2D> nodes;
  private final List<Edge> edges ;
  private final List<Heuristic> heuristics ;
  protected LetterGenerator letterGenerator = new LetterGenerator();

  /**
   * initialize a graphpanel with all the nodes and the edges
   *
   * @param nodes nodes of the graph
   * @param edges edges of the graph
   */
  public GraphPanel(final List<Point2D> nodes, final List<Edge> edges, List<Heuristic> heuristics) {
    this.heuristics = heuristics;
    this.nodes = nodes;
    this.edges = edges;
    int biggestD = 0;
    for (Point2D n : nodes) {
      biggestD = (Math.abs(n.getY()) > biggestD) ? Math.abs(n.getX()) : biggestD;
      biggestD = Math.max(Math.abs(n.getY()), biggestD);
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw the axes
    g.drawLine(10, ORIGIN_Y, ORIGIN_X * 2 - 10, ORIGIN_Y); // X-axis
    g.drawLine(ORIGIN_X, 10, ORIGIN_X, ORIGIN_Y * 2 - 10); // Y-axis

    // Draw points
    Graphics2D g2 = (Graphics2D) g;  // Cast to Graphics2D
    g2.setColor(Color.RED);
    drawNodes(g2);
    drawEdges(g2);

    for (Heuristic h : heuristics) {
      h.draw(g);
    }

  }

  protected void drawNodes(Graphics g) {
    for (Point2D n : nodes) n.draw(g, true);
  }

  protected void drawEdges(Graphics2D g) {
    g.setStroke(new BasicStroke(3));
    for (Edge e : edges) e.draw(g);
    g.setStroke(new BasicStroke(1));
  }
}