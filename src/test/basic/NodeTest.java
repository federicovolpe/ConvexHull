package basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
  Node Sut = new Node(List.of(1,1,1), 0);

  @Test
  void calcDistance_notSameDimension() {
    Node n = new Node(2, 10, 0);
    assertThrows(IllegalArgumentException.class, () -> Sut.calcDistance(n));
  }

  @ParameterizedTest
  @CsvSource({
      "0, 1, 1, 1",
      "1, 1, 1, 0",
      "10, 11, 1, 1"
  })
  void calcDistance_sameDimension(double expected, int x, int y, int  z) {
    Node n = new Node(List.of(x,y,z), 0);
    assertEquals(expected, Sut.calcDistance(n));
  }

  @ParameterizedTest
  @CsvSource({
      "true, 1, 1, 1",
      "false, 1, 1, 0",
      "false, 11, 1, 1"
  })
  void samePos(boolean expected, int x, int y, int  z) {
    Node n = new Node(List.of(x,y,z), 0);
    assertEquals(expected, Sut.samePos(n));
  }

  @Test
  void sameDimension() {
    Node n = new Node(List.of(1,1,1), 0);
    assertTrue(Sut.sameDimension(n));

    n = new Node(List.of(1,1,1,1), 0);
    assertFalse(Sut.sameDimension(n));

    n = new Node(List.of(1), 0);
    assertFalse(Sut.sameDimension(n));
  }

  @ParameterizedTest
  @CsvSource({
      "true, 1, 1, 1, 0",
      "false, 1, 1, 1, 1",
      "false, 1, 1, 0, 1",
      "false, 11, 1, 1, 0"
  })
  void testEquals(boolean expected, int x, int y, int z, int index) {
    Node n = new Node(List.of(x,y,z), index);
    assertEquals(expected, Sut.equals(n));
  }

}