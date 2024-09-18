package heuristics.fromPoints;

import basic.CircularList;
import basic.Point2D;
import main.JarvisMarch;
import shapes.Polygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DistanceFromG2 extends DistanceFromG {
  public DistanceFromG2(Color c) {
    super(c);
  }

  @Override
  public void calcConvexHull(int n) {
    super.calcConvexHull(n);
    CircularList<Point2D> vertices = new CircularList<>(getHullNodes());

    // ordinati in base a quelli piu' vicini a quelli piu lontani fra di loro
    vertices.sort(Comparator.comparing((Point2D p) ->
        p.calcDistance(vertices.getPrev(p)) +
        p.calcDistance(vertices.getNext(p))));

    // allNodes list should already be ordered
    List<Point2D> enlarged = enlargeArea(vertices);
    //System.out.println("modified vertices");
    //for(Point2D p : enlarged){
      //System.out.println(p);
    //}
    poly = new Polygon(new JarvisMarch(enlarged).getHullNodes());
  }

  private List<Point2D> enlargeArea(List<Point2D> vertices) {
    List<Point2D> other = getOtherNodes(allNodes, vertices);
    double area = new JarvisMarch(vertices).calcArea();
    //System.out.println("chosen:");
    //for(Point2D v : vertices)
      //System.out.println(v);
    //System.out.println("og area: " + area);

    // Lista temporanea copia dei vertici
    List<Point2D> newVertex = new ArrayList<>(vertices);

    for (int v = 0; v < vertices.size(); v++) {
      //System.out.println("analyzing: "+ vertices.get(v));
      for (int i = 0; i < other.size(); i++) {
        Point2D p = other.get(i);
        Point2D prev = newVertex.get(v);
        newVertex.set(v, p);

        double area2 = new JarvisMarch(newVertex).calcArea();

        if (area2 > area) {
          //System.out.println("substituting v: " + vertices.get(v) + " with " + p);
          //System.out.println("area with " + p + " = " + area2);
          area = area2;
          other.remove(p);
          //System.out.println("resulting:");
          //for(Point2D p1: newVertex) System.out.println(p1);
        } else {
          newVertex.set(v, prev);
        }
      }
    }


    return newVertex;
  }
  private List<Point2D> getOtherNodes(List<Point2D> allNodes, List<Point2D> subset){
    List<Point2D> other = new ArrayList<>();
    for(Point2D p : allNodes)
      if(! subset.contains(p))
        other.add(p);
    return other;
  }

}
