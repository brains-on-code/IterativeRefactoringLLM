package com.thealgorithms.maths;

class ComplexNumber {
    private final Double real;
    private final Double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber(double real) {
        this(real, null);
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
        double twoA = 2 * a;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDiscriminant) / twoA;
            double root2 = (-b - sqrtDiscriminant) / twoA;
            return new ComplexNumber[] {
                new ComplexNumber(root1),
                new ComplexNumber(root2)
            };
        }

        if (discriminant == 0) {
            double root = -b / twoA;
            return new ComplexNumber[] {
                new ComplexNumber(root)
            };
        }

        double realPart = -b / twoA;
        double imaginaryPart = Math.sqrt(-discriminant) / twoA;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}