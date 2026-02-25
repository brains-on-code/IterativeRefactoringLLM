package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public final class EulerMethod {

    private EulerMethod() {
    }

    public static void main(String[] args) {
        System.out.println("example 1:");
        BiFunction<Double, Double, Double> equation1 = (x, y) -> x;
        List<double[]> solution1 = solveEuler(0, 4, 0.1, 0, equation1);
        assert solution1.get(solution1.size() - 1)[1] == 7.800000000000003;
        solution1.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 2:");
        BiFunction<Double, Double, Double> equation2 = (x, y) -> y;
        List<double[]> solution2 = solveEuler(0, 4, 0.1, 1, equation2);
        assert solution2.get(solution2.size() - 1)[1] == 45.25925556817596;
        solution2.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));

        System.out.println("\n\nexample 3:");
        BiFunction<Double, Double, Double> equation3 = (x, y) -> x + y + x * y;
        List<double[]> solution3 = solveEuler(0, 0.1, 0.025, 1, equation3);
        assert solution3.get(solution3.size() - 1)[1] == 1.1116729841674804;
        solution3.forEach(point -> System.out.printf("x: %1$f; y: %2$f%n", point[0], point[1]));
    }

    public static double computeEulerStep(
            double currentX,
            double stepSize,
            double currentY,
            BiFunction<Double, Double, Double> differentialEquation
    ) {
        if (stepSize <= 0) {
            throw new IllegalArgumentException("stepSize should be greater than zero");
        }
        return currentY + stepSize * differentialEquation.apply(currentX, currentY);
    }

    public static ArrayList<double[]> solveEuler(
            double startX,
            double endX,
            double stepSize,
            double startY,
            BiFunction<Double, Double, Double> differentialEquation
    ) {
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
            currentY = computeEulerStep(currentX, stepSize, currentY, differentialEquation);
            currentX += stepSize;
            double[] nextPoint = {currentX, currentY};
            solutionPoints.add(nextPoint);
        }

        return solutionPoints;
    }
}