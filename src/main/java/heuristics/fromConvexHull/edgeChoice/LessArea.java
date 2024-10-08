package heuristics.fromConvexHull.edgeChoice;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import heuristics.fromConvexHull.FromCH;

/*
 * considering all the edges, the chosen are the one which if prolungated
 * create the smallest outside area from the polygon
 */
public class LessArea extends FromCH implements SelectEdges{

  public LessArea(Color c) {
    super(c);
  }

  @Override
  public void calcConvexHull(int n) {
    CircularList<Edge> edges = poly.getEdges();
    //connectEdges(
    CircularList<Edge> selected = new CircularList<>();
    selected.addAll(selectEdges(n));

    sortBByA(edges, selected);

    connectEdges(selected);
    edges = selected;
  }

  @Override
  public List<Edge> selectEdges(int n) {
    List<Edge> sortedEdges = new ArrayList<>(poly.getEdges());

    sortedEdges.sort((e1, e2) -> {
      double area1 = calcProjectedArea(e1);
      double area2 = calcProjectedArea(e2);
      return Double.compare(area1, area2);
    });

    return sortedEdges.subList(0, n);
  }

  /**
   * calculates the area of the projection of the polygon onto the edge
   * @param e edge onto which project the polygon
   * @return the area in between
   */
  private double calcProjectedArea(Edge e){
    List<Edge> estremi = findFurthestProjectionNodes(e, poly.getEdges());
    Edge extremeL = estremi.get(0);
    Edge extremeR = estremi.get(1);

    // calcolare la lista di lati contenuti fra i due
    List<Edge> betweenEd = betweenEdges(extremeL, extremeR);
    //Left - LeftP
    Edge projectL = new Edge(e.projection(extremeL.n1()), extremeL.n1());
    Edge projectR = new Edge(extremeR.n1(), e.projection(extremeR.n1()));
    Edge pConnection = new Edge(e.projection(extremeR.n1()), e.projection(extremeL.n1()));
    betweenEd.addAll(List.of(projectR, pConnection, projectL));

    // calcolare l'area compresa con la proiezione e i punti
    return calcArea(getHullNodes(betweenEd));
  }

  /**
   * gauss formula for calculating the area of a polygon
   * @param points set of the vertices of the polygon
   * @return the area
   */
  private double calcArea(List<Point2D> points) {
    int n = points.size();
    double area = 0;

    for (int i = 0; i < n; i++) {
      Point2D current = points.get(i);
      Point2D next = points.get((i + 1) % n); // Next point, wrapping around to the first point at the end
      area += current.getX() * next.getY() - current.getY() * next.getX();
    }

    return Math.abs(area) / 2.0;
  }

  /**
   * return s all the edges in clockwise direction edge b excluded
   * @param A first edge
   * @param B second edge
   * @return the list of edges in between A and B
   */
  private List<Edge> betweenEdges (Edge A, Edge B){
    List<Edge> edges = new ArrayList<>();

    Edge curr = A;
    while(! curr.equals(B)){
      edges.add(curr);
      curr = poly.getEdges().getNext(curr);
    }

    return edges;
  }

  /**
   * just like in the explainatory image finds the two points which projection
   * onto the selected edge are the furhtest
   * @param e edge onto which project
   * @param edges convex hull
   * @return a list of two nodes
   */
  private List<Edge> findFurthestProjectionNodes (Edge e, List<Edge> edges){
    Edge eA = e;
    Edge eB = e;
    double maxDistance = e.n1().calcDistance(e.n2());

    for (Edge e1: edges) {
      for(Edge e2: edges){
        if(e.projection(e1.n1()).calcDistance(e.projection(e2.n1())) > maxDistance){
          maxDistance = e.projection(e1.n1()).calcDistance(e.projection(e2.n1()));

          // to establish which is to the left and which on the right
          if(e.n1().calcDistance(e1.n1()) < e.n2().calcDistance(e1.n1())){ // n1 is closest to the first node (left)
            eA = e1;
            eB = e2;
          }else{
            eA = e2;
            eB = e1;
          }
        }
      }
    }

    return List.of(eA, eB);
  }

  /**
   * given a set of edges, connects them to form a polygon
   * @param edges edges to connect
   */
  private void connectEdges(CircularList<Edge> edges){
    for(Edge curr : edges){
      curr.extendEdgeN2(edges.getNext(curr));
      curr.extendEdgeN1(edges.getPrev(curr));
    }
  }

}
