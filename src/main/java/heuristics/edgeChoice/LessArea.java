package heuristics.edgeChoice;

import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import heuristics.fromConvexHull.CuttingNodes;
import java.awt.*;
import java.util.*;
import java.util.List;

/*
 * considering all the edges, the chosen are the one which if prolungated
 * create the smallest outside area from the polygon
 */
public class LessArea extends CuttingNodes {

  public LessArea(List<Edge> convexHull, Color c) {
    super(convexHull, c);
  }

  @Override
  public void calcConvexHull(int n) {
    System.out.println("\nconvex hull: ");
    for(Edge e : convexHull) System.out.println(e);

    //connectEdges(
    CircularList<Edge> selected = new CircularList<>();
    selected.addAll(selectEdges(n));

    System.out.println("\nbest edges: ");
    for(Edge e : selected) System.out.println(e);
    sortBByA(selected);
    System.out.println("\nsorted: ");
    for(Edge e : selected) System.out.println(e);

    connectEdges(selected);
    convexHull = selected;
  }

  @Override
  protected void applyCut() {

  }

  @Override
  protected int selectAngle() {
    return 0;
  }

  private List<Edge> selectEdges(int n) {
    List<Edge> sortedEdges = new ArrayList<>(convexHull);

    sortedEdges.sort((e1, e2) -> {
      double area1 = calcProjectedArea(e1);
      double area2 = calcProjectedArea(e2);
      return Double.compare(area1, area2);
    });

    return sortedEdges.subList(0,n);
  }

  /**
   * sort the list in order of apperance in the convexHull
   * @param edges selected edges
   */
  private void sortBByA(List<Edge> edges) {
    // Create a map from elements in A to their indices
    Map<Edge, Integer> indexMap = new HashMap<>();
    for (int i = 0; i < convexHull.size(); i++)
      indexMap.put(convexHull.get(i), i);

    // Sort B using the indices from the map
    edges.sort(Comparator.comparingInt(indexMap::get));
  }

  /**
   * calculates the area of the projection of the polygon onto the edge
   * @param e edge onto which project the polygon
   * @return the area in between
   */
  private double calcProjectedArea(Edge e){
    List<Edge> estremi = findFurthestProjectionNodes(e, convexHull);
    Edge extremeL = estremi.get(0);
    Edge extremeR = estremi.get(1);

    //System.out.println(e +" :\n"+estremi );

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
      curr = convexHull.getNext(curr);
    }

    return edges;
  }

  /**
   * return a list of nodes (vertices of the polygon)
   * @param hull list of all the edges of the polygon
   * @return list of vertices
   */
  private List<Point2D> getHullNodes(List<Edge> hull) {
    List<Point2D> nodes = new ArrayList<>();
    for (Edge e : hull)
      nodes.add(e.n1());
    return nodes;
  }

  private List<Edge> findFurthestProjectionNodes (Edge e, List<Edge> edges){
    Edge eA = e;
    Edge eB = e;
    Point2D furthestFromA = e.n1();
    Point2D furthestFromB = e.n2();
    double maxDistance = e.n1().calcDistance(e.n2());


    for (Edge e1: edges) {
      for(Edge e2: edges){
        if(e.projection(e1.n1()).calcDistance(e.projection(e2.n1())) > maxDistance){
          maxDistance = e.projection(e1.n1()).calcDistance(e.projection(e2.n1()));

          // to establish which is to the left and which on the right
          if(e.n1().calcDistance(e1.n1()) < e.n2().calcDistance(e1.n1())){ // n1 is closest to the first node (left)
            furthestFromA = e1.n1();
            eA = e1;
            furthestFromB = e2.n1();
            eB = e2;
          }else{
            furthestFromA = e2.n1();
            eA = e2;
            furthestFromB = e1.n1();
            eB = e1;
          }
        }
      }
    }
    //return List.of(furthestFromA, furthestFromB);
    return List.of(eA, eB);
  }

  private void connectEdges(CircularList<Edge> edges){
    for(Edge curr : edges){
      curr.extendEdgeN2(edges.getNext(curr));
      curr.extendEdgeN1(edges.getPrev(curr));
    }
  }

  @Override
  public void draw(Graphics g) {
    super.draw(g);
    /*for(Edge e: convexHull){
      System.out.println("drawing points");
      List<Edge> estremi = findFurthestProjectionNodes(e, convexHull);
      g.setColor(Color.GREEN);
      new Edge(e.projection(estremi.get(0).n1()), e.projection(estremi.get(1).n1())).draw(g);

      //for(Point2D p : estremi) {
      //  e.projection(p).draw(g, true);
      //}
    }*/
  }
}
