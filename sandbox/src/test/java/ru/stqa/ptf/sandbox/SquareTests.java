package ru.stqa.ptf.sandbox;

import org.testng.Assert;

import org.testng.annotations.Test;
import ru.stqa.pft.sandox.Square;

public class SquareTests {
  @Test
  public void testArea() {
    Square s = new Square(5);
    Assert.assertEquals(s.calcArea(), 20.0);
  }
}
