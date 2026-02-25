package com.thealgorithms.maths;

import java.util.function.DoubleUnaryOperator;

/**
 * Numerical integration using the Composite Simpson's rule.
 *
 * <p>Reference:
 * https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
 *
 * <p>For a function f and an even number n of subintervals over [a, b]:
 *
 * <pre>
 * h = (b - a) / n
 * x_i = a + i * h,  for i = 0, 1, ..., n
 *
 * ∫[a,b] f(x) dx ≈ (h / 3) * (f(x₀)
 *                          + 4 * f(x₁)
 *                          + 2 * f(x₂)
 *                          + 4 * f(x₃)
 *                          + ...
 *                          + 2 * f(x_{n-2})
 *                          + 4 * f(x_{n-1})
 *                          + f(x_n))
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

        double integralEvaluation =
                integration.simpsonsMethod(a, b, n, SimpsonIntegration::sampleFunction);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }

    /**
     * Approximates the definite integral of a function f over [a, b] using the
     * Composite Simpson's rule with n subintervals.
     *
     * <p>Precondition: n must be even (n = 2k).
     *
     * @param a lower bound of the integration interval
     * @param b upper bound of the integration interval
     * @param n number of subintervals (must be even)
     * @param f function to integrate
     * @return approximate value of the integral
     */
    public double simpsonsMethod(double a, double b, int n, DoubleUnaryOperator f) {
        double h = (b - a) / n;
        double weightedSum = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double fx = f.applyAsDouble(x);
            double weight = simpsonWeight(i, n);

            weightedSum += weight * fx;
            System.out.println("Multiply f(x" + i + ") by " + (int) weight);
        }

        return (h / 3.0) * weightedSum;
    }

    /**
     * Returns the Simpson's rule weight for the i-th sample point.
     *
     * <ul>
     *   <li>1 for the first and last points (i = 0 or i = n)</li>
     *   <li>4 for odd interior points</li>
     *   <li>2 for even interior points</li>
     * </ul>
     *
     * @param i index of the sample point
     * @param n total number of subintervals
     * @return Simpson's rule weight for index i
     */
    private double simpsonWeight(int i, int n) {
        if (i == 0 || i == n) {
            return 1.0;
        }
        return (i % 2 == 0) ? 2.0 : 4.0;
    }

    /**
     * Sample function:
     * f(x) = e^(-x) * (4 - x^2)
     *
     * @param x input value
     * @return value of the sample function at x
     */
    public static double sampleFunction(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}