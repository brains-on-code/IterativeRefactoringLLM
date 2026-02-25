package com.thealgorithms.maths;

/**
 * This class represents a complex number which has real and imaginary parts.
 */
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

/**
 * Quadratic Equation Formula is used to find
 * the roots of a quadratic equation of the form ax^2 + bx + c = 0
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadratic_equation">Quadratic Equation</a>
 */
public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0.
     *
     * @param coefficientA coefficient of x^2
     * @param coefficientB coefficient of x
     * @param coefficientC constant term
     * @return array of roots as ComplexNumber objects
     */
    public ComplexNumber[] solveEquation(double coefficientA, double coefficientB, double coefficientC) {
        double discriminant = coefficientB * coefficientB - 4 * coefficientA * coefficientC;
        double denominator = 2 * coefficientA;

        if (discriminant > 0) {
            double squareRootOfDiscriminant = Math.sqrt(discriminant);
            double firstRoot = (-coefficientB + squareRootOfDiscriminant) / denominator;
            double secondRoot = (-coefficientB - squareRootOfDiscriminant) / denominator;
            return new ComplexNumber[] {
                new ComplexNumber(firstRoot),
                new ComplexNumber(secondRoot)
            };
        }

        if (discriminant == 0) {
            double repeatedRoot = -coefficientB / denominator;
            return new ComplexNumber[] {
                new ComplexNumber(repeatedRoot)
            };
        }

        double realPart = -coefficientB / denominator;
        double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}