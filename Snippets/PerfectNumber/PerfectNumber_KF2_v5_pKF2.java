package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * <p>A perfect number is a positive integer that is equal to the sum of its
 * positive divisors excluding the number itself. For example, 6 is perfect
 * because 1 + 2 + 3 = 6.
 */
public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is perfect using a straightforward divisor-summing approach.
     *
     * <p>This method tests all integers from 1 up to {@code number - 1} and sums
     * those that divide {@code number} evenly.
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is perfect; {@code false} otherwise
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        for (int divisor = 1; divisor < number; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-pair approach.
     *
     * <p>This method:
     * <ul>
     *   <li>Returns {@code false} for numbers less than or equal to 1.</li>
     *   <li>Starts the sum at 1 (a proper divisor of all integers &gt; 1).</li>
     *   <li>Iterates divisors from 2 up to {@code sqrt(number)}.</li>
     *   <li>Adds both members of each divisor pair (d and number / d).</li>
     *   <li>Adjusts for perfect squares to avoid double-counting the square root.</li>
     * </ul>
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is perfect; {@code false} otherwise
     */
    public static boolean isPerfectNumber2(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1;
        double squareRoot = Math.sqrt(number);

        for (int divisor = 2; divisor <= squareRoot; divisor++) {
            if (number % divisor == 0) {
                int pairedDivisor = number / divisor;
                sumOfDivisors += divisor + pairedDivisor;
            }
        }

        // If number is a perfect square, its square root was added twice; remove one occurrence
        if (squareRoot == (int) squareRoot) {
            sumOfDivisors -= (int) squareRoot;
        }

        return sumOfDivisors == number;
    }
}