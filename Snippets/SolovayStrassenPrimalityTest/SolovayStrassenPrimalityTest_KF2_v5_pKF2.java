package com.thealgorithms.maths;

import java.util.Random;

/**
 * Implementation of the Solovay–Strassen probabilistic primality test.
 *
 * <p>This class is immutable and uses a seeded {@link Random} instance for
 * reproducible results.</p>
 */
final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    /**
     * Creates a new instance with the given random seed.
     *
     * @param seed the seed for the random number generator
     * @return a new {@code SolovayStrassenPrimalityTest} instance
     */
    public static SolovayStrassenPrimalityTest getSolovayStrassenPrimalityTest(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    /**
     * Computes (base^exponent) mod mod using fast modular exponentiation.
     *
     * @param base     the base
     * @param exponent the exponent (non-negative)
     * @param mod      the modulus (positive)
     * @return (base^exponent) mod mod
     */
    private static long modularExponentiation(long base, long exponent, long mod) {
        long result = 1;
        long currentPower = base % mod;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * currentPower) % mod;
            }
            currentPower = (currentPower * currentPower) % mod;
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Computes the Jacobi symbol (a/n).
     *
     * <p>The Jacobi symbol is a generalization of the Legendre symbol. This
     * implementation assumes {@code n} is a positive odd integer.</p>
     *
     * @param a the numerator
     * @param n the denominator (must be positive and odd)
     * @return the Jacobi symbol (a/n), which is -1, 0, or 1
     */
    public int calculateJacobi(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            // Factor out powers of 2 from a and update the sign using (2/n)
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

            // If both a and n are 3 (mod 4), flip the sign
            if ((a & 3) == 3 && (n & 3) == 3) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    /**
     * Performs the Solovay–Strassen primality test.
     *
     * <p>This is a probabilistic test: if it returns {@code true}, {@code n}
     * is probably prime; if it returns {@code false}, {@code n} is definitely
     * composite.</p>
     *
     * @param n          the number to test
     * @param iterations the number of random bases to test; higher values
     *                   reduce the probability of a false positive
     * @return {@code true} if {@code n} is probably prime, {@code false} if
     *         {@code n} is composite
     */
    public boolean solovayStrassen(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long a = Math.abs(random.nextLong() % (n - 2)) + 2;

            long jacobi = (n + calculateJacobi(a, n)) % n;
            long modExp = modularExponentiation(a, (n - 1) / 2, n);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }
}