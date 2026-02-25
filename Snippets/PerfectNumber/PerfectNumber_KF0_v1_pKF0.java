package com.thealgorithms.maths;

/**
 * In number theory, a perfect number is a positive integer that is equal to the
 * sum of its positive divisors, excluding the number itself. For instance, 6
 * has divisors 1, 2 and 3 (excluding itself), and 1 + 2 + 3 = 6, so 6 is a
 * perfect number.
 *
 * link:https://en.wikipedia.org/wiki/Perfect_number
 */
public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Check if {@code number} is a perfect number using a simple divisor-sum approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, otherwise {@code false}
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a divisor of every number > 1

        for (int divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Check if {@code number} is a perfect number using a square-root optimized approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, otherwise {@code false}
     */
    public static boolean isPerfectNumber2(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a divisor of every number > 1
        double squareRoot = Math.sqrt(number);

        /*
         * We can get the factors after the square root by dividing the number by its
         * factors before the square root.
         * Example: Factors of 100 are 1, 2, 4, 5, 10, 20, 25, 50 and 100.
         * The square root of 100 is 10. Factors before 10 are 1, 2, 4 and 5.
         * Dividing 100 by each factor before 10 gives:
         * 100/1 = 100, 100/2 = 50, 100/4 = 25 and 100/5 = 20,
         * which are the factors of 100 after 10.
         */
        for (int divisor = 2; divisor <= squareRoot; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor + number / divisor;
            }
        }

        // If number is a perfect square, its square root was added twice; subtract it once.
        if (squareRoot == (int) squareRoot) {
            sumOfDivisors -= squareRoot;
        }

        return sumOfDivisors == number;
    }
}