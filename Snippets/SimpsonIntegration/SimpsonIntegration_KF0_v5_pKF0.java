package com.thealgorithms.maths;

public class SimpsonIntegration {

    /**
     * Calculate definite integrals using Composite Simpson's rule.
     * Wiki: https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
     *
     * Given a function f and an even number n of intervals that divide the integration interval
     * [a, b], we calculate the step h = (b - a) / n and evaluate:
     *
     * I = h/3 * {f(x0) + 4*f(x1) + 2*f(x2) + 4*f(x3) + ... + 2*f(x_{n-2}) + 4*f(x_{n-1}) + f(x_n)}
     */
    public static void main(String[] args) {
        int intervals = 16; // must be even
        double lowerBound = 1.0;
        double upperBound = 3.0;

        double integral = integrateWithSimpson(lowerBound, upperBound, intervals);
        System.out.println("The integral is equal to: " + integral);
    }

    /**
     * Composite Simpson's rule over [lowerBound, upperBound] with the given number of intervals.
     *
     * @param lowerBound lower bound of integration
     * @param upperBound upper bound of integration
     * @param intervals  number of intervals (must be even, n = 2 * k)
     * @return approximate value of the integral
     */
    public static double integrateWithSimpson(double lowerBound, double upperBound, int intervals) {
        validateEvenIntervals(intervals);

        double stepSize = computeStepSize(lowerBound, upperBound, intervals);
        double weightedSum = 0.0;

        for (int i = 0; i <= intervals; i++) {
            double x = lowerBound + i * stepSize;
            double functionValue = f(x);
            int weight = getSimpsonWeight(i, intervals);
            weightedSum += weight * functionValue;
        }

        return (stepSize / 3.0) * weightedSum;
    }

    private static void validateEvenIntervals(int intervals) {
        if (intervals % 2 != 0) {
            throw new IllegalArgumentException(
                "Number of intervals must be even for Simpson's method."
            );
        }
    }

    private static double computeStepSize(double lowerBound, double upperBound, int intervals) {
        return (upperBound - lowerBound) / intervals;
    }

    private static int getSimpsonWeight(int index, int lastIndex) {
        if (index == 0 || index == lastIndex) {
            return 1;
        }
        return (index % 2 == 0) ? 2 : 4;
    }

    // Sample function f
    // f(x) = e^(-x) * (4 - x^2)
    public static double f(double x) {
        return Math.exp(-x) * (4 - x * x);
        // return Math.sqrt(x);
    }
}