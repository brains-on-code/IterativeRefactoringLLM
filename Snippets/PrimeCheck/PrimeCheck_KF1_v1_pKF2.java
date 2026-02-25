package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeChecker {

    private PrimeChecker() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter a number: ");
            int number = scanner.nextInt();

            // Deterministic primality test using trial division
            if (isPrimeDeterministic(number)) {
                System.out.println("algo1 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + number + " is not a prime number");
            }

            // Probabilistic primality test using Fermat's little theorem
            if (isPrimeProbabilistic(number, 20)) {
                System.out.println("algo2 verify that " + number + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + number + " is not a prime number");
            }
        }
    }

    /**
     * Deterministic primality test using trial division up to sqrt(n).
     *
     * @param n number to test
     * @return true if n is prime, false otherwise
     */
    public static boolean isPrimeDeterministic(int n) {
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
     * Probabilistic primality test based on Fermat's little theorem.
     * For a prime n and random a in [2, n-2], a^(n-1) ≡ 1 (mod n).
     *
     * @param n         number to test
     * @param iterations number of random bases to try
     * @return true if n is probably prime, false if composite
     */
    public static boolean isPrimeProbabilistic(int n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        int minBase = 2;
        int maxBase = n - 2;

        for (int i = 0; i < iterations; i++) {
            long base = (long) Math.floor(Math.random() * (maxBase - minBase + 1) + minBase);
            if (modularExponentiation(base, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using repeated multiplication.
     * Note: This is a simple implementation and not optimized (e.g., no fast exponentiation).
     *
     * @param base     base value
     * @param exponent exponent value
     * @param modulus  modulus value
     * @return (base^exponent) mod modulus
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
            result %= modulus;
        }
        return result % modulus;
    }
}