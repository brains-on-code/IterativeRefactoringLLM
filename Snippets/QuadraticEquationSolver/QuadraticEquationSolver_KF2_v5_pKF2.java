package com.thealgorithms.maths;

class ComplexNumber {
    private final double real;
    private final double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber(double real) {
        this(real, 0.0);
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }
}

public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation ax² + bx + c = 0.
     *
     * @param a coefficient of x² (must not be 0)
     * @param b coefficient of x
     * @param c constant term
     * @return an array of ComplexNumber representing the roots:
     *         - two distinct real roots if discriminant > 0
     *         - one real double root if discriminant == 0
     *         - two complex conjugate roots if discriminant < 0
     * @throws IllegalArgumentException if a == 0
     */
    public ComplexNumber[] solveEquation(double a, double b, double c) {
        validateQuadraticCoefficient(a);

        double discriminant = calculateDiscriminant(a, b, c);
        double denominator = 2 * a;

        if (discriminant > 0) {
            return solveWithPositiveDiscriminant(b, discriminant, denominator);
        } else if (discriminant == 0) {
            return solveWithZeroDiscriminant(b, denominator);
        } else {
            return solveWithNegativeDiscriminant(b, discriminant, denominator);
        }
    }

    private void validateQuadraticCoefficient(double a) {
        if (a == 0) {
            throw new IllegalArgumentException(
                "Coefficient 'a' must not be zero for a quadratic equation."
            );
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    private ComplexNumber[] solveWithPositiveDiscriminant(
        double b,
        double discriminant,
        double denominator
    ) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDiscriminant) / denominator;
        double root2 = (-b - sqrtDiscriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(root1),
            new ComplexNumber(root2)
        };
    }

    private ComplexNumber[] solveWithZeroDiscriminant(double b, double denominator) {
        double root = -b / denominator;
        return new ComplexNumber[] { new ComplexNumber(root) };
    }

    private ComplexNumber[] solveWithNegativeDiscriminant(
        double b,
        double discriminant,
        double denominator
    ) {
        double realPart = -b / denominator;
        double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}