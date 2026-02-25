package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonsRuleIntegrator {

    public static void main(String[] args) {
        SimpsonsRuleIntegrator integrator = new SimpsonsRuleIntegrator();

        int numberOfSubIntervals = 16;
        double lowerLimit = 1;
        double upperLimit = 3;

        if (numberOfSubIntervals % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / (double) numberOfSubIntervals;
        double integralApproximation =
                integrator.integrateUsingSimpsonsRule(numberOfSubIntervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integralApproximation);
    }

    public double integrateUsingSimpsonsRule(
            int numberOfSubIntervals, double stepSize, double lowerLimit) {

        TreeMap<Integer, Double> functionValuesByIndex = new TreeMap<>();
        double currentX = lowerLimit;

        for (int index = 0; index <= numberOfSubIntervals; index++) {
            double functionValue = evaluateFunction(currentX);
            functionValuesByIndex.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedSumOfFunctionValues = 0;
        int lastIndex = functionValuesByIndex.size() - 1;

        for (int index = 0; index < functionValuesByIndex.size(); index++) {
            double functionValue = functionValuesByIndex.get(index);

            if (index == 0 || index == lastIndex) {
                weightedSumOfFunctionValues += functionValue;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSumOfFunctionValues += 4.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSumOfFunctionValues += 2.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSumOfFunctionValues;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}