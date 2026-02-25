package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Demonstrates a simple Euler method implementation for solving
 * first-order ordinary differential equations (ODEs) of the form:
 *
 *     y' = f(x, y)
 *
 * with given initial conditions.
 */
public final class EulerMethod {

    private EulerMethod() {
        // Utility class; prevent instantiation
    }

    /**
     * Example usage of the Euler method with different differential equations.
     */
    public static void main(String[] args) {
        runExample(
            "example 1:",
            (x, y) -> x,
            0.0,
            4.0,
            0.1,
            0.0,
            7.800000000000003
        );

        runExample(
            "\n\nexample 2:",
            (x, y) -> y,
            0.0,
            4.0,
            0.1,
            1.0,
            45.25925556817596
        );

        runExample(
            "\n\nexample 3:",
            (x, y) -> x + y + x * y,
            0.0,
            0.1,
            0.025,
            1.0,
            1.1116729841674804
        );
    }

    private static void runExample(
        String title,
        BiFunction<Double, Double, Double> function,
        double xStart,
        double xEnd,
        double stepSize,
        double initialY,
        double expectedLastY
    ) {
        System.out.println(title);
        List<Point> result = eulerMethod(xStart, xEnd, stepSize, initialY, function);
        Point lastPoint = result.get(result.size() - 1);
        assert lastPoint.y() == expectedLastY;
        printPoints(result);
    }

    private static void printPoints(List<Point> points) {
        for (Point point : points) {
            System.out.printf("x: %f; y: %f%n", point.x(), point.y());
        }
    }

    /**
     * Performs a single Euler step:
     *
     *     y_{n+1} = y_n + h * f(x_n, y_n)
     *
     * @param x         current x value
     * @param stepSize  step size h (must be > 0)
     * @param y         current y value
     * @param function  function f(x, y) defining the ODE
     * @return          next y value after one Euler step
     */
    public static double eulerStep(
        double x,
        double stepSize,
        double y,
        BiFunction<Double, Double, Double> function
    ) {
        validateStepSize(stepSize);
        return y + stepSize * function.apply(x, y);
    }

    /**
     * Applies the Euler method to approximate the solution of the ODE:
     *
     *     y' = f(x, y)
     *
     * from xStart to xEnd with a given step size and initial value y0.
     *
     * @param xStart    starting x value (must be < xEnd)
     * @param xEnd      ending x value
     * @param stepSize  step size h (must be > 0)
     * @param y0        initial y value at xStart
     * @param function  function f(x, y) defining the ODE
     * @return          list of points (x, y) along the approximated solution
     */
    public static List<Point> eulerMethod(
        double xStart,
        double xEnd,
        double stepSize,
        double y0,
        BiFunction<Double, Double, Double> function
    ) {
        validateRange(xStart, xEnd);
        validateStepSize(stepSize);

        List<Point> points = new ArrayList<>();
        double x = xStart;
        double y = y0;

        points.add(new Point(x, y));

        while (x < xEnd) {
            y = eulerStep(x, stepSize, y, function);
            x += stepSize;
            points.add(new Point(x, y));
        }

        return points;
    }

    private static void validateRange(double xStart, double xEnd) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
    }

    private static void validateStepSize(double stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
    }

    private record Point(double x, double y) {}
}