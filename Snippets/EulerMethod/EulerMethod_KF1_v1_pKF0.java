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
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Example usage of the Euler method with different differential equations.
     */
    public static void method1(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> f1 = (x, y) -> x;
        List<double[]> result1 = eulerMethod(0, 4, 0.1, 0, f1);
        assert result1.get(result1.size() - 1)[1] == 7.800000000000003;
        result1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> f2 = (x, y) -> y;
        List<double[]> result2 = eulerMethod(0, 4, 0.1, 1, f2);
        assert result2.get(result2.size() - 1)[1] == 45.25925556817596;
        result2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> f3 = (x, y) -> x + y + x * y;
        List<double[]> result3 = eulerMethod(0, 0.1, 0.025, 1, f3);
        assert result3.get(result3.size() - 1)[1] == 1.1116729841674804;
        result3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
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
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
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
     * @return          list of points {x, y} along the approximated solution
     */
    public static ArrayList<double[]> eulerMethod(
            double xStart,
            double xEnd,
            double stepSize,
            double y0,
            BiFunction<Double, Double, Double> function
    ) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException("xEnd should be greater than xStart");
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }

        ArrayList<double[]> points = new ArrayList<>();
        double x = xStart;
        double y = y0;

        points.add(new double[] {x, y});

        while (x < xEnd) {
            y = eulerStep(x, stepSize, y, function);
            x += stepSize;
            points.add(new double[] {x, y});
        }

        return points;
    }
}