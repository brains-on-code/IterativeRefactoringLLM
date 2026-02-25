package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
 * If imaginaryPart is 0, the root is real.
 */
final class QuadraticRoot {
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
public final class QuadraticEquationSolver {

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
        validateQuadraticCoefficient(a);

        final double discriminant = calculateDiscriminant(a, b, c);
        final double denominator = 2 * a;

        if (discriminant > 0.0) {
            return solveWithPositiveDiscriminant(b, discriminant, denominator);
        } else if (discriminant == 0.0) {
            return solveWithZeroDiscriminant(b, denominator);
        } else {
            return solveWithNegativeDiscriminant(b, discriminant, denominator);
        }
    }

    private void validateQuadraticCoefficient(double a) {
        if (a == 0.0) {
            throw new IllegalArgumentException(
                "Coefficient 'a' must not be zero for a quadratic equation."
            );
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return (b * b) - (4.0 * a * c);
    }

    private QuadraticRoot[] solveWithPositiveDiscriminant(
        double b,
        double discriminant,
        double denominator
    ) {
        final double sqrtDiscriminant = Math.sqrt(discriminant);
        final double root1 = (-b + sqrtDiscriminant) / denominator;
        final double root2 = (-b - sqrtDiscriminant) / denominator;

        return new QuadraticRoot[] {
            new QuadraticRoot(root1),
            new QuadraticRoot(root2)
        };
    }

    private QuadraticRoot[] solveWithZeroDiscriminant(double b, double denominator) {
        final double root = -b / denominator;
        return new QuadraticRoot[] { new QuadraticRoot(root) };
    }

    private QuadraticRoot[] solveWithNegativeDiscriminant(
        double b,
        double discriminant,
        double denominator
    ) {
        final double realPart = -b / denominator;
        final double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}