package paintGraph;

import basic.Edge;
import basic.Node2D;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

class GraphPanel extends JPanel {
    private final int originX = 400;
    private final int originY = 400;
    private final int pointDim = 10;
    private final List<Node2D> nodes = new ArrayList<>();
    private final List<Edge<Node2D>> edges = new ArrayList<>();
    private final int letterYOff = -2, letterXOff = 2;
    protected LetterGenerator letterGenerator = new LetterGenerator();

    /**
     * initialize a graphpanel with all the nodes and the edges
     * @param nodes nodes of the graph
     * @param edges edges of the graph
     */
    public GraphPanel(final List<Node2D> nodes, final List<Edge<Node2D>> edges) {
        int biggestD = 0;
        for (Node2D n : nodes) {
            biggestD = (Math.abs(n.getY()) > biggestD) ? Math.abs(n.getX()) : biggestD;
            biggestD = (Math.abs(n.getY()) > biggestD) ? Math.abs(n.getY()) : biggestD;
        }

        float scale = biggestD / 400.0f;
        initNodesAndEdges(nodes, edges, scale);
        System.out.println("scale: 400/" + biggestD +" = "+scale);
    }

    private void initNodesAndEdges (List<Node2D> nodes, List<Edge<Node2D>> edges, float scale){
        for (Node2D n : nodes)
            this.nodes.add(scaleNode(n, scale));
        for (Edge<Node2D> e : edges)
            this.edges.add(new Edge<>(scaleNode(e.n1(), scale), scaleNode(e.n2(), scale)));
    }

    private Node2D scaleNode(Node2D n, float scale) {
        return new Node2D(n.getIndex(), (int)(n.getX() * scale), (int)(n.getY() * scale));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the axes
        g.drawLine(10, originY, originX * 2 - 10, originY); // X-axis
        g.drawLine(originX, 10, originX, originY * 2 - 10); // Y-axis

        // Draw points
        
        drawNodes(g);
        drawEdges(g);
    }

    protected void drawNodes(Graphics g){
        g.setColor(Color.BLUE);
        for (Node2D n : nodes) {
            int x = originX + n.getX() - pointDim / 2;
            int y = originY - n.getY() - pointDim / 2;
            g.drawString(letterGenerator.getNext(), x - letterXOff, y + letterYOff);
            g.fillOval( x, y, 10, 10);
        }
    }

    protected void drawEdges (Graphics g){
        g.setColor(Color.RED);
        for (Edge<Node2D> e : edges) {
            System.out.println("drawing line : " + e);
            g.drawLine(e.n1().getX() + originX,
                    originY - e.n1().getY(),
                    e.n2().getX() + originX,
                    originY - e.n2().getY());
            }
    }
}