package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * A perfect number is a positive integer that is equal to the sum of its
 * positive divisors excluding itself. For example, 6 is perfect because
 * 1 + 2 + 3 = 6.
 */
public final class PerfectNumberUtils {

    private PerfectNumberUtils() {
        // Prevent instantiation of utility class
    }

    /**
     * Checks if a number is perfect using a straightforward divisor-summing approach.
     *
     * Iterates from 1 to n - 1 and sums all divisors of n.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number, {@code false} otherwise
     */
    public static boolean isPerfectNaive(int n) {
        if (n <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        for (int divisor = 1; divisor < n; divisor++) {
            if (n % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == n;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-summing approach.
     *
     * Iterates only up to the square root of n and adds both divisors in each
     * pair (d and n / d) when d divides n. Starts the sum at 1 (since 1 is
     * always a proper divisor of n > 1). If n is a perfect square, the square
     * root is subtracted once to avoid double-counting.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number, {@code false} otherwise
     */
    public static boolean isPerfectOptimized(int n) {
        if (n <= 1) {
            return false;
        }

        int sumOfDivisors = 1;
        double sqrt = Math.sqrt(n);
        int sqrtFloor = (int) sqrt;

        for (int divisor = 2; divisor <= sqrtFloor; divisor++) {
            if (n % divisor == 0) {
                sumOfDivisors += divisor + n / divisor;
            }
        }

        // If n is a perfect square, its square root was counted twice above
        if (sqrtFloor * sqrtFloor == n) {
            sumOfDivisors -= sqrtFloor;
        }

        return sumOfDivisors == n;
    }
}