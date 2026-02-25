package com.thealgorithms.maths;

class ComplexNumber {
    private final Double realPart;
    private final Double imaginaryPart;

    ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    ComplexNumber(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = null;
    }

    public Double getRealPart() {
        return realPart;
    }

    public Double getImaginaryPart() {
        return imaginaryPart;
    }
}

public class QuadraticEquationSolver {

    public ComplexNumber[] solveEquation(double coefficientA, double coefficientB, double coefficientC) {
        double discriminant = coefficientB * coefficientB - 4 * coefficientA * coefficientC;

        if (discriminant > 0) {
            double squareRootOfDiscriminant = Math.sqrt(discriminant);
            double denominator = 2 * coefficientA;

            ComplexNumber firstRoot =
                new ComplexNumber((-coefficientB + squareRootOfDiscriminant) / denominator);
            ComplexNumber secondRoot =
                new ComplexNumber((-coefficientB - squareRootOfDiscriminant) / denominator);

            return new ComplexNumber[] {firstRoot, secondRoot};
        }

        if (discriminant == 0) {
            double denominator = 2 * coefficientA;
            ComplexNumber repeatedRoot = new ComplexNumber(-coefficientB / denominator);

            return new ComplexNumber[] {repeatedRoot};
        }

        double realPart = -coefficientB / (2 * coefficientA);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * coefficientA);

        ComplexNumber firstComplexRoot = new ComplexNumber(realPart, imaginaryPart);
        ComplexNumber secondComplexRoot = new ComplexNumber(realPart, -imaginaryPart);

        return new ComplexNumber[] {firstComplexRoot, secondComplexRoot};
    }
}