package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeCheck {

    private PrimeCheck() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter a number: ");
            int n = scanner.nextInt();

            if (isPrime(n)) {
                System.out.println("algo1 verify that " + n + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + n + " is not a prime number");
            }

            if (fermatPrimeChecking(n, 20)) {
                System.out.println("algo2 verify that " + n + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + n + " is not a prime number");
            }
        }
    }

    /**
     * Determines whether a number is prime using trial division.
     *
     * @param n the number to test
     * @return {@code true} if {@code n} is prime; {@code false} otherwise
     */
    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n < 2 || n % 2 == 0) {
            return false;
        }

        int limit = (int) Math.sqrt(n);
        for (int i = 3; i <= limit; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Probabilistically determines whether a number is prime using Fermat's
     * little theorem.
     *
     * @param n         the number to test
     * @param iteration the number of random bases to try; higher values reduce
     *                  the probability of a composite being reported as prime
     * @return {@code true} if {@code n} is probably prime; {@code false} if it is
     *         definitely composite
     */
    public static boolean fermatPrimeChecking(int n, int iteration) {
        if (n <= 3) {
            return n > 1;
        }

        int lowerBound = 2;
        int upperBound = n - 2;

        for (int i = 0; i < iteration; i++) {
            long a = (long) Math.floor(Math.random() * (upperBound - lowerBound + 1) + lowerBound);
            if (modPow(a, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using fast modular exponentiation.
     *
     * @param base     the base
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return {@code (base^exponent) mod modulus}
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