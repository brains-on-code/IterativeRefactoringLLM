package com.thealgorithms.maths;

public class SimpsonIntegration {

    private static final int DEFAULT_INTERVALS = 16;
    private static final double DEFAULT_LOWER_BOUND = 1.0;
    private static final double DEFAULT_UPPER_BOUND = 3.0;

    public static void main(String[] args) {
        int intervals = DEFAULT_INTERVALS;
        double lowerBound = DEFAULT_LOWER_BOUND;
        double upperBound = DEFAULT_UPPER_BOUND;

        validateEvenIntervals(intervals);

        SimpsonIntegration integration = new SimpsonIntegration();
        double result = integration.integrate(lowerBound, upperBound, intervals);
        System.out.println("The integral is equal to: " + result);
    }

    private static void validateEvenIntervals(int intervals) {
        if (!isEven(intervals)) {
            throw new IllegalArgumentException("Number of intervals must be even for Simpson's method.");
        }
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }

    public double integrate(double lowerBound, double upperBound, int intervals) {
        double stepSize = (upperBound - lowerBound) / intervals;
        double weightedSum = 0.0;

        for (int i = 0; i <= intervals; i++) {
            double x = lowerBound + i * stepSize;
            double functionValue = evaluateFunction(x);
            weightedSum += getWeight(i, intervals) * functionValue;
        }

        return (stepSize / 3.0) * weightedSum;
    }

    private int getWeight(int index, int lastIndex) {
        if (isEndpoint(index, lastIndex)) {
            return 1;
        }
        return isOdd(index) ? 4 : 2;
    }

    private boolean isEndpoint(int index, int lastIndex) {
        return index == 0 || index == lastIndex;
    }

    private boolean isOdd(int index) {
        return index % 2 != 0;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}