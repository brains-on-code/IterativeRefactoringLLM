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

    private PerfectNumber() {
        // Prevent instantiation.
    }

    /**
     * Checks whether the given number is a perfect number using a simple
     * divisor-summing approach.
     *
     * <p>This method iterates through all integers from 1 to {@code number - 1}
     * and sums those that divide {@code number} evenly.
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int sum = 0;

        for (int i = 1; i < number; ++i) {
            if (number % i == 0) {
                sum += i;
            }
        }

        return sum == number;
    }

    /**
     * Checks whether the given number is a perfect number using an optimized
     * divisor-summing approach.
     *
     * <p>This method iterates only up to the square root of {@code n}. For each
     * divisor {@code d} found, it adds both {@code d} and its complementary
     * divisor {@code n / d} to the sum of divisors.
     *
     * @param n the number to test
     * @return {@code true} if {@code n} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumber2(int n) {
        if (n <= 0) {
            return false;
        }

        // 1 is a proper divisor of any positive integer greater than 1.
        int sum = 1;
        double root = Math.sqrt(n);

        for (int i = 2; i <= root; i++) {
            if (n % i == 0) {
                sum += i + n / i;
            }
        }

        // If n is a perfect square, its square root was added twice above.
        if (root == (int) root) {
            sum -= root;
        }

        return sum == n;
    }
}