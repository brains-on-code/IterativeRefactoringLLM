package com.thealgorithms.maths;

public final class PerfectNumber {

    private PerfectNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isPerfectNumber(int candidateNumber) {
        if (candidateNumber <= 0) {
            return false;
        }

        int sumOfDivisors = 0;

        for (int divisor = 1; divisor < candidateNumber; divisor++) {
            if (candidateNumber % divisor == 0) {
                sumOfDivisors += divisor;
            }
        }

        return sumOfDivisors == candidateNumber;
    }

    public static boolean isPerfectNumberOptimized(int candidateNumber) {
        if (candidateNumber <= 0) {
            return false;
        }

        int sumOfDivisors = 1;
        double squareRootOfNumber = Math.sqrt(candidateNumber);

        for (int divisor = 2; divisor <= squareRootOfNumber; divisor++) {
            if (candidateNumber % divisor == 0) {
                int pairedDivisor = candidateNumber / divisor;
                sumOfDivisors += divisor + pairedDivisor;
            }
        }

        if (squareRootOfNumber == (int) squareRootOfNumber) {
            sumOfDivisors -= squareRootOfNumber;
        }

        return sumOfDivisors == candidateNumber;
    }
}