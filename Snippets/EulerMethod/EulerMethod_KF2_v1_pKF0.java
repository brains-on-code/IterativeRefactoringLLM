package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class EulerMethod {

    private EulerMethod() {
        // Utility class
    }

    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> exampleEquation1 = (x, y) -> x;
        List<double[]> points1 = eulerFull(0, 4, 0.1, 0, exampleEquation1);
        assert points1.get(points1.size() - 1)[1] == 7.800000000000003;
        printPoints(points1);

        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> exampleEquation2 = (x, y) -> y;
        List<double[]> points2 = eulerFull(0, 4, 0.1, 1, exampleEquation2);
        assert points2.get(points2.size() - 1)[1] == 45.25925556817596;
        printPoints(points2);

        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> exampleEquation3 = (x, y) -> x + y + x * y;
        List<double[]> points3 = eulerFull(0, 0.1, 0.025, 1, exampleEquation3);
        assert points3.get(points3.size() - 1)[1] == 1.1116729841674804;
        printPoints(points3);
    }

    public static double eulerStep(
            double xCurrent,
            double stepSize,
            double yCurrent,
            BiFunction<Double, Double, Double> differentialEquation
    ) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
        return yCurrent + stepSize * differentialEquation.apply(xCurrent, yCurrent);
    }

    public static List<double[]> eulerFull(
            double xStart,
            double xEnd,
            double stepSize,
            double yStart,
            BiFunction<Double, Double, Double> differentialEquation
    ) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }

        List<double[]> points = new ArrayList<>();
        double xCurrent = xStart;
        double yCurrent = yStart;

        points.add(new double[] {xCurrent, yCurrent});

        while (xCurrent < xEnd) {
            yCurrent = eulerStep(xCurrent, stepSize, yCurrent, differentialEquation);
            xCurrent += stepSize;
            points.add(new double[] {xCurrent, yCurrent});
        }

        return points;
    }

    private static void printPoints(List<double[]> points) {
        points.forEach(point -> System.out.printf("x: %f; y: %f%n", point[0], point[1]));
    }
}