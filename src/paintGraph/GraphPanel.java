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

    /**
     * initialize a graphpanel with all the nodes and the edges
     * @param nodes nodes of the graph
     * @param edges edges of the graph
     */
    public GraphPanel(List<Node2D> nodes, List<Edge<Node2D>> edges) {
        int biggestD = 0;
        for (Node2D n : nodes) {
            biggestD = (Math.abs(n.getY()) > biggestD) ? Math.abs(n.getX()) : biggestD;
            biggestD = (Math.abs(n.getY()) > biggestD) ? Math.abs(n.getY()) : biggestD;
        }

        int scale = (int) (400 / biggestD) - 1;
        initNodesAndEdges(nodes, edges, scale);

        System.out.println("scale: " + scale);
    }

    private void initNodesAndEdges (List<Node2D> nodes, List<Edge<Node2D>> edges, int scale){
        for (Node2D n : nodes)
            this.nodes.add(scaleNode(n, scale));
        for (Edge<Node2D> e : edges)
            this.edges.add(new Edge<>(scaleNode(e.n1(), scale), scaleNode(e.n2(), scale)));
    }

    private Node2D scaleNode(Node2D n, int scale) {
        return new Node2D(n.getIndex(), n.getX() * scale, n.getY() * scale);
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
        for (Node2D n : nodes) 
            g.fillOval(originX + n.getX() - pointDim / 2, originY - n.getY() - pointDim / 2, 10, 10);
        
    }

    protected void drawEdges (Graphics g){
        g.setColor(Color.RED);
        for (Edge<Node2D> e : edges) 
            g.drawLine(e.n1().getY() + originX,
                    originY - e.n1().getY(),
                    e.n2().getX() + originX,
                    originY - e.n2().getY());
    }
}