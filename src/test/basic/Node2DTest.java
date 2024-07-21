package basic;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Node2DTest {

  Node2D Sut = new Node2D(0, 0,0);

  @ParameterizedTest
  @CsvSource({
      "1.57, -1, 0 , 0, 1",
      "1.57, 0, 1, -1, 0",
      "3.14, -1, -1, 1, 1"
  })
  void angleBetweenNodes(double expected, int x1, int y1, int x2, int y2) {
    Node2D A = new Node2D(-1, x1, y1);
    Node2D C = new Node2D(-1, x2, y2);
    assertEquals(expected,0.01, Sut.angleBetweenNodes(A, C));
  }
}