package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
 */
class QuadraticRoot {
    Double real;
    Double imaginary;

    QuadraticRoot(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    QuadraticRoot(double real) {
        this.real = real;
        this.imaginary = null;
    }
}

/**
 * Solves quadratic equations of the form ax^2 + bx + c = 0.
 */
public class QuadraticEquationSolver {

    /**
     * Solves the quadratic equation ax^2 + bx + c = 0.
     *
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term
     * @return an array of QuadraticRoot representing the solutions
     */
    public QuadraticRoot[] solve(double a, double b, double c) {
        double discriminant = b * b - 4 * a * c;
        double twoA = 2 * a;

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);

            return new QuadraticRoot[] {
                new QuadraticRoot((-b + sqrtDiscriminant) / twoA),
                new QuadraticRoot((-b - sqrtDiscriminant) / twoA)
            };
        }

        if (discriminant == 0) {
            return new QuadraticRoot[] {
                new QuadraticRoot(-b / twoA)
            };
        }

        double realPart = -b / twoA;
        double imaginaryPart = Math.sqrt(-discriminant) / twoA;

        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}