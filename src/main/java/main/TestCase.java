package main;

import basic.Edge;
import basic.Point2D;
import shapes.Polygon;

import java.util.ArrayList;
import java.util.List;

public record TestCase(Polygon p,
                       int DesiredEdges,
                       List<Edge> hullEdges,
                       List<Point2D> hullNodes,
                       int regularity) {

  public TestCase(Polygon p, int DesiredEdges) {
    this(p, DesiredEdges, p.getEdges(), p.getVertices(), coefficienteDiVariazione(p.getEdges()));
  }

  public static double media(List<Double> valori) {
    double somma = 0.0;
    for (double val : valori) {
      somma += val;
    }
    return somma / valori.size();
  }

  // Calcola la deviazione standard
  public static double deviazioneStandard(List<Double> valori, double media) {
    double somma = 0.0;
    for (double val : valori) {
      somma += Math.pow(val - media, 2);
    }
    return Math.sqrt(somma / valori.size());
  }

  // Calcola il coefficiente di variazione
  public static int coefficienteDiVariazione(List<Edge> edges) {
    List<Double> valori = new ArrayList<>();
    for(Edge edge : edges)
      valori.add(edge.getLength());

    double media = media(valori);
    double deviazioneStandard = deviazioneStandard(valori, media);
    return (int) ((deviazioneStandard / media) * 100); // CV espresso in percentuale
  }
}
