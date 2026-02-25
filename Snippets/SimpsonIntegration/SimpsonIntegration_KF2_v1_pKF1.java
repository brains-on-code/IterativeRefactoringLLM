package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {

    public static void main(String[] args) {
        SimpsonIntegration simpsonIntegration = new SimpsonIntegration();

        int numberOfSubIntervals = 16;
        double lowerLimit = 1;
        double upperLimit = 3;

        if (numberOfSubIntervals % 2 != 0) {
            System.out.println("Number of sub-intervals must be even for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / (double) numberOfSubIntervals;
        double integralValue = simpsonIntegration.simpsonsMethod(numberOfSubIntervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integralValue);
    }

    public double simpsonsMethod(int numberOfSubIntervals, double stepSize, double lowerLimit) {
        TreeMap<Integer, Double> functionValues = new TreeMap<>();
        double currentX = lowerLimit;

        for (int index = 0; index <= numberOfSubIntervals; index++) {
            double functionValue = evaluateFunction(currentX);
            functionValues.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedSum = 0;
        int lastIndex = functionValues.size() - 1;

        for (int index = 0; index < functionValues.size(); index++) {
            double value = functionValues.get(index);

            if (index == 0 || index == lastIndex) {
                weightedSum += value;
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSum += 4.0 * value;
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSum += 2.0 * value;
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        return (stepSize / 3.0) * weightedSum;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}