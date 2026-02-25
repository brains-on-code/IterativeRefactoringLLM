package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * A perfect number is a positive integer that is equal to the sum of its
 * positive divisors, excluding the number itself. For example, 6 is perfect
 * because 1 + 2 + 3 = 6.
 */
public final class PerfectNumberUtils {

    private PerfectNumberUtils() {
        // Prevent instantiation
    }

    /**
     * Checks if a number is perfect using a straightforward divisor-summing approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, {@code false} otherwise
     */
    public static boolean isPerfectBruteForce(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        for (int divisor = 1; divisor < number; divisor++) {
            if (isDivisor(number, divisor)) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-summing approach
     * that iterates only up to the square root of the number.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, {@code false} otherwise
     */
    public static boolean isPerfectOptimized(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a divisor of every positive integer
        int sqrt = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (!isDivisor(number, divisor)) {
                continue;
            }

            int complementDivisor = number / divisor;
            sumOfDivisors += divisor;

            if (complementDivisor != divisor) {
                sumOfDivisors += complementDivisor;
            }
        }

        return sumOfDivisors == number;
    }

    private static boolean isDivisor(int number, int divisor) {
        return number % divisor == 0;
    }
}