package com.thealgorithms.maths;

public class Class1 {

    private static final int DEFAULT_INTERVALS = 16;
    private static final double DEFAULT_LOWER_LIMIT = 1.0;
    private static final double DEFAULT_UPPER_LIMIT = 3.0;

    public static void main(String[] args) {
        int intervals = DEFAULT_INTERVALS;
        double lowerLimit = DEFAULT_LOWER_LIMIT;
        double upperLimit = DEFAULT_UPPER_LIMIT;

        validateIntervals(intervals);

        double stepSize = computeStepSize(lowerLimit, upperLimit, intervals);
        double integral = integrateSimpson(lowerLimit, upperLimit, intervals);

        System.out.println("The integral is equal to: " + integral);
    }

    private static void validateIntervals(int intervals) {
        if (!isEven(intervals)) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }
    }

    private static double computeStepSize(double lowerLimit, double upperLimit, int intervals) {
        return (upperLimit - lowerLimit) / intervals;
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static double integrateSimpson(double lowerLimit, double upperLimit, int intervals) {
        double stepSize = computeStepSize(lowerLimit, upperLimit, intervals);
        double sum = 0.0;

        for (int i = 0; i <= intervals; i++) {
            double currentX = lowerLimit + i * stepSize;
            double functionValue = evaluateFunction(currentX);
            double coefficient = getSimpsonCoefficient(i, intervals);

            sum += coefficient * functionValue;
            System.out.println("Multiply f(x" + i + ") by " + (int) coefficient);
        }

        return (stepSize / 3.0) * sum;
    }

    private static double getSimpsonCoefficient(int index, int lastIndex) {
        if (index == 0 || index == lastIndex) {
            return 1.0;
        }
        return (index % 2 == 0) ? 2.0 : 4.0;
    }

    public static double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}