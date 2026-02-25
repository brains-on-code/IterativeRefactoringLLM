package com.thealgorithms.maths;

import java.util.TreeMap;

public class Class1 {

    public static void main(String[] args) {
        Class1 simpsonIntegrator = new Class1();

        int intervals = 16;
        double lowerLimit = 1;
        double upperLimit = 3;

        if (intervals % 2 != 0) {
            System.out.println("n must be even number for Simpson's method. Aborted");
            System.exit(1);
        }

        double stepSize = (upperLimit - lowerLimit) / (double) intervals;
        double integral = simpsonIntegrator.integrateSimpson(intervals, stepSize, lowerLimit);
        System.out.println("The integral is equal to: " + integral);
    }

    public double integrateSimpson(int intervals, double stepSize, double lowerLimit) {
        TreeMap<Integer, Double> functionValues = new TreeMap<>();
        double x = lowerLimit;

        for (int i = 0; i <= intervals; i++) {
            double fx = evaluateFunction(x);
            functionValues.put(i, fx);
            x += stepSize;
        }

        double sum = 0;
        int size = functionValues.size();

        for (int i = 0; i < size; i++) {
            double coefficient;
            if (i == 0 || i == size - 1) {
                coefficient = 1;
            } else if (i % 2 != 0) {
                coefficient = 4;
            } else {
                coefficient = 2;
            }
            sum += coefficient * functionValues.get(i);
            System.out.println("Multiply f(x" + i + ") by " + (int) coefficient);
        }

        return (stepSize / 3) * sum;
    }

    public double evaluateFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}