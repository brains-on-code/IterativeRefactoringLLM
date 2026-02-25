package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonsRuleIntegrator {

    public static void main(String[] args) {
        SimpsonsRuleIntegrator integrator = new SimpsonsRuleIntegrator();

        int subIntervalCount = 16;
        double lowerBound = 1;
        double upperBound = 3;

        if (subIntervalCount % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperBound - lowerBound) / (double) subIntervalCount;
        double integralApproximation =
                integrator.integrateUsingSimpsonsRule(subIntervalCount, stepSize, lowerBound);
        System.out.println("The integral is equal to: " + integralApproximation);
    }

    public double integrateUsingSimpsonsRule(
            int subIntervalCount, double stepSize, double lowerBound) {

        TreeMap<Integer, Double> functionValuesByIndex = new TreeMap<>();
        double currentX = lowerBound;

        for (int index = 0; index <= subIntervalCount; index++) {
            double functionValue = evaluateFunction(currentX);
            functionValuesByIndex.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedFunctionValueSum = 0;
        int lastIndex = functionValuesByIndex.size() - 1;

        for (int index = 0; index < functionValuesByIndex.size(); index++) {
            double functionValue = functionValuesByIndex.get(index);

            if (index == 0 || index == lastIndex) {
                weightedFunctionValueSum += functionValue;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedFunctionValueSum += 4.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedFunctionValueSum += 2.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedFunctionValueSum;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}