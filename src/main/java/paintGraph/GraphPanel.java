package paintGraph;

import basic.Edge;
import basic.Node2D;
import heuristics.Heuristic;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import static utils.Constants.GraphConstants.*;

public class GraphPanel extends JPanel {
  private final List<Node2D> nodes;
  private final List<Edge> edges ;
  private final List<Heuristic> heuristics ;
  private final int letterYOff = -2, letterXOff = 2;
  protected LetterGenerator letterGenerator = new LetterGenerator();

  /**
   * initialize a graphpanel with all the nodes and the edges
   *
   * @param nodes nodes of the graph
   * @param edges edges of the graph
   */
  public GraphPanel(final List<Node2D> nodes, final List<Edge> edges, List<Heuristic> heuristics) {
    this.heuristics = heuristics;
    this.nodes = nodes;
    this.edges = edges;
    int biggestD = 0;
    for (Node2D n : nodes) {
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

    drawNodes(g);
    drawEdges(g);
    for (Heuristic h : heuristics) {
      h.draw(g);
    }

  }

  protected void drawNodes(Graphics g) {
    g.setColor(Color.BLUE);
    for (Node2D n : nodes) {
      int x = ORIGIN_X + n.getX() - POINT_DIM / 2;
      int y = ORIGIN_Y - n.getY() - POINT_DIM / 2;
      //g.drawString(letterGenerator.getNext(), x - letterXOff, y + letterYOff); // scrittura della lettera
      g.drawString(n.getIndex()+"", x - letterXOff, y + letterYOff);
      g.fillOval(x, y, POINT_DIM, POINT_DIM);
      //System.out.println("drawing node " + n.getIndex());
    }

  }

  protected void drawEdges(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;  // Cast to Graphics2D
    g2.setColor(Color.RED);
    g2.setStroke(new BasicStroke(3)); // Imposta la larghezza della linea a 2 (puoi cambiare questo valore)

    for (Edge e : edges) {
      //System.out.println("drawing line : " + e);
      g.drawLine(e.n1().getX() + ORIGIN_X,
          ORIGIN_Y - e.n1().getY(),
          e.n2().getX() + ORIGIN_X,
          ORIGIN_Y - e.n2().getY());
    }
    g2.setStroke(new BasicStroke(1));
  }
}