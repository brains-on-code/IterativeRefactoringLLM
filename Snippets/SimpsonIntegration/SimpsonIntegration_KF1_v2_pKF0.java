package com.thealgorithms.maths;

public class Class1 {

    private static final int DEFAULT_INTERVALS = 16;
    private static final double DEFAULT_LOWER_LIMIT = 1.0;
    private static final double DEFAULT_UPPER_LIMIT = 3.0;

    public static void main(String[] args) {
        int intervals = DEFAULT_INTERVALS;
        double lowerLimit = DEFAULT_LOWER_LIMIT;
        double upperLimit = DEFAULT_UPPER_LIMIT;

        if (!isEven(intervals)) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / intervals;
        double integral = integrateSimpson(intervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integral);
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static double integrateSimpson(int intervals, double stepSize, double lowerLimit) {
        double sum = 0.0;
        double x = lowerLimit;

        for (int i = 0; i <= intervals; i++) {
            double fx = evaluateFunction(x);
            double coefficient = getSimpsonCoefficient(i, intervals);

            sum += coefficient * fx;
            System.out.println("Multiply f(x" + i + ") by " + (int) coefficient);

            x += stepSize;
        }

        return (stepSize / 3.0) * sum;
    }

    private static double getSimpsonCoefficient(int index, int maxIndex) {
        if (index == 0 || index == maxIndex) {
            return 1.0;
        }
        return (index % 2 == 0) ? 2.0 : 4.0;
    }

    public static double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}