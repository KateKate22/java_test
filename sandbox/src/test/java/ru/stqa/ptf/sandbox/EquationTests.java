package ru.stqa.ptf.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandox.Equation;

public class EquationTests {
  @Test
  public void test() {
    Equation e = new Equation(4, 6, 9);
    Assert.assertEquals(e.rootNumber(), 0);
  }

  @Test
  public void test2() {
    Equation e = new Equation(1, 2, 1);
    Assert.assertEquals(e.rootNumber(), 1);
  }

  @Test
  public void test3() {
    Equation e = new Equation(1, 76, 9);
    Assert.assertEquals(e.rootNumber(), 2);
  }
}
