package com.thealgorithms.maths;

import java.util.TreeMap;

public class Class1 {

    public static void method1(String[] args) {
        Class1 simpsonsIntegrator = new Class1();

        int numberOfSubIntervals = 16;
        double lowerLimit = 1;
        double upperLimit = 3;

        if (numberOfSubIntervals % 2 != 0) {
            System.out.println("n must be even number for Simpsons method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / (double) numberOfSubIntervals;
        double integralValue =
                simpsonsIntegrator.method2(numberOfSubIntervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integralValue);
    }

    public double method2(int numberOfSubIntervals, double stepSize, double lowerLimit) {
        TreeMap<Integer, Double> functionValues = new TreeMap<>();
        double currentX = lowerLimit;

        for (int index = 0; index <= numberOfSubIntervals; index++) {
            double functionValue = method3(currentX);
            functionValues.put(index, functionValue);
            currentX += stepSize;
        }

        double weightedSum = 0;
        for (int index = 0; index < functionValues.size(); index++) {
            if (index == 0 || index == functionValues.size() - 1) {
                weightedSum += functionValues.get(index);
                System.out.println("Multiply f(x" + index + ") by 1");
            } else if (index % 2 != 0) {
                weightedSum += 4.0 * functionValues.get(index);
                System.out.println("Multiply f(x" + index + ") by 4");
            } else {
                weightedSum += 2.0 * functionValues.get(index);
                System.out.println("Multiply f(x" + index + ") by 2");
            }
        }

        weightedSum = stepSize / 3 * weightedSum;

        return weightedSum;
    }

    public double method3(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}