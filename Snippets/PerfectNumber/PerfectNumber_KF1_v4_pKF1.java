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
     * @param candidateNumber the number to check
     * @return {@code true} if {@code candidateNumber} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumberNaive(int candidateNumber) {
        if (candidateNumber <= 0) {
            return false;
        }

        int sumOfProperDivisors = 0;

        for (int potentialDivisor = 1; potentialDivisor < candidateNumber; potentialDivisor++) {
            if (candidateNumber % potentialDivisor == 0) {
                sumOfProperDivisors += potentialDivisor;
            }
        }

        return sumOfProperDivisors == candidateNumber;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-summing approach
     * that iterates only up to the square root of the number.
     *
     * @param candidateNumber the number to check
     * @return {@code true} if {@code candidateNumber} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumberOptimized(int candidateNumber) {
        if (candidateNumber <= 0) {
            return false;
        }

        int sumOfProperDivisors = 1;
        double candidateSquareRoot = Math.sqrt(candidateNumber);

        for (int potentialDivisor = 2; potentialDivisor <= candidateSquareRoot; potentialDivisor++) {
            if (candidateNumber % potentialDivisor == 0) {
                int pairedDivisor = candidateNumber / potentialDivisor;
                sumOfProperDivisors += potentialDivisor + pairedDivisor;
            }
        }

        // If candidateNumber is a perfect square, we added the square root twice; subtract once
        if (candidateSquareRoot == (int) candidateSquareRoot) {
            sumOfProperDivisors -= candidateSquareRoot;
        }

        return sumOfProperDivisors == candidateNumber;
    }
}