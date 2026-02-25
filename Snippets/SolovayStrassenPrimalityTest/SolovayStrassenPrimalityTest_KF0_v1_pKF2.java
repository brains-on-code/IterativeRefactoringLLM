package com.thealgorithms.maths;

import java.util.Random;

/**
 * Implements the Solovay–Strassen probabilistic primality test.
 *
 * <p>For more information, see
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
     * Computes {@code (base^exponent) mod mod} using exponentiation by squaring.
     */
    private static long calculateModularExponentiation(long base, long exponent, long mod) {
        long result = 1;
        long currentBase = base % mod;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * currentBase) % mod;
            }
            currentBase = (currentBase * currentBase) % mod;
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Computes the Jacobi symbol (a/n).
     *
     * @param a   numerator
     * @param num odd positive denominator
     * @return -1, 0, or 1
     */
    public int calculateJacobi(long a, long num) {
        if (num <= 0 || (num & 1) == 0) {
            return 0;
        }

        a %= num;
        int jacobi = 1;

        while (a != 0) {
            while ((a & 1) == 0) {
                a >>= 1;
                long nMod8 = num & 7;
                if (nMod8 == 3 || nMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long temp = a;
            a = num;
            num = temp;

            if ((a & 3) == 3 && (num & 3) == 3) {
                jacobi = -jacobi;
            }

            a %= num;
        }

        return num == 1 ? jacobi : 0;
    }

    /**
     * Runs the Solovay–Strassen primality test.
     *
     * @param num        number to test
     * @param iterations number of iterations to perform
     * @return {@code true} if {@code num} is probably prime, {@code false} if composite
     */
    public boolean solovayStrassen(long num, int iterations) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long r = Math.abs(random.nextLong() % (num - 1)) + 2;
            long a = r % (num - 1) + 1;

            long jacobi = (num + calculateJacobi(a, num)) % num;
            long mod = calculateModularExponentiation(a, (num - 1) / 2, num);

            if (jacobi == 0 || mod != jacobi) {
                return false;
            }
        }

        return true;
    }
}