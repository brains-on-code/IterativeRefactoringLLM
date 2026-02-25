package com.thealgorithms.maths.Prime;

import java.util.Random;

/**
 * Miller–Rabin primality test.
 *
 * <p>Provides:
 * <ul>
 *   <li>A probabilistic test: {@link #millerRabin(long, int)}</li>
 *   <li>A deterministic test for 64-bit integers: {@link #deterministicMillerRabin(long)}</li>
 * </ul>
 *
 * <p>References:
 * <ul>
 *   <li><a href="https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test">
 *       Wikipedia: Miller–Rabin primality test</a></li>
 *   <li><a href="https://cp-algorithms.com/algebra/primality_tests.html">
 *       CP-Algorithms: Primality tests</a></li>
 * </ul>
 */
public final class MillerRabinPrimalityCheck {

    private static final Random RANDOM = new Random();

    private MillerRabinPrimalityCheck() {
        // Prevent instantiation
    }

    /**
     * Probabilistic Miller–Rabin primality test.
     *
     * @param n number to test
     * @param k number of random bases to try; error probability ≤ 4^(-k) for composite n
     * @return {@code true} if n is probably prime, {@code false} if n is definitely composite
     */
    public static boolean millerRabin(long n, int k) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        for (int i = 0; i < k; i++) {
            long a = 2 + Math.floorMod(RANDOM.nextLong(), n - 3);
            if (isComposite(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deterministic Miller–Rabin primality test for 64-bit integers.
     *
     * @param n number to test
     * @return {@code true} if n is prime, {@code false} otherwise
     */
    public static boolean deterministicMillerRabin(long n) {
        if (n < 2) {
            return false;
        }

        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        int[] bases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int a : bases) {
            if (n == a) {
                return true;
            }
            if (isComposite(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks whether n is composite for a given base a using the decomposition n - 1 = 2^s * d.
     *
     * @param n number under test
     * @param a base
     * @param d odd part of n - 1
     * @param s exponent of 2 in n - 1
     * @return {@code true} if n is composite for this base, {@code false} otherwise
     */
    private static boolean isComposite(long n, long a, long d, int s) {
        long x = powerMod(a, d, n);
        if (x == 1 || x == n - 1) {
            return false;
        }
        for (int r = 1; r < s; r++) {
            x = powerMod(x, 2, n);
            if (x == n - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using binary exponentiation.
     */
    private static long powerMod(long base, long exponent, long modulus) {
        long result = 1;
        base %= modulus;

        if (base == 0) {
            return 0;
        }

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = multiplyMod(result, base, modulus);
            }
            exponent >>= 1;
            base = multiplyMod(base, base, modulus);
        }
        return result;
    }

    /**
     * Computes (a * b) mod p avoiding overflow by splitting operands.
     */
    private static long multiplyMod(long a, long b, long p) {
        long aHi = a >> 24;
        long aLo = a & ((1L << 24) - 1);
        long bHi = b >> 24;
        long bLo = b & ((1L << 24) - 1);

        long result = ((((aHi * bHi << 16) % p) << 16) % p) << 16;
        result += ((aLo * bHi + aHi * bLo) << 24) + aLo * bLo;

        return result % p;
    }
}