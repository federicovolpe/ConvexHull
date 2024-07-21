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
  void getPrevious() {
  }

  @Test
  void get() {
  }

  @Test
  void remove() {
  }
}