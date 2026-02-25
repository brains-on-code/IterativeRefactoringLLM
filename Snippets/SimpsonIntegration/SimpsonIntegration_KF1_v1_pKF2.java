package com.thealgorithms.maths;

import java.util.TreeMap;

public class Class1 {

    /**
     * Demonstrates numerical integration using Simpson's rule.
     *
     * Integrates the function defined in {@link #method3(double)} on the
     * interval [1, 3] using 16 subintervals.
     */
    public static void method1(String[] args) {
        Class1 integrator = new Class1();

        int n = 16;      // number of subintervals (must be even for Simpson's rule)
        double a = 1.0;  // lower bound of integration
        double b = 3.0;  // upper bound of integration

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }

        double h = (b - a) / n; // step size
        double result = integrator.method2(n, h, a);
        System.out.println("The integral is equal to: " + result);
    }

    /**
     * Computes the integral of {@link #method3(double)} using Simpson's rule.
     *
     * @param n number of subintervals (must be even)
     * @param h step size
     * @param a lower bound of integration
     * @return approximate value of the integral
     */
    public double method2(int n, double h, double a) {
        TreeMap<Integer, Double> values = new TreeMap<>();
        double x = a;

        // Evaluate f(x) at each node and store in the map
        for (int i = 0; i <= n; i++) {
            double fx = method3(x);
            values.put(i, fx);
            x += h;
        }

        // Apply Simpson's rule coefficients: 1, 4, 2, 4, ..., 4, 1
        double sum = 0;
        for (int i = 0; i < values.size(); i++) {
            if (i == 0 || i == values.size() - 1) {
                sum += values.get(i);
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (i % 2 != 0) {
                sum += 4.0 * values.get(i);
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                sum += 2.0 * values.get(i);
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        return (h / 3.0) * sum;
    }

    /**
     * Function to be integrated:
     * f(x) = e^(-x) * (4 - x^2)
     *
     * @param x input value
     * @return value of the function at x
     */
    public double method3(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}