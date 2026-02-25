package com.thealgorithms.maths.Prime;

import java.util.Random;
import java.util.Scanner;

public final class PrimeCheck {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;
    private static final Random RANDOM = new Random();

    private PrimeCheck() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int number = scanner.nextInt();

            printDeterministicCheckResult(number);
            printFermatCheckResult(number, DEFAULT_FERMAT_ITERATIONS);
        }
    }

    private static void printDeterministicCheckResult(int number) {
        boolean isPrime = isPrime(number);
        String messagePrefix = "algo1 verify that " + number + " is ";
        String message = messagePrefix + (isPrime ? "" : "not ") + "a prime number";
        System.out.println(message);
    }

    private static void printFermatCheckResult(int number, int iterations) {
        boolean isProbablyPrime = isProbablyPrimeFermat(number, iterations);
        String messagePrefix = "algo2 verify that " + number + " is ";
        String message = messagePrefix + (isProbablyPrime ? "" : "not ") + "a prime number";
        System.out.println(message);
    }

    /**
     * Checks if a number is prime using a deterministic algorithm.
     *
     * @param n the number to test
     * @return {@code true} if {@code n} is prime, {@code false} otherwise
     */
    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }

        int limit = (int) Math.sqrt(n);
        for (int divisor = 3; divisor <= limit; divisor += 2) {
            if (n % divisor == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistic primality test using Fermat's little theorem.
     *
     * @param n          the number to test
     * @param iterations number of random bases to try
     * @return {@code true} if {@code n} is probably prime, {@code false} if composite
     */
    public static boolean isProbablyPrimeFermat(int n, int iterations) {
        if (n <= 3) {
            return n > 1;
        }

        int lowerBound = 2;
        int upperBound = n - 2;

        for (int i = 0; i < iterations; i++) {
            long base = getRandomLongInRange(lowerBound, upperBound);
            if (modPow(base, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    private static long getRandomLongInRange(int minInclusive, int maxInclusive) {
        int bound = maxInclusive - minInclusive + 1;
        return minInclusive + (long) RANDOM.nextInt(bound);
    }

    /**
     * Computes (base^exponent) mod modulus using fast exponentiation.
     *
     * @param base     the base
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return (base^exponent) mod modulus
     */
    private static long modPow(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base % modulus;
        long currentExponent = exponent;

        while (currentExponent > 0) {
            if ((currentExponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            currentExponent >>= 1;
        }

        return result;
    }
}