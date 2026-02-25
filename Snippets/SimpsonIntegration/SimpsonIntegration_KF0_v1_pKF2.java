package com.thealgorithms.maths;

import java.util.TreeMap;

/**
 * Calculates definite integrals using the Composite Simpson's rule.
 *
 * <p>Reference:
 * https://en.wikipedia.org/wiki/Simpson%27s_rule#Composite_Simpson's_rule
 *
 * <p>Given a function f and an even number N of subintervals over [a, b], we define:
 * <pre>
 * h = (b - a) / N
 * x_i = a + i * h,  for i = 0, 1, ..., N
 * </pre>
 *
 * The integral is approximated by:
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

        int n = 16; // number of subintervals (must be even)
        double a = 1.0; // start of interval
        double b = 3.0; // end of interval

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted.");
            System.exit(1);
        }

        double h = (b - a) / n;
        double integralEvaluation = integration.simpsonsMethod(n, h, a);
        System.out.println("The integral is equal to: " + integralEvaluation);
    }

    /**
     * Applies the Composite Simpson's rule.
     *
     * @param n number of subintervals (must be even, n = 2 * k)
     * @param h step size, h = (b - a) / n
     * @param a start of the integration interval
     * @return approximate value of the integral
     */
    public double simpsonsMethod(int n, double h, double a) {
        // Key: index i, Value: f(x_i)
        TreeMap<Integer, Double> data = new TreeMap<>();

        double xi = a;
        for (int i = 0; i <= n; i++) {
            data.put(i, f(xi));
            xi += h;
        }

        double sum = 0.0;
        int lastIndex = data.size() - 1;

        for (int i = 0; i <= lastIndex; i++) {
            double value = data.get(i);

            if (i == 0 || i == lastIndex) {
                sum += value;
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (i % 2 != 0) {
                sum += 4.0 * value;
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                sum += 2.0 * value;
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        return (h / 3.0) * sum;
    }

    /**
     * Sample function:
     * f(x) = e^(-x) * (4 - x^2)
     */
    public double f(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
        // Example alternative:
        // return Math.sqrt(x);
    }
}