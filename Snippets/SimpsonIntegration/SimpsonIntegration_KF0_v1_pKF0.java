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
        SimpsonIntegration integration = new SimpsonIntegration();

        int n = 16; // must be even
        double a = 1.0;
        double b = 3.0;

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }

        double h = (b - a) / n;
        double integralEvaluation = integration.simpsonsMethod(n, h, a);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }

    /**
     * @param n number of intervals (must be even, n = 2 * k)
     * @param h step size, h = (b - a) / n
     * @param a starting point of the interval
     * @return result of the integral evaluation
     */
    public double simpsonsMethod(int n, double h, double a) {
        double integralSum = 0.0;

        for (int i = 0; i <= n; i++) {
            double xi = a + i * h;
            double fx = f(xi);

            int coefficient;
            if (i == 0 || i == n) {
                coefficient = 1;
            } else if (i % 2 != 0) {
                coefficient = 4;
            } else {
                coefficient = 2;
            }

            integralSum += coefficient * fx;
            System.out.println("Multiply f(x" + i + ") by " + coefficient);
        }

        return (h / 3.0) * integralSum;
    }

    // Sample function f
    // f(x) = e^(-x) * (4 - x^2)
    public double f(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
        // return Math.sqrt(x);
    }
}