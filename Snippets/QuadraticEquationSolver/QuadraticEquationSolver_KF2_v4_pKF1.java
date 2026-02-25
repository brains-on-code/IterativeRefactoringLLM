package com.thealgorithms.maths;

class ComplexNumber {
    private final Double real;
    private final Double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber(double real) {
        this.real = real;
        this.imaginary = null;
    }

    public Double getReal() {
        return real;
    }

    public Double getImaginary() {
        return imaginary;
    }
}

public class QuadraticEquationSolver {

    public ComplexNumber[] solveEquation(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double denominator = 2 * a;

            ComplexNumber firstRoot = new ComplexNumber((-b + sqrtDiscriminant) / denominator);
            ComplexNumber secondRoot = new ComplexNumber((-b - sqrtDiscriminant) / denominator);

            return new ComplexNumber[] {firstRoot, secondRoot};
        }

        if (discriminant == 0) {
            double denominator = 2 * a;
            ComplexNumber repeatedRoot = new ComplexNumber(-b / denominator);

            return new ComplexNumber[] {repeatedRoot};
        }

        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);

        ComplexNumber firstComplexRoot = new ComplexNumber(realPart, imaginaryPart);
        ComplexNumber secondComplexRoot = new ComplexNumber(realPart, -imaginaryPart);

        return new ComplexNumber[] {firstComplexRoot, secondComplexRoot};
    }
}