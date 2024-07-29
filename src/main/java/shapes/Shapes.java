package shapes;

import basic.Node2D;

import java.util.List;

public enum Shapes {
  TRIANGLE(new Polygon(List.of(
      new Node2D(0,-200,-200),
      new Node2D(1,200,-200),
      new Node2D(2,0,300)))),
  SQUARE(new Polygon(List.of(
      new Node2D(0,-200,-200),
      new Node2D(1,200,-200),
      new Node2D(2,200,200),
      new Node2D(2,-200,200)))),
  RECTANGLE(new Polygon(List.of(
      new Node2D(0,-200,-100),
      new Node2D(1,200,-100),
      new Node2D(2,200,100),
      new Node2D(2,-200,100)))),
  PENTAGON(new Polygon(List.of(
      new Node2D(0,-200,-200),
      new Node2D(1,200,-200),
      new Node2D(2,0,300))));

  private final  Polygon p;
  Shapes (Polygon p) {
    this.p = p;
  }
  public Polygon getPolygon() {
    return p;
  }
}
