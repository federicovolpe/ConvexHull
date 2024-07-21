package basic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CircularListTest {

  CircularList<Integer> Sut = new CircularList<>(List.of(1,2,3,4,5));

  @Test
  void getNext() {
    assertEquals(2, Sut.getNext(1));
    assertEquals(3, Sut.getNext(2));
    assertEquals(5, Sut.getNext(4));
    assertEquals(1, Sut.getNext(5));
  }

  @Test
  void get() {
    assertEquals(1, Sut.get(0));
    assertEquals(5, Sut.get(4));
    assertEquals(2, Sut.get(6));
    assertEquals(1, Sut.get(10));
  }

  @Test
  void remove() {
    Sut.remove(1);
    assertEquals(3, Sut.getNext(1));

    Sut.remove(1);
    Sut.remove(1);
    Sut.remove(1);
    assertEquals(1, Sut.getNext(1));
  }
}