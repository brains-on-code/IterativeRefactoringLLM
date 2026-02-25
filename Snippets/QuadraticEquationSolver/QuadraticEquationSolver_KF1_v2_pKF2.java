package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
 * If {@code imaginaryPart} is {@code null}, the root is real.
 */
class QuadraticRoot {
    Double realPart;
    Double imaginaryPart;

    QuadraticRoot(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    QuadraticRoot(double realPart) {
        this.realPart = realPart;
        this.imaginaryPart = null;
    }
}

/**
 * Provides methods for solving quadratic equations of the form:
 * {@code a * x^2 + b * x + c = 0}.
 */
public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation {@code a * x^2 + b * x + c = 0}.
     *
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term
     * @return an array of {@link QuadraticRoot} representing the roots:
     *         <ul>
     *             <li>Two real roots if the discriminant is positive</li>
     *             <li>One real root if the discriminant is zero</li>
     *             <li>Two complex roots if the discriminant is negative</li>
     *         </ul>
     */
    public QuadraticRoot[] solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            double root1 = (-b + sqrtDiscriminant) / (2 * a);
            double root2 = (-b - sqrtDiscriminant) / (2 * a);
            return new QuadraticRoot[] {new QuadraticRoot(root1), new QuadraticRoot(root2)};
        }

        if (discriminant == 0) {
            double root = -b / (2 * a);
            return new QuadraticRoot[] {new QuadraticRoot(root)};
        }

        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);
        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}