package utils;

import basic.Node;
import basic.Node2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static utils.Constants.GraphConstants.GRAPH_BOUND;

public class utilMethods {

    private static final Random rnd = new Random();

    /**
     * to read and return a list of nodes
     * 
     * @param fileName file of the nodes
     * @return
     */
    public static List<Node> readNodesFromFile(String fileName) {
        List<Node> nodes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Salta la prima riga di intestazione
                }

                String[] parts = line.split("\\s+"); // Dividi la riga usando spazi bianchi come separatore

                int index = Integer.parseInt(parts[0]);
                int xPos = Integer.parseInt(parts[1]);
                int yPos = Integer.parseInt(parts[2]);

                Node node = new Node(index, xPos, yPos);
                System.out.println("\nnodo : index " + index + " yPos " + yPos);
                nodes.add(node);
            }
        } catch (IOException e) {
            System.out.println("erroreeeee\n");
            e.printStackTrace();
        }

        return nodes;
    }

    public static List<Node2D> rndNodesGenerator2D(int n) {
        List<Node2D> nodes = new ArrayList<>();

        while (nodes.size() < n) {
            Node2D node = new Node2D(nodes.size(), rnd.nextInt(- GRAPH_BOUND, GRAPH_BOUND), rnd.nextInt(- GRAPH_BOUND, GRAPH_BOUND));

            if (!nodes.contains(node))
                nodes.add(node);
        }
        return nodes;
    }

}
