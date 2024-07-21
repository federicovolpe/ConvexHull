package basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

  @ParameterizedTest
  @CsvSource({
      "1, 0",
      "5, 4",
      "2, 6",
      "5, -1",
      "3, -3"
  })
  void get(int expected, int index) {
    assertEquals(expected, Sut.get(index));
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