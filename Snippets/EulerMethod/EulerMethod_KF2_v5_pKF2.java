package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Utility class providing an implementation of the Euler method for numerically
 * solving first-order ordinary differential equations (ODEs) of the form
 * dy/dx = f(x, y).
 */
public final class EulerMethod {

    private EulerMethod() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        runExample1();
        runExample2();
        runExample3();
    }

    private static void runExample1() {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> equation = (x, y) -> x;

        List<double[]> points = eulerFull(0, 4, 0.1, 0, equation);
        assert points.get(points.size() - 1)[1] == 7.800000000000003;

        printPoints(points);
    }

    private static void runExample2() {
        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> equation = (x, y) -> y;

        List<double[]> points = eulerFull(0, 4, 0.1, 1, equation);
        assert points.get(points.size() - 1)[1] == 45.25925556817596;

        printPoints(points);
    }

    private static void runExample3() {
        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> equation = (x, y) -> x + y + x * y;

        List<double[]> points = eulerFull(0, 0.1, 0.025, 1, equation);
        assert points.get(points.size() - 1)[1] == 1.1116729841674804;

        printPoints(points);
    }

    private static void printPoints(List<double[]> points) {
        points.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }

    /**
     * Performs a single Euler method step for the ODE dy/dx = f(x, y).
     *
     * @param xCurrent             current x value
     * @param stepSize             step size (must be > 0)
     * @param yCurrent             current y value
     * @param differentialEquation function representing dy/dx = f(x, y)
     * @return next y value after one Euler step
     * @throws IllegalArgumentException if stepSize is not greater than zero
     */
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

    /**
     * Computes the numerical solution of an ODE using the Euler method over an interval.
     *
     * @param xStart               start of the interval
     * @param xEnd                 end of the interval (must be greater than xStart)
     * @param stepSize             step size (must be > 0)
     * @param yStart               initial y value at xStart
     * @param differentialEquation function representing dy/dx = f(x, y)
     * @return list of points {x, y} approximating the solution
     * @throws IllegalArgumentException if xEnd <= xStart or stepSize <= 0
     */
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
}