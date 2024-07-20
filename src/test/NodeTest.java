import basic.Node;

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
  void equalsIndex() {
  }

  @Test
  void sameDimension() {
  }

  @Test
  void getCoor() {
  }

  @Test
  void testToString() {
  }

  @Test
  void getIndex() {
  }

  @Test
  void testEquals() {
  }

  @Test
  void testHashCode() {
  }

  @Test
  void getDim() {
  }
}