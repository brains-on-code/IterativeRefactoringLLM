package com.thealgorithms.maths;

import java.util.Random;

/**
 * Solovay–Strassen probabilistic primality test.
 *
 * <p>Reference:
 * <a href="https://en.wikipedia.org/wiki/Solovay%E2%80%93Strassen_primality_test">
 * Solovay–Strassen primality test</a>.
 */
final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    /**
     * Creates a new {@code SolovayStrassenPrimalityTest} instance.
     *
     * @param seed seed for the underlying random number generator
     * @return a new {@code SolovayStrassenPrimalityTest} instance
     */
    public static SolovayStrassenPrimalityTest getSolovayStrassenPrimalityTest(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    /**
     * Computes {@code (base^exponent) mod modulus} using exponentiation by squaring.
     *
     * @param base     the base
     * @param exponent the exponent (non‑negative)
     * @param modulus  the modulus (positive)
     * @return {@code (base^exponent) mod modulus}
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base % modulus;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Computes the Jacobi symbol {@code (a/n)}.
     *
     * @param a numerator
     * @param n odd positive denominator
     * @return {@code -1}, {@code 0}, or {@code 1}
     */
    public int calculateJacobi(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            // Remove factors of 2 from a and adjust the sign accordingly
            while ((a & 1) == 0) {
                a >>= 1;
                long nMod8 = n & 7;
                if (nMod8 == 3 || nMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            // Apply quadratic reciprocity by swapping a and n
            long temp = a;
            a = n;
            n = temp;

            if ((a & 3) == 3 && (n & 3) == 3) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return n == 1 ? jacobi : 0;
    }

    /**
     * Performs the Solovay–Strassen primality test.
     *
     * @param n          number to test
     * @param iterations number of iterations to perform
     * @return {@code true} if {@code n} is probably prime, {@code false} if composite
     */
    public boolean solovayStrassen(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long a = Math.abs(random.nextLong() % (n - 1)) + 1;

            long jacobi = (n + calculateJacobi(a, n)) % n;
            long mod = modularExponentiation(a, (n - 1) / 2, n);

            if (jacobi == 0 || mod != jacobi) {
                return false;
            }
        }

        return true;
    }
}