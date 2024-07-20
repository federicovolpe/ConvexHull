import basic.Node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {
  Node Sut = new Node(3, 10, 0);

  @Test
  void calcDistance_notSameDimension() {
    Node n = new Node(2, 10, 0);
    assertThrows(IllegalArgumentException.class, () -> Sut.calcDistance(n));
  }

  @Test
  void samePos() {
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