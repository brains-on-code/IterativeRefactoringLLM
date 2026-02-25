package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Euler's method (forward Euler) is a first-order numerical procedure for
 * solving ordinary differential equations (ODEs) with a given initial value.
 *
 * Update rule:
 * y_{n+1} = y_n + h * f(x_n, y_n)
 *
 * where:
 * - h is the step size
 * - f(x, y) is the differential equation dy/dx = f(x, y)
 *
 * References:
 * - https://en.wikipedia.org/wiki/Euler_method
 * - https://www.geeksforgeeks.org/euler-method-solving-differential-equation/
 */
public final class EulerMethod {

    private EulerMethod() {
        // Prevent instantiation of utility class.
    }

    /**
     * Demonstrates Euler's method with three example differential equations
     * and prints the resulting (x, y) points.
     */
    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> exampleEquation1 = (x, y) -> x;
        List<double[]> points1 = eulerFull(0, 4, 0.1, 0, exampleEquation1);
        assert points1.get(points1.size() - 1)[1] == 7.800000000000003;
        points1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        // Example from: https://en.wikipedia.org/wiki/Euler_method
        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> exampleEquation2 = (x, y) -> y;
        List<double[]> points2 = eulerFull(0, 4, 0.1, 1, exampleEquation2);
        assert points2.get(points2.size() - 1)[1] == 45.25925556817596;
        points2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        // Example from:
        // https://www.geeksforgeeks.org/euler-method-solving-differential-equation/
        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> exampleEquation3 = (x, y) -> x + y + x * y;
        List<double[]> points3 = eulerFull(0, 0.1, 0.025, 1, exampleEquation3);
        assert points3.get(points3.size() - 1)[1] == 1.1116729841674804;
        points3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }

    /**
     * Computes the next y-value using a single Euler step.
     *
     * @param xCurrent             current x-value
     * @param stepSize             step size on the x-axis (must be > 0)
     * @param yCurrent             current y-value
     * @param differentialEquation function representing dy/dx = f(x, y)
     * @return next y-value after one Euler step
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
     * Applies Euler's method from xStart to xEnd and returns all computed
     * (x, y) points.
     *
     * @param xStart               initial x-value
     * @param xEnd                 final x-value (must be greater than xStart)
     * @param stepSize             step size on the x-axis (must be > 0)
     * @param yStart               initial y-value
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
        points.add(new double[] {xStart, yStart});

        double xCurrent = xStart;
        double yCurrent = yStart;

        while (xCurrent < xEnd) {
            yCurrent = eulerStep(xCurrent, stepSize, yCurrent, differentialEquation);
            xCurrent += stepSize;
            points.add(new double[] {xCurrent, yCurrent});
        }

        return points;
    }
}