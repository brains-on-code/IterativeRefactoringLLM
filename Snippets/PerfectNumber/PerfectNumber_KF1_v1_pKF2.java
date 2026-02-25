package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * A perfect number is a positive integer that is equal to the sum of its
 * positive divisors excluding itself. For example, 6 is perfect because
 * 1 + 2 + 3 = 6.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Checks if a number is perfect using a simple divisor-summing approach.
     *
     * This method iterates through all integers from 1 to n - 1 and sums
     * those that divide n evenly. It then compares the sum to n.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number, {@code false} otherwise
     */
    public static boolean method1(int n) {
        if (n <= 0) {
            return false;
        }

        int sum = 0;

        for (int divisor = 1; divisor < n; ++divisor) {
            if (n % divisor == 0) {
                sum += divisor;
            }
        }

        return sum == n;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-summing approach.
     *
     * This method iterates only up to the square root of n and adds both
     * divisors in each pair (d and n / d) when d divides n. It starts the sum
     * at 1 (since 1 is always a proper divisor of n > 1). If n is a perfect
     * square, the square root is subtracted once to avoid double-counting.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a perfect number, {@code false} otherwise
     */
    public static boolean method2(int n) {
        if (n <= 0) {
            return false;
        }

        int sum = 1;
        double sqrt = Math.sqrt(n);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (n % divisor == 0) {
                sum += divisor + n / divisor;
            }
        }

        // If n is a perfect square, subtract the square root once
        // because it was added twice in the loop (as divisor and n / divisor).
        if (sqrt == (int) sqrt) {
            sum -= sqrt;
        }

        return sum == n;
    }
}