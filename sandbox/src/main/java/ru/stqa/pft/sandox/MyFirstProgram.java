package ru.stqa.pft.sandox;

public class MyFirstProgram {
  public static void main(String[] args) {

    hello("Tom");
    hello("Alex");

    Square square = new Square(10.5);

    System.out.println("Площадь квадрата со стороной " + square.a + " равна " + square.calcArea());

    Rectangle rectangle = new Rectangle(14.7,  17.2);

    System.out.println("Площадь прямоугольника со сторонами " + rectangle.a + " и " + rectangle.b + " равна " + rectangle.calcArea());
    }

  public static void hello(String name) {
    System.out.println("Hello, " + name);
  }
  public static double area(double a) {
    return a*a;
  }

  public static double area(double a, double b) {
    return a*b;
  }
}

