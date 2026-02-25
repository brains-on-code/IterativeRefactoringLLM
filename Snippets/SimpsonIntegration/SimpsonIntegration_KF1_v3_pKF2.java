package com.thealgorithms.maths;

import java.util.ArrayList;
import java.util.List;

public class Class1 {

    /**
     * Demonstrates numerical integration using Simpson's rule.
     *
     * Integrates the function defined in {@link #evaluateFunction(double)} on the
     * interval [1, 3] using 16 subintervals.
     */
    public static void main(String[] args) {
        Class1 integrator = new Class1();

        int subintervalCount = 16; // Simpson's rule requires an even number of subintervals
        double lowerBound = 1.0;
        double upperBound = 3.0;

        if (subintervalCount % 2 != 0) {
            System.out.println("Number of subintervals must be even for Simpson's method. Aborted.");
            System.exit(1);
        }

        double stepSize = (upperBound - lowerBound) / subintervalCount;
        double result = integrator.integrateWithSimpson(subintervalCount, stepSize, lowerBound);
        System.out.println("The integral is equal to: " + result);
    }

    /**
     * Computes the integral of {@link #evaluateFunction(double)} using Simpson's rule.
     *
     * @param subintervalCount number of subintervals (must be even)
     * @param stepSize         step size
     * @param lowerBound       lower bound of integration
     * @return approximate value of the integral
     */
    public double integrateWithSimpson(int subintervalCount, double stepSize, double lowerBound) {
        List<Double> functionValues = new ArrayList<>(subintervalCount + 1);
        double x = lowerBound;

        for (int i = 0; i <= subintervalCount; i++) {
            functionValues.add(evaluateFunction(x));
            x += stepSize;
        }

        double weightedSum = 0.0;
        int lastIndex = functionValues.size() - 1;

        for (int i = 0; i <= lastIndex; i++) {
            double coefficient;
            if (i == 0 || i == lastIndex) {
                coefficient = 1.0; // Endpoints
            } else if (i % 2 != 0) {
                coefficient = 4.0; // Odd indices
            } else {
                coefficient = 2.0; // Even indices (non-endpoints)
            }
            weightedSum += coefficient * functionValues.get(i);
            System.out.println("Multiply f(x" + i + ") by " + (int) coefficient);
        }

        return (stepSize / 3.0) * weightedSum;
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
}