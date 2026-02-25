package com.thealgorithms.maths;

/**
 * In number theory, a perfect number is a positive integer that is equal to the
 * sum of its positive divisors, excluding the number itself. For instance, 6
 * has divisors 1, 2 and 3 (excluding itself), and 1 + 2 + 3 = 6, so 6 is a
 * perfect number.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Perfect_number">Perfect number (Wikipedia)</a>
 */
public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation.
    }

    /**
     * Checks whether the given number is a perfect number using a simple
     * divisor-summing approach.
     *
     * @param number the number to test
     * @return {@code true} if {@code number} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int sum = 0;

        // Sum all positive divisors of number, excluding the number itself.
        for (int i = 1; i < number; ++i) {
            if (number % i == 0) {
                sum += i;
            }
        }

        return sum == number;
    }

    /**
     * Checks whether the given number is a perfect number using a more efficient
     * divisor-summing approach that iterates only up to the square root.
     *
     * @param n the number to test
     * @return {@code true} if {@code n} is a perfect number; {@code false} otherwise
     */
    public static boolean isPerfectNumber2(int n) {
        if (n <= 0) {
            return false;
        }

        // 1 is always a proper divisor of any positive integer greater than 1.
        int sum = 1;
        double root = Math.sqrt(n);

        /*
         * For any divisor d <= sqrt(n), there is a complementary divisor n / d >= sqrt(n).
         * By iterating only up to sqrt(n), we can add both divisors at once.
         *
         * Example (n = 100):
         *   Divisors: 1, 2, 4, 5, 10, 20, 25, 50, 100
         *   sqrt(100) = 10
         *   Divisors <= 10: 1, 2, 4, 5, 10
         *   For each d in {1, 2, 4, 5, 10}, n / d gives the paired divisor:
         *     100 / 1 = 100
         *     100 / 2 = 50
         *     100 / 4 = 25
         *     100 / 5 = 20
         *     100 / 10 = 10
         */
        for (int i = 2; i <= root; i++) {
            if (n % i == 0) {
                sum += i + n / i;
            }
        }

        // If n is a perfect square, its square root was added twice in the loop above.
        // Subtract one occurrence of the root to correct the sum.
        if (root == (int) root) {
            sum -= root;
        }

        return sum == n;
    }
}