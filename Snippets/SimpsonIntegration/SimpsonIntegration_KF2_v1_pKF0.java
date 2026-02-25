package com.thealgorithms.maths;

public class SimpsonIntegration {

    public static void main(String[] args) {
        int n = 16;
        double a = 1.0;
        double b = 3.0;

        if (n % 2 != 0) {
            System.out.println("n must be an even number for Simpson's method. Aborted");
            System.exit(1);
        }

        SimpsonIntegration integration = new SimpsonIntegration();
        double result = integration.simpsonsMethod(a, b, n);
        System.out.println("The integral is equal to: " + result);
    }

    public double simpsonsMethod(double a, double b, int n) {
        double h = (b - a) / n;
        double sum = 0.0;

        for (int i = 0; i <= n; i++) {
            double x = a + i * h;
            double fx = f(x);

            if (i == 0 || i == n) {
                sum += fx;
                System.out.println("Multiply f(x" + i + ") by 1");
            } else if (i % 2 != 0) {
                sum += 4 * fx;
                System.out.println("Multiply f(x" + i + ") by 4");
            } else {
                sum += 2 * fx;
                System.out.println("Multiply f(x" + i + ") by 2");
            }
        }

        return (h / 3.0) * sum;
    }

    public double f(double x) {
        return Math.exp(-x) * (4 - Math.pow(x, 2));
    }
}