package com.thealgorithms.maths;

public class SimpsonIntegration {

    /**
     * Calculate definite integrals using Composite Simpson's rule.
     * Wiki: https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
     *
     * Given a function f and an even number n of intervals that divide the integration interval
     * [a, b], we calculate the step h = (b - a) / n and evaluate:
     *
     * I = h/3 * {f(x0) + 4*f(x1) + 2*f(x2) + 4*f(x3) + ... + 2*f(x_{n-2}) + 4*f(x_{n-1}) + f(x_n)}
     */
    public static void main(String[] args) {
        int n = 16; // must be even
        double a = 1.0;
        double b = 3.0;

        ensureEvenIntervals(n);

        double h = computeStepSize(a, b, n);
        double integral = integrateWithSimpson(a, b, n);

        System.out.println("The integral is equal to: " + integral);
    }

    private static void ensureEvenIntervals(int intervals) {
        if (intervals % 2 != 0) {
            throw new IllegalArgumentException("Number of intervals n must be even for Simpson's method.");
        }
    }

    private static double computeStepSize(double a, double b, int n) {
        return (b - a) / n;
    }

    /**
     * Composite Simpson's rule over [a, b] with n subintervals.
     *
     * @param a lower bound of integration
     * @param b upper bound of integration
     * @param n number of intervals (must be even, n = 2 * k)
     * @return approximate value of the integral
     */
    public static double integrateWithSimpson(double a, double b, int n) {
        ensureEvenIntervals(n);

        double h = computeStepSize(a, b, n);
        double weightedSum = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double fx = f(x);
            int weight = simpsonWeight(i, n);

            weightedSum += weight * fx;
        }

        return (h / 3.0) * weightedSum;
    }

    private static int simpsonWeight(int index, int lastIndex) {
        if (index == 0 || index == lastIndex) {
            return 1;
        }
        return (index % 2 == 0) ? 2 : 4;
    }

    // Sample function f
    // f(x) = e^(-x) * (4 - x^2)
    public static double f(double x) {
        return Math.exp(-x) * (4 - x * x);
        // return Math.sqrt(x);
    }
}