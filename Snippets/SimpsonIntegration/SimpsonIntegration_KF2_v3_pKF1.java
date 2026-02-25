package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {

    public static void main(String[] args) {
        SimpsonIntegration simpsonIntegration = new SimpsonIntegration();

        int numberOfSubIntervals = 16;
        double lowerLimit = 1.0;
        double upperLimit = 3.0;

        if (numberOfSubIntervals % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / (double) numberOfSubIntervals;
        double integralApproximation = simpsonIntegration.integrateWithSimpson(numberOfSubIntervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integralApproximation);
    }

    public double integrateWithSimpson(int numberOfSubIntervals, double stepSize, double lowerLimit) {
        TreeMap<Integer, Double> functionValuesByIndex = new TreeMap<>();
        double currentX = lowerLimit;

        for (int index = 0; index <= numberOfSubIntervals; index++) {
            double functionValueAtX = evaluateFunction(currentX);
            functionValuesByIndex.put(index, functionValueAtX);
            currentX += stepSize;
        }

        double weightedSum = 0.0;
        int lastIndex = functionValuesByIndex.size() - 1;

        for (int index = 0; index < functionValuesByIndex.size(); index++) {
            double functionValueAtX = functionValuesByIndex.get(index);

            if (index == 0 || index == lastIndex) {
                weightedSum += functionValueAtX;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSum += 4.0 * functionValueAtX;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSum += 2.0 * functionValueAtX;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSum;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}