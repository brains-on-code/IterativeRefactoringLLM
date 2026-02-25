package com.thealgorithms.maths.Prime;

import java.util.Random;

/**
 * Primality tests using the Miller–Rabin algorithm.
 */
public final class MillerRabinPrimality {

    /**
     * Deterministic bases sufficient for testing 64‑bit integers.
     */
    private static final int[] DETERMINISTIC_BASES_64BIT =
        {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    private MillerRabinPrimality() {
        // Prevent instantiation.
    }

    /**
     * Probabilistic Miller–Rabin primality test.
     *
     * @param n          number to test for primality
     * @param iterations number of random bases to test; higher means more accuracy
     * @return {@code true} if {@code n} is probably prime, {@code false} if composite
     */
    public static boolean isProbablePrime(long n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        Decomposition decomposition = decompose(n - 1);
        long d = decomposition.d;
        int s = decomposition.s;

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long a = 2 + Math.floorMod(random.nextLong(), n - 3);
            if (isCompositeWitness(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deterministic Miller–Rabin primality test for 64-bit integers.
     *
     * @param n number to test for primality
     * @return {@code true} if {@code n} is prime, {@code false} otherwise
     */
    public static boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }

        Decomposition decomposition = decompose(n - 1);
        long d = decomposition.d;
        int s = decomposition.s;

        for (int a : DETERMINISTIC_BASES_64BIT) {
            if (n == a) {
                return true;
            }
            if (isCompositeWitness(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Miller–Rabin witness test.
     *
     * @param n number being tested
     * @param a base
     * @param d odd part of n − 1 (n − 1 = 2^s * d)
     * @param s exponent of 2 in n − 1
     * @return {@code true} if {@code a} is a witness that {@code n} is composite,
     *         {@code false} otherwise
     */
    private static boolean isCompositeWitness(long n, long a, long d, int s) {
        long x = modularExponentiation(a, d, n);

        if (x == 1 || x == n - 1) {
            return false;
        }

        for (int r = 1; r < s; r++) {
            x = modularExponentiation(x, 2, n);
            if (x == n - 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Computes (base^exponent) mod modulus using fast exponentiation.
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;

        base %= modulus;
        if (base == 0) {
            return 0;
        }

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = modularMultiplication(result, base, modulus);
            }
            exponent >>= 1;
            base = modularMultiplication(base, base, modulus);
        }
        return result;
    }

    /**
     * Computes (a * b) mod m avoiding overflow by splitting operands.
     */
    private static long modularMultiplication(long a, long b, long m) {
        long aHigh = a >> 24;
        long aLow = a & ((1L << 24) - 1);
        long bHigh = b >> 24;
        long bLow = b & ((1L << 24) - 1);

        long highProduct = ((((aHigh * bHigh << 16) % m) << 16) % m) << 16;
        long midProduct = ((aLow * bHigh + aHigh * bLow) << 24);
        long lowProduct = aLow * bLow;

        long result = highProduct + midProduct + lowProduct;
        return result % m;
    }

    /**
     * Represents the decomposition of n − 1 into 2^s * d with d odd.
     */
    private static final class Decomposition {
        final long d;
        final int s;

        Decomposition(long d, int s) {
            this.d = d;
            this.s = s;
        }
    }

    /**
     * Decomposes {@code value} into 2^s * d with d odd.
     */
    private static Decomposition decompose(long value) {
        long d = value;
        int s = 0;

        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        return new Decomposition(d, s);
    }
}