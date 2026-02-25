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
 * y_n+1 = y_n + stepSize * f(x_n, y_n). (description adapted from
 * https://en.wikipedia.org/wiki/Euler_method ) (see also:
 * https://www.geeksforgeeks.org/euler-method-solving-differential-equation/ )
 */
public final class EulerMethod {

    private EulerMethod() {
        // Utility class; prevent instantiation.
    }

    /**
     * Illustrates how the algorithm is used in 3 examples and prints the
     * results to the console.
     */
    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> exampleEquation1 = (x, y) -> x;
        List<double[]> solutionPoints1 = solveEuler(0, 4, 0.1, 0, exampleEquation1);
        assert solutionPoints1.get(solutionPoints1.size() - 1)[1] == 7.800000000000003;
        solutionPoints1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        // example from https://en.wikipedia.org/wiki/Euler_method
        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> exampleEquation2 = (x, y) -> y;
        List<double[]> solutionPoints2 = solveEuler(0, 4, 0.1, 1, exampleEquation2);
        assert solutionPoints2.get(solutionPoints2.size() - 1)[1] == 45.25925556817596;
        solutionPoints2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        // example from https://www.geeksforgeeks.org/euler-method-solving-differential-equation/
        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> exampleEquation3 = (x, y) -> x + y + x * y;
        List<double[]> solutionPoints3 = solveEuler(0, 0.1, 0.025, 1, exampleEquation3);
        assert solutionPoints3.get(solutionPoints3.size() - 1)[1] == 1.1116729841674804;
        solutionPoints3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }

    /**
     * Calculates the next y-value based on the current value of x, y and the
     * stepSize.
     *
     * @param currentX Current x-value.
     * @param stepSize Step-size on the x-axis.
     * @param currentY Current y-value.
     * @param differentialEquation The differential equation to be solved.
     * @return The next y-value.
     */
    public static double computeNextY(
            double currentX,
            double stepSize,
            double currentY,
            BiFunction<Double, Double, Double> differentialEquation) {

        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
        return currentY + stepSize * differentialEquation.apply(currentX, currentY);
    }

    /**
     * Loops through all the steps until endX is reached, adds a point for each
     * step and then returns all the points.
     *
     * @param startX First x-value.
     * @param endX Last x-value.
     * @param stepSize Step-size on the x-axis.
     * @param startY First y-value.
     * @param differentialEquation The differential equation to be solved.
     * @return The points constituting the solution of the differential
     * equation.
     */
    public static ArrayList<double[]> solveEuler(
            double startX,
            double endX,
            double stepSize,
            double startY,
            BiFunction<Double, Double, Double> differentialEquation) {

        if (startX >= endX) {
            throw new IllegalArgumentException("endX should be greater than startX");
        }
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }

        ArrayList<double[]> solutionPoints = new ArrayList<>();
        double[] initialPoint = {startX, startY};
        solutionPoints.add(initialPoint);

        double currentX = startX;
        double currentY = startY;

        while (currentX < endX) {
            currentY = computeNextY(currentX, stepSize, currentY, differentialEquation);
            currentX += stepSize;
            double[] point = {currentX, currentY};
            solutionPoints.add(point);
        }

        return solutionPoints;
    }
}