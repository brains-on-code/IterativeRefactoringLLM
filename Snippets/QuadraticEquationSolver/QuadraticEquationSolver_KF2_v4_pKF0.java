package com.thealgorithms.maths;

final class ComplexNumber {
    private final double real;
    private final double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber(double real) {
        this(real, 0.0);
    }

    double getReal() {
        return real;
    }

    double getImaginary() {
        return imaginary;
    }

    boolean hasZeroImaginaryPart() {
        return imaginary == 0.0;
    }
}

public class QuadraticEquationSolver {

    public ComplexNumber[] solveEquation(double a, double b, double c) {
        validateLeadingCoefficient(a);

        double discriminant = calculateDiscriminant(a, b, c);

        if (discriminant > 0) {
            return solveWithTwoRealRoots(a, b, discriminant);
        }

        if (discriminant == 0) {
            return solveWithSingleRealRoot(a, b);
        }

        return solveWithComplexRoots(a, b, discriminant);
    }

    private void validateLeadingCoefficient(double a) {
        if (a == 0.0) {
            throw new IllegalArgumentException(
                "Coefficient 'a' must not be zero for a quadratic equation."
            );
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return (b * b) - (4.0 * a * c);
    }

    private ComplexNumber[] solveWithTwoRealRoots(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double denominator = 2.0 * a;

        double firstRoot = (-b + sqrtDiscriminant) / denominator;
        double secondRoot = (-b - sqrtDiscriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(firstRoot),
            new ComplexNumber(secondRoot)
        };
    }

    private ComplexNumber[] solveWithSingleRealRoot(double a, double b) {
        double denominator = 2.0 * a;
        double root = -b / denominator;

        return new ComplexNumber[] { new ComplexNumber(root) };
    }

    private ComplexNumber[] solveWithComplexRoots(double a, double b, double discriminant) {
        double denominator = 2.0 * a;
        double realPart = -b / denominator;
        double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}