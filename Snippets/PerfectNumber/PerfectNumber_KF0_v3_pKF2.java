package com.thealgorithms.maths;

/**
 * Utility class for working with perfect numbers.
 *
 * <p>In number theory, a perfect number is a positive integer that is equal to
 * the sum of its positive divisors, excluding the number itself. For example,
 * 6 has divisors 1, 2, and 3 (excluding itself), and 1 + 2 + 3 = 6, so 6 is a
 * perfect number.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Perfect_number">Perfect number (Wikipedia)</a>
 */
public final class PerfectNumber {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private PerfectNumber() {}

    /**
     * Determines whether the given number is a perfect number using a
     * straightforward divisor-summing approach.
     *
     * <p>This method iterates through all integers from {@code 1} to
     * {@code number - 1} and sums those that divide {@code number} evenly.
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is a perfect number;
     *         {@code false} otherwise
     */
    public static boolean isPerfectNumber(int number) {
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
     * Determines whether the given number is a perfect number using an
     * optimized divisor-summing approach.
     *
     * <p>This method iterates only up to the square root of {@code n}. For each
     * divisor {@code d} found, it adds both {@code d} and its complementary
     * divisor {@code n / d} to the sum of divisors. The divisor {@code 1} is
     * included by default for all {@code n > 1}.
     *
     * @param n the number to test
     * @return {@code true} if {@code n} is a perfect number;
     *         {@code false} otherwise
     */
    public static boolean isPerfectNumber2(int n) {
        if (n <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a proper divisor of any integer > 1
        double root = Math.sqrt(n);

        for (int divisor = 2; divisor <= root; divisor++) {
            if (n % divisor == 0) {
                sumOfDivisors += divisor + n / divisor;
            }
        }

        // Correct for double-counting when n is a perfect square
        if (root == (int) root) {
            sumOfDivisors -= (int) root;
        }

        return sumOfDivisors == n;
    }
}