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
        // Utility class; prevent instantiation
    }

    /**
     * Determines whether the given number is a perfect number using a
     * straightforward divisor-summing approach.
     *
     * This method checks all integers from 1 to {@code n - 1} and sums those
     * that divide {@code n} evenly.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number; {@code false} otherwise
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
     * Determines whether the given number is a perfect number using an
     * optimized divisor-summing approach.
     *
     * This method:
     * <ul>
     *   <li>Returns {@code false} for {@code n <= 1} (no perfect numbers there).</li>
     *   <li>Starts the sum at 1, since 1 is always a proper divisor of {@code n > 1}.</li>
     *   <li>Iterates divisors from 2 up to {@code floor(sqrt(n))}.</li>
     *   <li>For each divisor {@code d} that divides {@code n}, adds both
     *       {@code d} and {@code n / d} to the sum.</li>
     *   <li>If {@code n} is a perfect square, subtracts the square root once
     *       to correct for double-counting.</li>
     * </ul>
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number; {@code false} otherwise
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

        // Correct for double-counting when n is a perfect square
        if (sqrtFloor * sqrtFloor == n) {
            sumOfDivisors -= sqrtFloor;
        }

        return sumOfDivisors == n;
    }
}