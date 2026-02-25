package com.thealgorithms.maths;

/**
 * Represents a complex number with real and imaginary parts.
 */
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

/**
 * Solves quadratic equations of the form ax^2 + bx + c = 0.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Quadratic_equation">Quadratic Equation</a>
 */
public class QuadraticEquationSolver {

    /**
     * Computes the roots of the quadratic equation ax^2 + bx + c = 0.
     *
     * @param a coefficient of x^2 (must not be 0)
     * @param b coefficient of x
     * @param c constant term
     * @return an array of roots as {@link ComplexNumber}:
     *         <ul>
     *             <li>Two elements if there are two distinct roots</li>
     *             <li>One element if there is a repeated root</li>
     *             <li>Empty array if no roots can be computed (e.g., a == 0)</li>
     *         </ul>
     */
    public ComplexNumber[] solveEquation(double a, double b, double c) {
        if (a == 0) {
            return new ComplexNumber[0];
        }

        double discriminant = calculateDiscriminant(a, b, c);

        if (discriminant > 0) {
            return solveWithTwoRealRoots(a, b, discriminant);
        }

        if (discriminant == 0) {
            return solveWithSingleRealRoot(a, b);
        }

        return solveWithComplexRoots(a, b, discriminant);
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    private ComplexNumber[] solveWithTwoRealRoots(double a, double b, double discriminant) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double denominator = 2 * a;

        double root1 = (-b + sqrtDiscriminant) / denominator;
        double root2 = (-b - sqrtDiscriminant) / denominator;

        return new ComplexNumber[] {
            new ComplexNumber(root1),
            new ComplexNumber(root2)
        };
    }

    private ComplexNumber[] solveWithSingleRealRoot(double a, double b) {
        double root = -b / (2 * a);
        return new ComplexNumber[] { new ComplexNumber(root) };
    }

    private ComplexNumber[] solveWithComplexRoots(double a, double b, double discriminant) {
        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);

        return new ComplexNumber[] {
            new ComplexNumber(realPart, imaginaryPart),
            new ComplexNumber(realPart, -imaginaryPart)
        };
    }
}