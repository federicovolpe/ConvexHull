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

  @Test
  void getCenterOfMass() {
  }

  @Test
  void getLineParameters() {
  }

  @Test
  void calcIntersectionWithLine() {
  }

  @Test
  void traslate() {
  }

  @Test
  void n1() {
  }

  @Test
  void n2() {
  }

  @Test
  void setn1() {
  }

  @Test
  void setn2() {
  }
}