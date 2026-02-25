package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {

    public static void main(String[] args) {
        SimpsonIntegration integration = new SimpsonIntegration();

        int n = 16;
        double a = 1;
        double b = 3;

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }

        double h = (b - a) / (double) n;
        double integralEvaluation = integration.simpsonsMethod(n, h, a);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }

    public double simpsonsMethod(int n, double h, double a) {
        TreeMap<Integer, Double> functionValues = new TreeMap<>();
        double x = a;

        for (int i = 0; i <= n; i++) {
            functionValues.put(i, f(x));
            x += h;
        }

        double weightedSum = 0;
        int lastIndex = functionValues.size() - 1;

        for (int i = 0; i <= lastIndex; i++) {
            double fx = functionValues.get(i);
            int weight;

            if (i == 0 || i == lastIndex) {
                weight = 1;
            } else if (i % 2 != 0) {
                weight = 4;
            } else {
                weight = 2;
            }

            weightedSum += weight * fx;
            System.out.println("Multiply f(x" + i + ") by " + weight);
        }

        return (h / 3.0) * weightedSum;
    }

    public double f(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}