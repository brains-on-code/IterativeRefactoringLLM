package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs numerical integration of a specific function using Simpson's rule
 * over a fixed interval.
 */
public class Class1 {

    private static final int DEFAULT_SUBINTERVAL_COUNT = 16;
    private static final double DEFAULT_LOWER_BOUND = 1.0;
    private static final double DEFAULT_UPPER_BOUND = 3.0;

    /**
     * Integrates {@link #evaluateFunction(double)} on [1, 3] using Simpson's rule
     * with 16 subintervals and prints the result.
     */
    public static void main(String[] args) {
        Class1 integrator = new Class1();

        int subintervalCount = DEFAULT_SUBINTERVAL_COUNT;
        double lowerBound = DEFAULT_LOWER_BOUND;
        double upperBound = DEFAULT_UPPER_BOUND;

        if (!isEven(subintervalCount)) {
            System.out.println("Number of subintervals must be even for Simpson's method. Aborted.");
            System.exit(1);
        }

        double stepSize = (upperBound - lowerBound) / subintervalCount;
        double result = integrator.integrateWithSimpson(subintervalCount, stepSize, lowerBound);
        System.out.println("The integral is equal to: " + result);
    }

    /**
     * Approximates the integral of {@link #evaluateFunction(double)} using Simpson's rule.
     *
     * @param subintervalCount number of subintervals (must be even)
     * @param stepSize         distance between consecutive x-values
     * @param lowerBound       lower bound of integration
     * @return approximate value of the integral
     */
    public double integrateWithSimpson(int subintervalCount, double stepSize, double lowerBound) {
        List<Double> functionValues = sampleFunctionValues(subintervalCount, stepSize, lowerBound);

        double weightedSum = 0.0;
        int lastIndex = functionValues.size() - 1;

        for (int i = 0; i <= lastIndex; i++) {
            double coefficient = simpsonCoefficient(i, lastIndex);
            weightedSum += coefficient * functionValues.get(i);
            System.out.println("Multiply f(x" + i + ") by " + (int) coefficient);
        }

        return (stepSize / 3.0) * weightedSum;
    }

    /**
     * Samples {@link #evaluateFunction(double)} at equally spaced points.
     *
     * @param subintervalCount number of subintervals
     * @param stepSize         distance between consecutive x-values
     * @param lowerBound       starting x-value
     * @return list of sampled function values
     */
    private List<Double> sampleFunctionValues(int subintervalCount, double stepSize, double lowerBound) {
        List<Double> functionValues = new ArrayList<>(subintervalCount + 1);
        double x = lowerBound;

        for (int i = 0; i <= subintervalCount; i++) {
            functionValues.add(evaluateFunction(x));
            x += stepSize;
        }

        return functionValues;
    }

    /**
     * Returns the Simpson's rule coefficient for a given index.
     *
     * @param index     current index
     * @param lastIndex last index in the list of function values
     * @return Simpson's rule coefficient (1, 4, or 2)
     */
    private double simpsonCoefficient(int index, int lastIndex) {
        if (index == 0 || index == lastIndex) {
            return 1.0;
        }
        return (index % 2 != 0) ? 4.0 : 2.0;
    }

    /**
     * Function to be integrated:
     * f(x) = e^(-x) * (4 - x^2)
     *
     * @param x input value
     * @return value of the function at x
     */
    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - x * x);
    }

    private static boolean isEven(int value) {
        return value % 2 == 0;
    }
}