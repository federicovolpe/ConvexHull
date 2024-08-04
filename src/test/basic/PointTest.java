package basic;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {
  Point Sut = new Point(List.of(1,1,1), 0);

  @Test
  void calcDistance_notSameDimension() {
    Point n = new Point(2, 10, 0);
    assertThrows(IllegalArgumentException.class, () -> Sut.calcDistance(n));
  }

  @ParameterizedTest
  @CsvSource({
      "0, 1, 1, 1",
      "1, 1, 1, 0",
      "10, 11, 1, 1"
  })
  void calcDistance_sameDimension(double expected, int x, int y, int  z) {
    Point n = new Point(List.of(x,y,z), 0);
    assertEquals(expected, Sut.calcDistance(n));
  }

  @ParameterizedTest
  @CsvSource({
      "true, 1, 1, 1",
      "false, 1, 1, 0",
      "false, 11, 1, 1"
  })
  void samePos(boolean expected, int x, int y, int  z) {
    Point n = new Point(List.of(x,y,z), 0);
    assertEquals(expected, Sut.samePos(n));
  }

  @Test
  void sameDimension() {
    Point n = new Point(List.of(1,1,1), 0);
    assertTrue(Sut.sameDimension(n));

    n = new Point(List.of(1,1,1,1), 0);
    assertFalse(Sut.sameDimension(n));

    n = new Point(List.of(1), 0);
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
    Point n = new Point(List.of(x,y,z), index);
    assertEquals(expected, Sut.equals(n));
  }

  @Test
  void toPolymake(){
    assertEquals("[1,1,1,1]",Sut.toPolymakeVert());
    Point n = new Point(List.of(1,2,3,4,5), 0);
    assertEquals("[1,1,2,3,4,5]",n.toPolymakeVert());
  }

}