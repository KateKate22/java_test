package ru.stqa.pft.sandox;
public class DistanceClass {
  public static void main(String[] args) {
    Point point1 = new Point(-1,4);
    Point point2 = new Point(10,15);
    System.out.println("Вычисляем расстояние при помощи функции:");
    System.out.println("Расстояние между точками на плоскости равно " + distance(point1, point2));
    System.out.println("Вычисляем расстояние при помощи метода, реализованного в классе Point:");
    System.out.println("Расстояние между точками на плоскости равно " + Point.distance(point1, point2));
  }

  public static double distance(Point p1, Point p2) {
    return Math.sqrt(Math.pow(p2.x-p1.x, 2) + Math.pow(p2.y-p1.y, 2));
  }
}