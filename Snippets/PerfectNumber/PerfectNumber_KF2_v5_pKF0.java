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
        if (!isValidCandidate(number)) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a proper divisor of any number > 1

        for (int divisor = 2; divisor < number; divisor++) {
            if (isDivisor(number, divisor)) {
                sumOfDivisors += divisor;
            }
        }

        return isPerfect(number, sumOfDivisors);
    }

    /**
     * Checks if a number is perfect using an optimized divisor search up to sqrt(n).
     * Time complexity: O(sqrt(n))
     *
     * @param number the number to check
     * @return true if the number is perfect, false otherwise
     */
    public static boolean isPerfectNumber2(int number) {
        if (!isValidCandidate(number)) {
            return false;
        }

        int sumOfDivisors = 1; // 1 is a proper divisor of any number > 1
        int sqrtOfNumber = (int) Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrtOfNumber; divisor++) {
            if (!isDivisor(number, divisor)) {
                continue;
            }

            int pairedDivisor = number / divisor;
            sumOfDivisors += divisor;

            if (pairedDivisor != divisor) {
                sumOfDivisors += pairedDivisor;
            }
        }

        return isPerfect(number, sumOfDivisors);
    }

    private static boolean isValidCandidate(int number) {
        return number > 1;
    }

    private static boolean isDivisor(int number, int divisor) {
        return number % divisor == 0;
    }

    private static boolean isPerfect(int number, int sumOfDivisors) {
        return sumOfDivisors == number;
    }
}