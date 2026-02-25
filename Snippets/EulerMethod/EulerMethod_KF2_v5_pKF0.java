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
        BiFunction<Double, Double, Double> differentialEquation,
        double xStart,
        double xEnd,
        double stepSize,
        double yStart,
        double expectedLastY
    ) {
        System.out.println(title);
        List<Point> points = eulerFull(xStart, xEnd, stepSize, yStart, differentialEquation);
        Point lastPoint = points.get(points.size() - 1);
        assert lastPoint.y == expectedLastY;
        printPoints(points);
    }

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

    public static List<Point> eulerFull(
        double xStart,
        double xEnd,
        double stepSize,
        double yStart,
        BiFunction<Double, Double, Double> differentialEquation
    ) {
        validateRange(xStart, xEnd);
        validateStepSize(stepSize);

        List<Point> points = new ArrayList<>();
        double xCurrent = xStart;
        double yCurrent = yStart;

        points.add(new Point(xCurrent, yCurrent));

        while (xCurrent < xEnd) {
            yCurrent = eulerStep(xCurrent, stepSize, yCurrent, differentialEquation);
            xCurrent += stepSize;
            points.add(new Point(xCurrent, yCurrent));
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

    private static void printPoints(List<Point> points) {
        for (Point point : points) {
            System.out.printf("x: %f; y: %f%n", point.x, point.y);
        }
    }

    public static final class Point {
        public final double x;
        public final double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}