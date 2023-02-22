package ru.stqa.ptf.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandox.Point;


public class PointTest {

  @Test
  public void testPoint() {
    Point point1 = new Point(-1, 0);
    Point point2 = new Point(2, 4);
    Assert.assertEquals(point1.distance(point2), 5.0);
  }
  @Test
  public void testPoint2() {
    Point point1 = new Point(0, 0);
    Point point2 = new Point(0, 0);
    Assert.assertEquals(point1.distance(point2), 0.0);
  }

  @Test
  public void testPoint3() {
    Point point1 = new Point(-7, -8);
    Point point2 = new Point(-4, -16);
    Assert.assertEquals(point1.distance(point2), 8.54400374531753);
  }
}
