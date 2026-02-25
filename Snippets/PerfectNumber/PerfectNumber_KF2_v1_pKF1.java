package com.thealgorithms.maths;

public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isPerfectNumber(int number) {
        if (number <= 0) {
            return false;
        }

        int divisorSum = 0;

        for (int divisor = 1; divisor < number; ++divisor) {
            if (number % divisor == 0) {
                divisorSum += divisor;
            }
        }

        return divisorSum == number;
    }

    public static boolean isPerfectNumberOptimized(int number) {
        if (number <= 0) {
            return false;
        }

        int divisorSum = 1;
        double squareRoot = Math.sqrt(number);

        for (int divisor = 2; divisor <= squareRoot; divisor++) {
            if (number % divisor == 0) {
                divisorSum += divisor + number / divisor;
            }
        }

        if (squareRoot == (int) squareRoot) {
            divisorSum -= squareRoot;
        }

        return divisorSum == number;
    }
}