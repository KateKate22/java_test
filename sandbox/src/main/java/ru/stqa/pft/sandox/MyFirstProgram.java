package ru.stqa.pft.sandox;

public class MyFirstProgram {
  public static void main(String[] args) {

    hello("Tom");
    hello("Alex");

    double w = 10.5;

    System.out.println("Площадь квадрата со стороной " + w + " равна " + area(w));

    double a = 14.7;
    double b = 17.2;

    System.out.println("Площадь прямоугольника со сторонами " + a + " и " + b + " равна " + area(a,b));

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

