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
     * Deterministic primality test using trial division up to sqrt(n).
     *
     * @param n number to test
     * @return true if n is prime, false otherwise
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
     * Probabilistic primality test using Fermat's little theorem.
     * Runs the test 'iterations' times with random bases in [2, n-2].
     *
     * @param n          number to test
     * @param iterations number of random bases to try
     * @return true if n is probably prime, false if definitely composite
     */
    public static boolean fermatPrimeChecking(int n, int iterations) {
        if (n <= 3) {
            return n > 1;
        }

        int lowerBound = 2;
        int upperBound = n - 2;

        for (int i = 0; i < iterations; i++) {
            long a = (long) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
            if (modPow(a, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using fast exponentiation.
     *
     * @param base     base
     * @param exponent exponent
     * @param modulus  modulus
     * @return (base^exponent) % modulus
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