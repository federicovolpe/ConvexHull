package utils;

import basic.Point;
import basic.Point2D;
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
     * to read and return a list of point
     * 
     * @param fileName file of the points
     * @return
     */
    public static List<Point> readNodesFromFile(String fileName) {
        List<Point> points = new ArrayList<>();

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

                Point point = new Point(index, xPos, yPos);
                System.out.println("\nnodo : index " + index + " yPos " + yPos);
                points.add(point);
            }
        } catch (IOException e) {
            System.out.println("erroreeeee\n");
            e.printStackTrace();
        }

        return points;
    }

    public static List<Point2D> rndNodesGenerator2D(int n) {
        List<Point2D> points = new ArrayList<>();

        while (points.size() < n) {
            Point2D point = new Point2D(points.size(), GRAPH_BOUND);
            if (!points.contains(point))
                points.add(point);
        }
        return points;
    }

}
