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
     * Solves the quadratic equation ax^2 + bx + c = 0.
     *
     * @param a coefficient of x^2 (must not be 0)
     * @param b coefficient of x
     * @param c constant term
     * @return an array of ComplexNumber representing the roots:
     *         - two distinct roots if discriminant > 0
     *         - one root (double root) if discriminant == 0
     *         - two complex conjugate roots if discriminant < 0
     * @throws IllegalArgumentException if a == 0
     */
    public ComplexNumber[] solveEquation(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException("Coefficient 'a' must not be zero for a quadratic equation.");
        }

        double discriminant = b * b - 4 * a * c;
        double twoA = 2 * a;

        if (discriminant > 0) {
            return solveWithPositiveDiscriminant(b, discriminant, twoA);
        } else if (discriminant == 0) {
            return solveWithZeroDiscriminant(b, twoA);
        } else {
            return solveWithNegativeDiscriminant(b, discriminant, twoA);
        }
    }

    private ComplexNumber[] solveWithPositiveDiscriminant(double b, double discriminant, double twoA) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDiscriminant) / twoA;
        double root2 = (-b - sqrtDiscriminant) / twoA;

        return new ComplexNumber[] {
            new ComplexNumber(root1),
            new ComplexNumber(root2)
        };
    }

    private ComplexNumber[] solveWithZeroDiscriminant(double b, double twoA) {
        double root = -b / twoA;

        return new ComplexNumber[] {
            new ComplexNumber(root)
        };
    }

    private ComplexNumber[] solveWithNegativeDiscriminant(double b, double discriminant, double twoA) {
        double realPart = -b / twoA;
        double imaginaryPart = Math.sqrt(-discriminant) / twoA;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}