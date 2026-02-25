package com.thealgorithms.maths;

/**
 * Represents a root of a quadratic equation.
 *
 * <p>A root can be:
 * <ul>
 *     <li>Real: {@code imaginaryPart == null}</li>
 *     <li>Complex: {@code imaginaryPart != null}</li>
 * </ul>
 */
class QuadraticRoot {
    final Double realPart;
    final Double imaginaryPart;

    QuadraticRoot(double realPart, Double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    QuadraticRoot(double realPart) {
        this(realPart, null);
    }
}

/**
 * Solves quadratic equations of the form {@code a * x^2 + b * x + c = 0}.
 */
public class QuadraticEquationSolver {

    /**
     * Computes the roots of the quadratic equation {@code a * x^2 + b * x + c = 0}.
     *
     * @param a coefficient of x² (must be non-zero)
     * @param b coefficient of x
     * @param c constant term
     * @return an array of {@link QuadraticRoot}:
     *         <ul>
     *             <li>Two real roots if the discriminant is positive</li>
     *             <li>One real root if the discriminant is zero</li>
     *             <li>Two complex roots if the discriminant is negative</li>
     *         </ul>
     * @throws IllegalArgumentException if {@code a == 0}
     */
    public QuadraticRoot[] solve(double a, double b, double c) {
        if (a == 0) {
            throw new IllegalArgumentException(
                "Coefficient 'a' must be non-zero for a quadratic equation."
            );
        }

        double discriminant = b * b - 4 * a * c;
        double twoA = 2 * a;

        if (discriminant > 0) {
            return solveWithPositiveDiscriminant(b, discriminant, twoA);
        } else if (discriminant == 0) {
            return solveWithZeroDiscriminant(b, twoA);
        } else {
            return solveWithNegativeDiscriminant(b, discriminant, twoA);
        }
    }

    /**
     * Handles the case where the discriminant is positive (two distinct real roots).
     */
    private QuadraticRoot[] solveWithPositiveDiscriminant(double b, double discriminant, double twoA) {
        double sqrtDiscriminant = Math.sqrt(discriminant);
        double root1 = (-b + sqrtDiscriminant) / twoA;
        double root2 = (-b - sqrtDiscriminant) / twoA;
        return new QuadraticRoot[] {
            new QuadraticRoot(root1),
            new QuadraticRoot(root2)
        };
    }

    /**
     * Handles the case where the discriminant is zero (one repeated real root).
     */
    private QuadraticRoot[] solveWithZeroDiscriminant(double b, double twoA) {
        double root = -b / twoA;
        return new QuadraticRoot[] {
            new QuadraticRoot(root)
        };
    }

    /**
     * Handles the case where the discriminant is negative (two complex conjugate roots).
     */
    private QuadraticRoot[] solveWithNegativeDiscriminant(double b, double discriminant, double twoA) {
        double realPart = -b / twoA;
        double imaginaryPart = Math.sqrt(-discriminant) / twoA;
        return new QuadraticRoot[] {
            new QuadraticRoot(realPart, imaginaryPart),
            new QuadraticRoot(realPart, -imaginaryPart)
        };
    }
}