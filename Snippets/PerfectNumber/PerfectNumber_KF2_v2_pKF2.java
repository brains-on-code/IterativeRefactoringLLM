package com.thealgorithms.maths;

public final class PerfectNumber {

    private PerfectNumber() {
        // Prevent instantiation
    }

    /**
     * Determines whether a number is perfect using a straightforward approach.
     * A perfect number equals the sum of its positive divisors excluding itself.
     *
     * @param number the number to check
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
     * Determines whether a number is perfect using an optimized approach.
     * Iterates only up to the square root of the number and uses divisor pairs.
     *
     * @param number the number to check
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

        if (squareRoot == (int) squareRoot) {
            sumOfDivisors -= (int) squareRoot;
        }

        return sumOfDivisors == number;
    }
}