package com.thealgorithms.maths;

/**
 * Represents a (possibly complex) root of a quadratic equation.
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

        if (discriminant > 0) {
            double sqrtDiscriminant = Math.sqrt(discriminant);
            return new QuadraticRoot[] {
                new QuadraticRoot((-b + sqrtDiscriminant) / (2 * a)),
                new QuadraticRoot((-b - sqrtDiscriminant) / (2 * a))
            };
        }

        if (discriminant == 0) {
            return new QuadraticRoot[] {
                new QuadraticRoot(-b / (2 * a))
            };
        }

        double realPart = -b / (2 * a);
        double imaginaryPart = Math.sqrt(-discriminant) / (2 * a);

        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}