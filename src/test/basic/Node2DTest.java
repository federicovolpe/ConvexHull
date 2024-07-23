package basic;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class Node2DTest {

  Node2D Sut = new Node2D(0, 0, 0);

  @ParameterizedTest
  @CsvSource({
      "1.57, -1, 0 , 0, 1",
      "1.57, 0, 1, -1, 0",
      "3.14, -1, -1, 1, 1"
  })
  void angleBetweenNodes(double expected, int x1, int y1, int x2, int y2) {
    Node2D A = new Node2D(-1, x1, y1);
    Node2D C = new Node2D(-1, x2, y2);
    assertEquals(expected, 0.01, Sut.angleBetweenNodes(A, C));
  }

  @ParameterizedTest
  @CsvSource({
      "true, -1, 1 , 1, 1, -1 ,0, -1",
      "true, 0, -1, -1, 1, -1 ,1, 1",
      "true, 0, 0, 0, 1, -1 ,1, 0",
      "true, 0, 0, 1, 0, -1,0, 0",
      "false, 0, 0, 1, 0, 0,0, 0"// node 3 is equal to the Sut node
  })
  void isContained(boolean expected, int x1, int y1, int x2, int y2, int index3, int x3, int y3) {
    Node2D A = new Node2D(-1, x1, y1);
    Node2D B = new Node2D(-1, x2, y2);
    Node2D C = new Node2D(index3, x3, y3);
    assertEquals(expected, Sut.isContained(A, B, C));
  }

}