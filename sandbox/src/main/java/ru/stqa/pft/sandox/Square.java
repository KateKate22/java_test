package ru.stqa.pft.sandox;

public class Square {
  public double a;
  public Square(double a) {
    this.a = a;
  }

  public double calcArea() {
    return this.a * this.a;
  }
}
