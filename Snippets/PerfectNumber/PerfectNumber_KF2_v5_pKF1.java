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

        for (int divisor = 1; divisor < number; divisor++) {
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
        double sqrt = Math.sqrt(number);

        for (int divisor = 2; divisor <= sqrt; divisor++) {
            if (number % divisor == 0) {
                int complementaryDivisor = number / divisor;
                divisorSum += divisor + complementaryDivisor;
            }
        }

        if (sqrt == (int) sqrt) {
            divisorSum -= sqrt;
        }

        return divisorSum == number;
    }
}