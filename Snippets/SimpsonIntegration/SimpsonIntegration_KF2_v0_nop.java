package com.thealgorithms.maths;

import java.util.TreeMap;

public class SimpsonIntegration {


    public static void main(String[] args) {
        SimpsonIntegration integration = new SimpsonIntegration();

        int n = 16;
        double a = 1;
        double b = 3;

        if (n % 2 != 0) {
            System.out.println("n must be even number for Simpsons method. Aborted");
            System.exit(1);
        }

        double h = (b - a) / (double) n;
        double integralEvaluation = integration.simpsonsMethod(n, h, a);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }


    public double simpsonsMethod(int n, double h, double a) {
        TreeMap<Integer, Double> data = new TreeMap<>();
        double temp;
        double xi = a;

        for (int i = 0; i <= n; i++) {
            temp = f(xi);
            data.put(i, temp);
            xi += h;
        }

        double integralEvaluation = 0;
        for (int i = 0; i < data.size(); i++) {
            if (i == 0 || i == data.size() - 1) {
                integralEvaluation += data.get(i);
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (i % 2 != 0) {
                integralEvaluation += (double) 4 * data.get(i);
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                integralEvaluation += (double) 2 * data.get(i);
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        integralEvaluation = h / 3 * integralEvaluation;

        return integralEvaluation;
    }

    public double f(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}
