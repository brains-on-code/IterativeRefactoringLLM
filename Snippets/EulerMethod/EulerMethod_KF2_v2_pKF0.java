package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class EulerMethod {

    private static final String STEP_SIZE_ERROR = "stepSize should be greater than zero";
    private static final String RANGE_ERROR = "xEnd should be greater than xStart";

    private EulerMethod() {
        // Utility class
    }

    public static void main(String[] args) {
        runExample(
            "example 1:",
            (x, y) -> x,
            0, 4, 0.1, 0,
            7.800000000000003
        );

        runExample(
            "\n\nexample 2:",
            (x, y) -> y,
            0, 4, 0.1, 1,
            45.25925556817596
        );

        runExample(
            "\n\nexample 3:",
            (x, y) -> x + y + x * y,
            0, 0.1, 0.025, 1,
            1.1116729841674804
        );
    }

    private static void runExample(
        String title,
        BiFunction<Double, Double, Double> equation,
        double xStart,
        double xEnd,
        double stepSize,
        double yStart,
        double expectedLastY
    ) {
        System.out.println(title);
        List<double[]> points = eulerFull(xStart, xEnd, stepSize, yStart, equation);
        assert points.get(points.size() - 1)[1] == expectedLastY;
        printPoints(points);
    }

    public static double eulerStep(
        double xCurrent,
        double stepSize,
        double yCurrent,
        BiFunction<Double, Double, Double> differentialEquation
    ) {
        validateStepSize(stepSize);
        return yCurrent + stepSize * differentialEquation.apply(xCurrent, yCurrent);
    }

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

    private static void validateStepSize(double stepSize) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException(STEP_SIZE_ERROR);
        }
    }

    private static void validateRange(double xStart, double xEnd) {
        if (xStart >= xEnd) {
            throw new IllegalArgumentException(RANGE_ERROR);
        }
    }

    private static void printPoints(List<double[]> points) {
        points.forEach(point -> System.out.printf("x: %f; y: %f%n", point[0], point[1]));
    }
}