package ru.stqa.pft.sandox;

public class Primes {
  public static boolean isPrimeNumber(int n) {
    for (int i = 2; i < n; i++ ) {
      if (n % 2 == 0) {
        return false;
      }
    }
    return true;
  }

  public static boolean isPrimeWhile(int n) {
    int i = 2;
    while (i < n) {
      if (n % i == 0)
      {
        return false;
      }
      i++;
    }
    return true;
  }

  public static boolean isPrimeWhile(long n) {
    long i = 2;
    long m = (long) Math.sqrt(n);
    while (i < m) {
      if (n % i == 0)
      {
        return false;
      }
      i++;
    }
    return true;
  }
}
