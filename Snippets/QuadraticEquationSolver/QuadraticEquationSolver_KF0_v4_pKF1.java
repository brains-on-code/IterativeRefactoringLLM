package com.thealgorithms.maths;

/**
 * This class represents a complex number which has real and imaginary parts.
 */
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
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double firstRoot = (-coefficientB + sqrtDiscriminant) / denominator;
            double secondRoot = (-coefficientB - sqrtDiscriminant) / denominator;
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