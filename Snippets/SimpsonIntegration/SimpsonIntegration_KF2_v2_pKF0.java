package com.thealgorithms.maths;

public class SimpsonIntegration {

    private static final int DEFAULT_INTERVALS = 16;
    private static final double LOWER_BOUND = 1.0;
    private static final double UPPER_BOUND = 3.0;

    public static void main(String[] args) {
        int intervals = DEFAULT_INTERVALS;
        double lowerLimit = LOWER_BOUND;
        double upperLimit = UPPER_BOUND;

        validateEvenIntervals(intervals);

        SimpsonIntegration integration = new SimpsonIntegration();
        double result = integration.simpsonsMethod(lowerLimit, upperLimit, intervals);
        System.out.println("The integral is equal to: " + result);
    }

    private static void validateEvenIntervals(int intervals) {
        if (intervals % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }
    }

    public double simpsonsMethod(double lowerLimit, double upperLimit, int intervals) {
        double stepSize = (upperLimit - lowerLimit) / intervals;
        double weightedSum = 0.0;

        for (int i = 0; i <= intervals; i++) {
            double x = lowerLimit + i * stepSize;
            double functionValue = evaluateFunction(x);

            if (isFirstOrLastPoint(i, intervals)) {
                weightedSum += functionValue;
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (isOddIndex(i)) {
                weightedSum += 4 * functionValue;
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                weightedSum += 2 * functionValue;
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSum;
    }

    private boolean isFirstOrLastPoint(int index, int lastIndex) {
        return index == 0 || index == lastIndex;
    }

    private boolean isOddIndex(int index) {
        return index % 2 != 0;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}