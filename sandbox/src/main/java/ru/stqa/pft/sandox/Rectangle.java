package ru.stqa.pft.sandox;

public class Rectangle {
  double a;
  double b;

  public Rectangle(double a, double b) {
    this.a = a;
    this.b = b;
  }

  public double calcArea(){
    return this.a * this.b;
  }
}
