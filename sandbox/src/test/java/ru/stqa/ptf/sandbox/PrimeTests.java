package ru.stqa.ptf.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.sandox.Primes;

public class PrimeTests {
  @Test
  public void testPrime() {
    int a = Integer.MAX_VALUE;
    Assert.assertTrue(Primes.isPrimeWhile(Integer.MAX_VALUE));
  }

  @Test
  public void testPrime2() {
    int b = Integer.MAX_VALUE - 1;
    Assert.assertFalse(Primes.isPrimeWhile(Integer.MAX_VALUE - 1));
  }

  @Test
  public void testPrime3() {
    Assert.assertTrue(Primes.isPrimeWhile(1));
  }

  @Test
  public void testPrime4() {
    long c = Integer.MAX_VALUE;
    boolean r = Primes.isPrimeWhile(c);
    Assert.assertTrue(Primes.isPrimeWhile(c));
  }

  //изменения в ветке 101

  //еще изменения testMy
}
