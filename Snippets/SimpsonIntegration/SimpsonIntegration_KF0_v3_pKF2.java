package com.thealgorithms.maths;

import java.util.function.DoubleUnaryOperator;

/**
 * Calculates definite integrals using the Composite Simpson's rule.
 *
 * <p>Reference:
 * https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
 *
 * <p>Given a function f and an even number N of subintervals over [a, b], we define:
 *
 * <pre>
 * h = (b - a) / N
 * x_i = a + i * h,  for i = 0, 1, ..., N
 * </pre>
 *
 * The integral is approximated by:
 *
 * <pre>
 * ∫[a,b] f(x) dx ≈ (h / 3) * (f(x₀)
 *                          + 4 * f(x₁)
 *                          + 2 * f(x₂)
 *                          + 4 * f(x₃)
 *                          + ...
 *                          + 2 * f(x_{N-2})
 *                          + 4 * f(x_{N-1})
 *                          + f(x_N))
 * </pre>
 */
public class SimpsonIntegration {

    public static void main(String[] args) {
        SimpsonIntegration integration = new SimpsonIntegration();

        int n = 16;
        double a = 1.0;
        double b = 3.0;

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted.");
            System.exit(1);
        }

        double integralEvaluation = integration.simpsonsMethod(a, b, n, SimpsonIntegration::sampleFunction);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }

    /**
     * Applies the Composite Simpson's rule to approximate the integral of a function f
     * over the interval [a, b] using n subintervals.
     *
     * @param a lower bound of the integration interval
     * @param b upper bound of the integration interval
     * @param n number of subintervals (must be even, n = 2 * k)
     * @param f function to integrate
     * @return approximate value of the integral
     */
    public double simpsonsMethod(double a, double b, int n, DoubleUnaryOperator f) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double fx = f.applyAsDouble(x);

            if (i == 0 || i == n) {
                sum += fx;
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (i % 2 != 0) {
                sum += 4.0 * fx;
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                sum += 2.0 * fx;
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        return (h / 3.0) * sum;
    }

    /**
     * Sample function:
     * f(x) = e^(-x) * (4 - x^2)
     */
    public static double sampleFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}