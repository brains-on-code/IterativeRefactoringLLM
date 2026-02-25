package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * In mathematics and computational science, the Euler method (also called
 * forward Euler method) is a first-order numerical procedure for solving
 * ordinary differential equations (ODEs) with a given initial value. It is the
 * most basic explicit method for numerical integration of ordinary differential
 * equations. The method proceeds in a series of steps. At each step the y-value
 * is calculated by evaluating the differential equation at the previous step,
 * multiplying the result with the step-size and adding it to the last y-value:
 * y_{n+1} = y_n + stepSize * f(x_n, y_n).
 *
 * (description adapted from https://en.wikipedia.org/wiki/Euler_method)
 * (see also: https://www.geeksforgeeks.org/euler-method-solving-differential-equation/)
 */
public final class EulerMethod {

    private EulerMethod() {
        // Utility class; prevent instantiation
    }

    /**
     * Demonstrates how the algorithm is used in 3 examples and prints the
     * results to the console.
     */
    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> exampleEquation1 = (x, y) -> x;
        List<double[]> points1 = eulerFull(0, 4, 0.1, 0, exampleEquation1);
        assert points1.get(points1.size() - 1)[1] == 7.800000000000003;
        printPoints(points1);

        // example from https://en.wikipedia.org/wiki/Euler_method
        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> exampleEquation2 = (x, y) -> y;
        List<double[]> points2 = eulerFull(0, 4, 0.1, 1, exampleEquation2);
        assert points2.get(points2.size() - 1)[1] == 45.25925556817596;
        printPoints(points2);

        // example from https://www.geeksforgeeks.org/euler-method-solving-differential-equation/
        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> exampleEquation3 = (x, y) -> x + y + x * y;
        List<double[]> points3 = eulerFull(0, 0.1, 0.025, 1, exampleEquation3);
        assert points3.get(points3.size() - 1)[1] == 1.1116729841674804;
        printPoints(points3);
    }

    private static void printPoints(List<double[]> points) {
        points.forEach(point -> System.out.printf("x: %f; y: %f%n", point[0], point[1]));
    }

    /**
     * Calculates the next y-value based on the current value of x, y and the
     * step size.
     *
     * @param xCurrent            current x-value
     * @param stepSize            step size on the x-axis (must be > 0)
     * @param yCurrent            current y-value
     * @param differentialEquation the differential equation to be solved
     * @return the next y-value
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
     * Loops through all the steps until xEnd is reached, adds a point for each
     * step and then returns all the points.
     *
     * @param xStart              first x-value
     * @param xEnd                last x-value (must be > xStart)
     * @param stepSize            step size on the x-axis (must be > 0)
     * @param yStart              first y-value
     * @param differentialEquation the differential equation to be solved
     * @return the points constituting the solution of the differential equation
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