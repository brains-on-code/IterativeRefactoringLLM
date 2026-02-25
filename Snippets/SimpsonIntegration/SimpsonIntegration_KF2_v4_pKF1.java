package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {

    public static void main(String[] args) {
        SimpsonIntegration simpsonIntegration = new SimpsonIntegration();

        int subIntervalCount = 16;
        double lowerBound = 1.0;
        double upperBound = 3.0;

        if (subIntervalCount % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperBound - lowerBound) / (double) subIntervalCount;
        double integralApproximation = simpsonIntegration.integrateWithSimpson(subIntervalCount, stepSize, lowerBound);
        System.out.println("The integral is equal to: " + integralApproximation);
    }

    public double integrateWithSimpson(int subIntervalCount, double stepSize, double lowerBound) {
        TreeMap<Integer, Double> functionValuesByIndex = new TreeMap<>();
        double currentX = lowerBound;

        for (int index = 0; index <= subIntervalCount; index++) {
            double functionValue = evaluateFunction(currentX);
            functionValuesByIndex.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedSum = 0.0;
        int lastIndex = functionValuesByIndex.size() - 1;

        for (int index = 0; index < functionValuesByIndex.size(); index++) {
            double functionValue = functionValuesByIndex.get(index);

            if (index == 0 || index == lastIndex) {
                weightedSum += functionValue;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSum += 4.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSum += 2.0 * functionValue;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSum;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}