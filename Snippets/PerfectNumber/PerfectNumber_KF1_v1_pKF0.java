package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * A perfect number is a positive integer that is equal to the sum of its
 * positive divisors, excluding the number itself. For example, 6 is perfect
 * because 1 + 2 + 3 = 6.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if a number is perfect using a straightforward divisor-summing
     * approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, {@code false} otherwise
     */
    public static boolean method1(int number) {
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
     * Checks if a number is perfect using an optimized divisor-summing
     * approach that iterates only up to the square root of the number.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, {@code false} otherwise
     */
    public static boolean method2(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a divisor of every positive integer
        double sqrt = Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor + number / divisor;
            }
        }

        // If number is a perfect square, we've added sqrt twice; subtract one occurrence
        if (sqrt == (int) sqrt) {
            sumOfDivisors -= sqrt;
        }

        return sumOfDivisors == number;
    }
}