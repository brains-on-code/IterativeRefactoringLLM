package com.thealgorithms.maths;

/**
 * Represents a complex number with real and imaginary parts.
 */
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

/**
 * Solves quadratic equations of the form ax^2 + bx + c = 0.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadratic_equation">Quadratic Equation</a>
 */
public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0.
     *
     * @param a coefficient of x^2 (must not be zero)
     * @param b coefficient of x
     * @param c constant term
     * @return array of roots as ComplexNumber objects
     *         (length 2 for distinct or complex roots, length 1 for a double root)
     * @throws IllegalArgumentException if a is zero
     */
    public ComplexNumber[] solveEquation(double a, double b, double c) {
        validateQuadraticCoefficient(a);

        double discriminant = calculateDiscriminant(a, b, c);
        double twoA = 2 * a;

        if (discriminant > 0) {
            return solveDistinctRealRoots(b, discriminant, twoA);
        } else if (discriminant == 0) {
            return solveDoubleRoot(b, twoA);
        } else {
            return solveComplexRoots(b, discriminant, twoA);
        }
    }

    private void validateQuadraticCoefficient(double a) {
        if (a == 0) {
            throw new IllegalArgumentException("Coefficient 'a' must not be zero for a quadratic equation.");
        }
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    private ComplexNumber[] solveDistinctRealRoots(double b, double discriminant, double twoA) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDiscriminant) / twoA;
        double root2 = (-b - sqrtDiscriminant) / twoA;
        return new ComplexNumber[] {
            new ComplexNumber(root1),
            new ComplexNumber(root2)
        };
    }

    private ComplexNumber[] solveDoubleRoot(double b, double twoA) {
        double root = -b / twoA;
        return new ComplexNumber[] { new ComplexNumber(root) };
    }

    private ComplexNumber[] solveComplexRoots(double b, double discriminant, double twoA) {
        double realPart = -b / twoA;
        double imaginaryPart = Math.sqrt(-discriminant) / twoA;
        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}