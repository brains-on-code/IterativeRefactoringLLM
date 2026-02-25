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
     * @param coefficientA coefficient of x^2
     * @param coefficientB coefficient of x
     * @param coefficientC constant term
     * @return an array of QuadraticRoot representing the solutions
     */
    public QuadraticRoot[] solve(double coefficientA, double coefficientB, double coefficientC) {
        double discriminant = coefficientB * coefficientB - 4 * coefficientA * coefficientC;
        double denominator = 2 * coefficientA;

        if (discriminant > 0) {
            double squareRootOfDiscriminant = Math.sqrt(discriminant);

            return new QuadraticRoot[] {
                new QuadraticRoot((-coefficientB + squareRootOfDiscriminant) / denominator),
                new QuadraticRoot((-coefficientB - squareRootOfDiscriminant) / denominator)
            };
        }

        if (discriminant == 0) {
            return new QuadraticRoot[] {
                new QuadraticRoot(-coefficientB / denominator)
            };
        }

        double realPart = -coefficientB / denominator;
        double imaginaryPart = Math.sqrt(-discriminant) / denominator;

        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}