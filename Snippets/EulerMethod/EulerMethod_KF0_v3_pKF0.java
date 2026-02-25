package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * In mathematics and computational science, the Euler method (also called
 * forward Euler method) is a first-order numerical procedure for solving
 * ordinary differential equations (ODEs) with a given initial value.
 *
 * y_{n+1} = y_n + stepSize * f(x_n, y_n)
 *
 * (description adapted from https://en.wikipedia.org/wiki/Euler_method)
 * (see also: https://www.geeksforgeeks.org/euler-method-solving-differential-equation/)
 */
public final class EulerMethod {

    private static final int X_INDEX = 0;
    private static final int Y_INDEX = 1;

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

        List<double[]> points = eulerFull(0.0, 4.0, 0.1, 0.0, equation);
        assert points.get(points.size() - 1)[Y_INDEX] == 7.800000000000003;

        printPoints(points);
    }

    private static void runExample2() {
        // example from https://en.wikipedia.org/wiki/Euler_method
        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> equation = (x, y) -> y;

        List<double[]> points = eulerFull(0.0, 4.0, 0.1, 1.0, equation);
        assert points.get(points.size() - 1)[Y_INDEX] == 45.25925556817596;

        printPoints(points);
    }

    private static void runExample3() {
        // example from https://www.geeksforgeeks.org/euler-method-solving-differential-equation/
        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> equation = (x, y) -> x + y + x * y;

        List<double[]> points = eulerFull(0.0, 0.1, 0.025, 1.0, equation);
        assert points.get(points.size() - 1)[Y_INDEX] == 1.1116729841674804;

        printPoints(points);
    }

    private static void printPoints(List<double[]> points) {
        for (double[] point : points) {
            double x = point[X_INDEX];
            double y = point[Y_INDEX];
            System.out.printf("x: %f; y: %f%n", x, y);
        }
    }

    /**
     * Calculates the next y-value based on the current value of x, y and the
     * step size.
     *
     * @param xCurrent             current x-value
     * @param stepSize             step size on the x-axis (must be > 0)
     * @param yCurrent             current y-value
     * @param differentialEquation the differential equation to be solved
     * @return the next y-value
     */
    public static double eulerStep(
            double xCurrent,
            double stepSize,
            double yCurrent,
            BiFunction<Double, Double, Double> differentialEquation
    ) {
        validateStepSize(stepSize);
        double slope = differentialEquation.apply(xCurrent, yCurrent);
        return yCurrent + stepSize * slope;
    }

    /**
     * Loops through all the steps until xEnd is reached, adds a point for each
     * step and then returns all the points.
     *
     * @param xStart               first x-value
     * @param xEnd                 last x-value (must be > xStart)
     * @param stepSize             step size on the x-axis (must be > 0)
     * @param yStart               first y-value
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
        validateRange(xStart, xEnd);
        validateStepSize(stepSize);

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

    private static void validateStepSize(double stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
    }

    private static void validateRange(double xStart, double xEnd) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
    }
}