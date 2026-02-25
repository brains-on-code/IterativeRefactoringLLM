package com.thealgorithms.maths;


class ComplexNumber {
    Double real;
    Double imaginary;

    ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    ComplexNumber(double real) {
        this.real = real;
        this.imaginary = null;
    }
}


public class QuadraticEquationSolver {

    public ComplexNumber[] solveEquation(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        if (discriminant > 0) {
            return new ComplexNumber[] {new ComplexNumber((-b + Math.sqrt(discriminant)) / (2 * a)), new ComplexNumber((-b - Math.sqrt(discriminant)) / (2 * a))};
        }

        if (discriminant == 0) {
            return new ComplexNumber[] {new ComplexNumber((-b) / (2 * a))};
        }

        if (discriminant < 0) {
            double realPart = -b / (2 * a);
            double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);

            return new ComplexNumber[] {new ComplexNumber(realPart, imaginaryPart), new ComplexNumber(realPart, -imaginaryPart)};
        }

        return new ComplexNumber[] {};
    }
}
