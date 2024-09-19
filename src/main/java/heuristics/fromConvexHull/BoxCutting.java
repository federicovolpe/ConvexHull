package heuristics.fromConvexHull;


import basic.CircularList;
import basic.Edge;
import basic.Point2D;
import shapes.Polygon;
import java.awt.*;
import java.util.List;

public class BoxCutting extends FromCH{

  private Polygon box;
  private List<Edge> convexHull;

  public BoxCutting(Color c) {
    super(c);
  }

  private void initBox() {
    int maxX = Integer.MIN_VALUE, minX = Integer.MAX_VALUE, maxY = Integer.MIN_VALUE, minY = Integer.MAX_VALUE;
    for(Point2D v : poly.getVertices()){
      maxX = Math.max(v.getX(), maxX);
      maxY = Math.max(v.getY(), maxY);
      minX = Math.min(v.getX(), minX);
      minY = Math.min(v.getY(), minY);
    }

    box = new Polygon(List.of(
        new Point2D(0, minX, maxY),
        new Point2D(1, minX, minY),
        new Point2D(2, maxX, minY),
        new Point2D(3, maxX, maxY)
    ));
  }

  @Override
  public void calcConvexHull(int n) {
    while(box.getEdgeNumber() < n) {
      // seleziona il lato che separa un'area maggiore del box
      Edge selected = selectEdge();
      // taglia via l-area dalla box
      box = box.cutIn(selected);

      convexHull.remove(selected);
    }
  }

  /**
   * algoritmo che fra tutti gli edge del convexhull seleziona quello che separa
   * la maggior area di piano dalla box
   * @return un edge da aggiungere alla box
   */
  private Edge selectEdge() {

    double bestArea = 0;
    Edge bestEdge = convexHull.get(0);

    for(Edge e : convexHull){
      double areaCompresa = calcArea(e);
      if(areaCompresa > bestArea){
        bestArea = areaCompresa;
        bestEdge = e;
      }
    }
    return bestEdge;
  }

  /**
   * calcola l'area formata dalla box e il lato indicato
   * @param edge lato da collimare con la box
   * @return il valore dell'area compresa
   */
  private double calcArea(Edge edge){
    return box.cutOut(edge).calcArea();
  }

  @Override
  public CircularList<Point2D> getHullNodes(){
    CircularList<Point2D> nodes = new CircularList<>();
    for (Edge e : box.getEdges())
      nodes.add(e.n1());
    return nodes;
  }

  @Override
  public void draw(Graphics g){
    g.setColor(c);
    box.draw(g);
  }
  @Override
  public void newData(Polygon p){
    super.newData(p);
    convexHull = p.getEdges();
    initBox();
  }

}
