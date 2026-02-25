package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
 * If imaginaryPart is null, the root is real.
 */
class QuadraticRoot {
    Double realPart;
    Double imaginaryPart;

    QuadraticRoot(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    QuadraticRoot(double realPart) {
        this(realPart, 0.0);
    }
}

/**
 * Utility class for solving quadratic equations of the form:
 * ax^2 + bx + c = 0
 */
public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0.
     *
     * @param a coefficient of x^2 (must not be 0)
     * @param b coefficient of x
     * @param c constant term
     * @return an array of QuadraticRoot:
     *         - two roots if discriminant > 0
     *         - one root if discriminant == 0
     *         - two complex roots if discriminant < 0
     */
    public QuadraticRoot[] solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double denominator = 2 * a;

            double root1 = (-b + sqrtDiscriminant) / denominator;
            double root2 = (-b - sqrtDiscriminant) / denominator;

            return new QuadraticRoot[] {new QuadraticRoot(root1), new QuadraticRoot(root2)};
        }

        if (discriminant == 0) {
            double root = -b / (2 * a);
            return new QuadraticRoot[] {new QuadraticRoot(root)};
        }

        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);

        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}