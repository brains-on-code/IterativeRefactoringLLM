package com.thealgorithms.maths;

class ComplexNumber {
    private final double real;
    private final double imaginary;
    private final boolean imaginaryZero;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        this.imaginaryZero = imaginary == 0.0;
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

    public boolean isImaginaryZero() {
        return imaginaryZero;
    }
}

public class QuadraticEquationSolver {

    public ComplexNumber[] solveEquation(double a, double b, double c) {
        validateCoefficientA(a);

        double discriminant = calculateDiscriminant(a, b, c);

        if (discriminant > 0) {
            return solveWithTwoRealRoots(a, b, discriminant);
        }
        if (discriminant == 0) {
            return solveWithSingleRealRoot(a, b);
        }
        return solveWithComplexRoots(a, b, discriminant);
    }

    private void validateCoefficientA(double a) {
        if (a == 0) {
            throw new IllegalArgumentException(
                "Coefficient 'a' must not be zero for a quadratic equation."
            );
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return (b * b) - (4 * a * c);
    }

    private ComplexNumber[] solveWithTwoRealRoots(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double denominator = 2 * a;

        double root1 = (-b + sqrtDiscriminant) / denominator;
        double root2 = (-b - sqrtDiscriminant) / denominator;

        return new ComplexNumber[] { new ComplexNumber(root1), new ComplexNumber(root2) };
    }

    private ComplexNumber[] solveWithSingleRealRoot(double a, double b) {
        double denominator = 2 * a;
        double root = -b / denominator;

        return new ComplexNumber[] { new ComplexNumber(root) };
    }

    private ComplexNumber[] solveWithComplexRoots(double a, double b, double discriminant) {
        double denominator = 2 * a;
        double realPart = -b / denominator;
        double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}