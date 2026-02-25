package com.thealgorithms.maths.Prime;

import java.util.Scanner;

public final class PrimeCheck {

    private static final int DEFAULT_FERMAT_ITERATIONS = 20;

    private PrimeCheck() {}

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter a number: ");
            int n = scanner.nextInt();

            if (isPrime(n)) {
                System.out.println("algo1 verify that " + n + " is a prime number");
            } else {
                System.out.println("algo1 verify that " + n + " is not a prime number");
            }

            if (isProbablyPrimeFermat(n, DEFAULT_FERMAT_ITERATIONS)) {
                System.out.println("algo2 verify that " + n + " is a prime number");
            } else {
                System.out.println("algo2 verify that " + n + " is not a prime number");
            }
        }
    }

    /**
     * Checks if {@code n} is a prime number using trial division up to sqrt(n).
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
     * Probabilistically checks if {@code n} is prime using Fermat's little theorem.
     * Random bases are chosen uniformly from the interval [2, n - 2].
     *
     * @param n          the number to test
     * @param iterations the number of random bases to test
     * @return {@code true} if {@code n} is probably prime, {@code false} if it is definitely composite
     */
    public static boolean isProbablyPrimeFermat(int n, int iterations) {
        if (n <= 3) {
            return n > 1;
        }

        int lowerBound = 2;
        int upperBound = n - 2;

        for (int i = 0; i < iterations; i++) {
            long base = (long) (Math.random() * (upperBound - lowerBound + 1)) + lowerBound;
            if (modPow(base, n - 1, n) != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes {@code (base^exponent) % modulus} using fast modular exponentiation.
     *
     * @param base     the base
     * @param exponent the exponent
     * @param modulus  the modulus
     * @return {@code (base^exponent) % modulus}
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