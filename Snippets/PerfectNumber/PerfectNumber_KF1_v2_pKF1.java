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

        int sumOfProperDivisors = 0;

        for (int candidateDivisor = 1; candidateDivisor < number; candidateDivisor++) {
            if (number % candidateDivisor == 0) {
                sumOfProperDivisors += candidateDivisor;
            }
        }

        return sumOfProperDivisors == number;
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

        int sumOfProperDivisors = 1;
        double squareRootOfNumber = Math.sqrt(number);

        for (int candidateDivisor = 2; candidateDivisor <= squareRootOfNumber; candidateDivisor++) {
            if (number % candidateDivisor == 0) {
                int pairedDivisor = number / candidateDivisor;
                sumOfProperDivisors += candidateDivisor + pairedDivisor;
            }
        }

        // If number is a perfect square, we added the square root twice; subtract once
        if (squareRootOfNumber == (int) squareRootOfNumber) {
            sumOfProperDivisors -= squareRootOfNumber;
        }

        return sumOfProperDivisors == number;
    }
}