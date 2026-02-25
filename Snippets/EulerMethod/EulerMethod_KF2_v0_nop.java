package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.function.BiFunction;


public final class EulerMethod {
    private EulerMethod() {
    }


    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> exampleEquation1 = (x, y) -> x;
        ArrayList<double[]> points1 = eulerFull(0, 4, 0.1, 0, exampleEquation1);
        assert points1.get(points1.size() - 1)[1] == 7.800000000000003;
        points1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> exampleEquation2 = (x, y) -> y;
        ArrayList<double[]> points2 = eulerFull(0, 4, 0.1, 1, exampleEquation2);
        assert points2.get(points2.size() - 1)[1] == 45.25925556817596;
        points2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> exampleEquation3 = (x, y) -> x + y + x * y;
        ArrayList<double[]> points3 = eulerFull(0, 0.1, 0.025, 1, exampleEquation3);
        assert points3.get(points3.size() - 1)[1] == 1.1116729841674804;
        points3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }


    public static double eulerStep(double xCurrent, double stepSize, double yCurrent, BiFunction<Double, Double, Double> differentialEquation) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
        return yCurrent + stepSize * differentialEquation.apply(xCurrent, yCurrent);
    }


    public static ArrayList<double[]> eulerFull(double xStart, double xEnd, double stepSize, double yStart, BiFunction<Double, Double, Double> differentialEquation) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }

        ArrayList<double[]> points = new ArrayList<double[]>();
        double[] firstPoint = {xStart, yStart};
        points.add(firstPoint);
        double yCurrent = yStart;
        double xCurrent = xStart;

        while (xCurrent < xEnd) {
            yCurrent = eulerStep(xCurrent, stepSize, yCurrent, differentialEquation);
            xCurrent += stepSize;
            double[] point = {xCurrent, yCurrent};
            points.add(point);
        }

        return points;
    }
}
