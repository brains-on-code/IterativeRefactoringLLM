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
     * Check if {@code number} is a perfect number or not using a simple divisor-sum approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, otherwise {@code false}
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        // Sum all positive divisors less than the number itself
        for (int candidateDivisor = 1; candidateDivisor < number; candidateDivisor++) {
            if (number % candidateDivisor == 0) {
                sumOfDivisors += candidateDivisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Check if {@code number} is a perfect number or not using a square-root optimized approach.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a perfect number, otherwise {@code false}
     */
    public static boolean isPerfectNumberOptimized(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 1;
        double squareRootOfNumber = Math.sqrt(number);

        /*
         * We can get the factors after the square root by dividing the number by its
         * factors before the square root.
         * Example: Factors of 100 are 1, 2, 4, 5, 10, 20, 25, 50 and 100.
         * The square root of 100 is 10. So factors before 10 are 1, 2, 4 and 5.
         * Now by dividing 100 by each factor before 10 we get:
         * 100/1 = 100, 100/2 = 50, 100/4 = 25 and 100/5 = 20
         * So we get 100, 50, 25 and 20 which are factors of 100 after 10.
         */
        for (int candidateDivisor = 2; candidateDivisor <= squareRootOfNumber; candidateDivisor++) {
            if (number % candidateDivisor == 0) {
                sumOfDivisors += candidateDivisor + number / candidateDivisor;
            }
        }

        // If number is a perfect square then its square root was added twice in the loop above,
        // so subtract the square root once from the sum.
        if (squareRootOfNumber == (int) squareRootOfNumber) {
            sumOfDivisors -= squareRootOfNumber;
        }

        return sumOfDivisors == number;
    }
}