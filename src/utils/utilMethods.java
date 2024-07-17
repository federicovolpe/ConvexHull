package utils;

import basic.Node;
import basic.Node2D;
import basic.Node3D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class utilMethods {

    private static Random rnd = new Random();

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

    public static List<Node2D> rndNodesGenerator2D(int n, int bound) {
        List<Node2D> nodes = new ArrayList<>();

        while (nodes.size() < n) {
            Node2D node = new Node2D(nodes.size(), rnd.nextInt(- bound, bound), rnd.nextInt(- bound, bound));

            if (!nodes.contains(node)) 
                nodes.add(node);
        }
        return nodes;
    }

    public static List<Node3D> rndNodesGenerator3D(int n, int limit) {

        // TODO: non devono essere generati nodi con coordinate uguali non coordinate
        // uguali
        // come fatto in questo caso

        List<Integer> generated = new ArrayList<>();
        List<Node3D> nodes = new ArrayList<>();
        /*int index = 1;
        while (nodes.size() < n) {
            int x = rnd.nextInt(-40, 40);
            int y = rnd.nextInt(-40, 40);
            int z = rnd.nextInt(-40, 40);
            if (!generated.contains(x) || !generated.contains(y) || !generated.contains(z)) {
                nodes.add(new Node3d(index, x, y));
                index++;
            }
        }*/
        return nodes;
    }

}
