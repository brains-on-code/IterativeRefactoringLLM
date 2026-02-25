package com.thealgorithms.maths;

/**
 * Utility class for checking perfect numbers.
 *
 * A perfect number is a positive integer that is equal to the sum of its proper
 * positive divisors, excluding the number itself. For example, 6 is perfect
 * because 1 + 2 + 3 = 6.
 */
public final class PerfectNumberUtils {

    private PerfectNumberUtils() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is perfect using a straightforward divisor-summing approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumberNaive(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        for (int divisor = 1; divisor < number; ++divisor) {
            if (number % divisor == 0) {
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
     * @return {@code true} if {@code number} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumberOptimized(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 1;
        double squareRoot = Math.sqrt(number);

        for (int divisor = 2; divisor <= squareRoot; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor + number / divisor;
            }
        }

        // If number is a perfect square, we added the square root twice; subtract once
        if (squareRoot == (int) squareRoot) {
            sumOfDivisors -= squareRoot;
        }

        return sumOfDivisors == number;
    }
}