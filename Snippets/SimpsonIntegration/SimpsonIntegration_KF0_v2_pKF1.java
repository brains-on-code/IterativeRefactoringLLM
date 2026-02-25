package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {

    /*
     * Calculate definite integrals by using Composite Simpson's rule.
     * Wiki: https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
     * Given f a function and an even number N of intervals that divide the integration interval
     * e.g. [a, b], we calculate the step h = (b-a)/N and create a table that contains all the x
     * points of the real axis xi = x0 + i*h and the value f(xi) that corresponds to these xi.
     *
     * To evaluate the integral use the formula below:
     * I = h/3 * {f(x0) + 4*f(x1) + 2*f(x2) + 4*f(x3) + ... + 2*f(xN-2) + 4*f(xN-1) + f(xN)}
     *
     */
    public static void main(String[] args) {
        SimpsonIntegration simpsonIntegration = new SimpsonIntegration();

        int numberOfSubIntervals = 16;
        double lowerBound = 1;
        double upperBound = 3;

        if (numberOfSubIntervals % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperBound - lowerBound) / (double) numberOfSubIntervals;
        double integralValue =
                simpsonIntegration.computeIntegralWithSimpson(numberOfSubIntervals, stepSize, lowerBound);
        System.out.println("The integral is equal to: " + integralValue);
    }

    /*
     * @param numberOfSubIntervals: Number of intervals (must be even number N=2*k)
     * @param stepSize: Step h = (b-a)/N
     * @param lowerBound: Starting point of the interval
     *
     * The interpolation points xi = x0 + i*h are stored in the functionValuesByIndex map
     *
     * @return result of the integral evaluation
     */
    public double computeIntegralWithSimpson(int numberOfSubIntervals, double stepSize, double lowerBound) {
        TreeMap<Integer, Double> functionValuesByIndex = new TreeMap<>(); // Key: i, Value: f(xi)
        double currentX = lowerBound;

        for (int index = 0; index <= numberOfSubIntervals; index++) {
            double functionValue = evaluateFunction(currentX);
            functionValuesByIndex.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedSum = 0;
        int lastIndex = functionValuesByIndex.size() - 1;

        for (int index = 0; index < functionValuesByIndex.size(); index++) {
            double valueAtIndex = functionValuesByIndex.get(index);

            if (index == 0 || index == lastIndex) {
                weightedSum += valueAtIndex;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSum += 4.0 * valueAtIndex;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSum += 2.0 * valueAtIndex;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSum;
    }

    // Sample function f
    // Function f(x) = e^(-x) * (4 - x^2)
    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
        //        return Math.sqrt(x);
    }
}