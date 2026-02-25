package com.thealgorithms.maths;

public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is perfect by summing all proper divisors.
     * Time complexity: O(n)
     *
     * @param number the number to check
     * @return true if the number is perfect, false otherwise
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a proper divisor of any number > 1

        for (int divisor = 2; divisor < number; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Checks if a number is perfect using an optimized divisor search up to sqrt(n).
     * Time complexity: O(sqrt(n))
     *
     * @param number the number to check
     * @return true if the number is perfect, false otherwise
     */
    public static boolean isPerfectNumber2(int number) {
        if (number <= 1) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a proper divisor of any number > 1
        int sqrtOfNumber = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrtOfNumber; divisor++) {
            if (number % divisor != 0) {
                continue;
            }

            int pairedDivisor = number / divisor;
            sumOfDivisors += divisor;

            if (pairedDivisor != divisor) {
                sumOfDivisors += pairedDivisor;
            }
        }

        return sumOfDivisors == number;
    }
}