package com.thealgorithms.maths;

public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is perfect using a simple divisor-summing approach.
     * A perfect number is equal to the sum of its positive divisors excluding itself.
     *
     * @param number the number to check
     * @return true if {@code number} is perfect, false otherwise
     */
    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        // Check all numbers from 1 to number - 1 as potential divisors
        for (int divisor = 1; divisor < number; divisor++) {
            if (number % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == number;
    }

    /**
     * Checks if a number is perfect using an optimized divisor-summing approach.
     * Only iterates up to the square root of the number and adds divisor pairs.
     *
     * @param number the number to check
     * @return true if {@code number} is perfect, false otherwise
     */
    public static boolean isPerfectNumber2(int number) {
        if (number <= 0) {
            return false;
        }

        // 1 is always a proper divisor for positive integers > 1
        int sumOfDivisors = 1;
        double squareRoot = Math.sqrt(number);

        // Check divisors from 2 up to the square root
        for (int divisor = 2; divisor <= squareRoot; divisor++) {
            if (number % divisor == 0) {
                int pairedDivisor = number / divisor;
                sumOfDivisors += divisor + pairedDivisor;
            }
        }

        // If number is a perfect square, its square root was added twice; remove one instance
        if (squareRoot == (int) squareRoot) {
            sumOfDivisors -= squareRoot;
        }

        return sumOfDivisors == number;
    }
}