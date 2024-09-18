package heuristics.fromConvexHull.cuttingEdges;

import basic.CircularList;
import basic.Edge;
import heuristics.fromConvexHull.FromCH;
import shapes.Polygon;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CuttingEdges extends FromCH {

  private Map<Edge, Double> edge_area = new HashMap<>();

  public CuttingEdges(Color c) {
    super(c);
  }

  /**
   * initializes the edge to area map for the future cutting procedure
   */
  private void initMap(){
    for(Edge e : poly.getEdges())
      edge_area.put(e, getArea(e));
  }

  @Override
  public void newData(Polygon p){
    super.newData(p);
    edge_area = new HashMap<>();
    initMap();
  }

  @Override
  public void calcConvexHull(int n) {
    while(poly.getEdgeNumber() > n) {
      applyCut();
    }
  }

  private void applyCut(){
    CircularList<Edge> edges = poly.getEdges();
    Edge selected = selectEdge();

    Edge prev = edges.getPrev(selected);
    Edge next = edges.getNext(selected);
    prev.extendEdgeN2(next);
    next.extendEdgeN1(prev);

    edges.remove(selected);
    edge_area.remove(selected);

    // ricalcolo delle nuove areee per i due lati modificati
    edge_area.put(prev, getArea(prev));
    edge_area.put(next, getArea(next));
    edge_area.put(edges.getNext(next), getArea(edges.getNext(next)));
    edge_area.put(edges.getPrev(prev), getArea(edges.getPrev(prev)));
  }

  /**
   * for every edges of the convex hull selects the one with the minimum area
   * (could be parallelized)
   * @return the best candidate edge
   */
  private Edge selectEdge (){
    double minArea = Double.MAX_VALUE;
    Edge e = null;

    for(Map.Entry<Edge, Double> entry : edge_area.entrySet())
      if(entry.getValue() < minArea && entry.getValue() > 0){
        minArea = entry.getValue();
        e = entry.getKey();
      }

    return e;
  }

  /**
   * given an edge calculates the area under the extension of two adjacent sides
   * if the adjacent edges area is inf, Double.MAX_VALUE is returned instead
   *
   * @param e edge of interest
   * @return the area
   */
  private double getArea(Edge e){
    double c = e.getLength();
    double alpha = Math.PI - poly.getEdges().getPrev(e).calcAngle(e);
    double beta = Math.PI - e.calcAngle(poly.getEdges().getNext(e));

    // se i due angoli interni al triangolo esterno sono maggiori di 180 allora non può essere selezionato il lato
    if(alpha + beta > Math.PI) return Double.MAX_VALUE;

    double gamma = Math.PI - alpha - beta;

    double a = Math.sin(alpha) * c / Math.sin(gamma);
    double b = Math.sin(beta) * c / Math.sin(gamma);

    double p = (a + b + c)/2;

    return Math.sqrt(p *(p-a) * (p-b) * (p-c));
  }

  @Override
  public void draw(Graphics g){
    super.draw(g);
  }
}
