package basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;


class EdgeTest {
  Edge Sut = new Edge(new Node2D(0,0,0), new Node2D(1, 1,1));

  @Test
  void calcAngle_notConsecutive() {
    Edge e = new Edge(new Node2D(0,0,0), new Node2D(1, 1,1));
    assertThrows(IllegalArgumentException.class, () -> Sut.calcAngle(e));
  }

  @ParameterizedTest
  @CsvSource({
      "3.14 , 2, 2",
      "0.7, 0, 1",
      "0.7, 1, 0"
  })
  void calcAngle_consecutive(double expected, int x, int y) {
    Edge e = new Edge(new Node2D(1,1,1), new Node2D(2, x,y));
    assertEquals(expected, 0.01 ,Sut.calcAngle(e));
  }

  @ParameterizedTest
  @CsvSource({
      "33, 33 , 0, 0",
      "33, 66, 0, 100",
      "66, 33, 100, 0"
  })
  void getCenterOfMass(int expectedx, int expectedy, int x, int y) {
    Edge Sut = new Edge(new Node2D(0,0,0), new Node2D(1, 100,100));

    Edge e = new Edge(new Node2D(1,100,100), new Node2D(2, x, y));

    assert(new Node2D(-1, expectedx, expectedy).equals(Sut.getCenterOfMass(e)));
  }

  @ParameterizedTest
  @CsvSource({
      "1, 1 , 1, 1, 2, 0",
      "-1, -1, -1,-1, 2, -2"
  })
  void calcIntersectionWithLine(int expectedx, int expectedy, int x1, int y1, int x2, int y2) {
    Edge Sut = new Edge(new Node2D(0,0,0), new Node2D(1, 100,100));

    Edge e = new Edge(new Node2D(1,x1,y1), new Node2D(2, x2, y2));

    assertEquals(new Node2D(-1, expectedx, expectedy),
        Sut.calcIntersectionWithLine(e.getLineParameters()[0],
            e.getLineParameters()[1],
            e.getLineParameters()[2]));
  }

  @ParameterizedTest
  @CsvSource({
      "-50, -50, 50, 50, 0, 0",
      "-50, 50, 50, 150, 0, 100",
      "-100, -100,0,0, -50, -50"
  })
  void traslate(int expX1, int expY1, int expX2, int expY2, int x, int y) {
    Edge Sut = new Edge(new Node2D(0,0,0), new Node2D(1, 100,100));
    Sut.traslate(new Node2D(-1, x, y));
    assertEquals(expX1, Sut.n1().getX());
    assertEquals(expY1, Sut.n1().getY());
    assertEquals(expX2, Sut.n2().getX());
    assertEquals(expY2, Sut.n2().getY());
  }
}