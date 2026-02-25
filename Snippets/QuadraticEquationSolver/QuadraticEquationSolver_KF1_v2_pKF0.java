package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
 * If imaginaryPart is 0, the root is real.
 */
class QuadraticRoot {
    private final double realPart;
    private final double imaginaryPart;

    QuadraticRoot(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    QuadraticRoot(double realPart) {
        this(realPart, 0.0);
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public boolean isReal() {
        return imaginaryPart == 0.0;
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
     * @throws IllegalArgumentException if a is 0
     */
    public QuadraticRoot[] solve(double a, double b, double c) {
        if (a == 0.0) {
            throw new IllegalArgumentException("Coefficient 'a' must not be zero for a quadratic equation.");
        }

        double discriminant = b * b - 4 * a * c;
        double twoA = 2 * a;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDiscriminant) / twoA;
            double root2 = (-b - sqrtDiscriminant) / twoA;
            return new QuadraticRoot[] { new QuadraticRoot(root1), new QuadraticRoot(root2) };
        } else if (discriminant == 0) {
            double root = -b / twoA;
            return new QuadraticRoot[] { new QuadraticRoot(root) };
        } else {
            double realPart = -b / twoA;
            double imaginaryPart = Math.sqrt(-discriminant) / twoA;
            return new QuadraticRoot[] {
                new QuadraticRoot(realPart, imaginaryPart),
                new QuadraticRoot(realPart, -imaginaryPart)
            };
        }
    }
}