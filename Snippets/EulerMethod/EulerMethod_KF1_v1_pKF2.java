package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.function.BiFunction;

/**
 * Utility class demonstrating Euler's method for solving ordinary differential equations (ODEs).
 *
 * <p>The example in {@link #main(String[])} shows three different differential equations:
 * <ul>
 *     <li>y' = x</li>
 *     <li>y' = y</li>
 *     <li>y' = x + y + x*y</li>
 * </ul>
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Demonstrates Euler's method with three example differential equations.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("example 1: y' = x, y(0) = 0");
        BiFunction<Double, Double, Double> derivative1 = (x, y) -> x;
        ArrayList<double[]> result1 = eulerMethod(0, 4, 0.1, 0, derivative1);
        assert result1.get(result1.size() - 1)[1] == 7.800000000000003;
        result1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 2: y' = y, y(0) = 1");
        BiFunction<Double, Double, Double> derivative2 = (x, y) -> y;
        ArrayList<double[]> result2 = eulerMethod(0, 4, 0.1, 1, derivative2);
        assert result2.get(result2.size() - 1)[1] == 45.25925556817596;
        result2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 3: y' = x + y + x*y, y(0) = 1");
        BiFunction<Double, Double, Double> derivative3 = (x, y) -> x + y + x * y;
        ArrayList<double[]> result3 = eulerMethod(0, 0.1, 0.025, 1, derivative3);
        assert result3.get(result3.size() - 1)[1] == 1.1116729841674804;
        result3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }

    /**
     * Performs a single Euler step.
     *
     * <p>Given the current point (x, y), step size h, and derivative function f(x, y),
     * this computes the next y value:
     *
     * <pre>
     * y_{n+1} = y_n + h * f(x_n, y_n)
     * </pre>
     *
     * @param x          current x value
     * @param stepSize   step size (h); must be greater than zero
     * @param y          current y value
     * @param derivative derivative function f(x, y)
     * @return next y value after one Euler step
     * @throws IllegalArgumentException if {@code stepSize <= 0}
     */
    public static double eulerStep(
        double x,
        double stepSize,
        double y,
        BiFunction<Double, Double, Double> derivative
    ) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
        return y + stepSize * derivative.apply(x, y);
    }

    /**
     * Applies Euler's method to approximate the solution of an ODE y' = f(x, y).
     *
     * @param xStart     starting x value
     * @param xEnd       ending x value; must be greater than {@code xStart}
     * @param stepSize   step size (h); must be greater than zero
     * @param yStart     initial y value at xStart
     * @param derivative derivative function f(x, y)
     * @return list of points {x, y} representing the approximate solution
     * @throws IllegalArgumentException if {@code xEnd <= xStart} or {@code stepSize <= 0}
     */
    public static ArrayList<double[]> eulerMethod(
        double xStart,
        double xEnd,
        double stepSize,
        double yStart,
        BiFunction<Double, Double, Double> derivative
    ) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }

        ArrayList<double[]> points = new ArrayList<>();
        double x = xStart;
        double y = yStart;

        points.add(new double[] {x, y});

        while (x < xEnd) {
            y = eulerStep(x, stepSize, y, derivative);
            x += stepSize;
            points.add(new double[] {x, y});
        }

        return points;
    }
}