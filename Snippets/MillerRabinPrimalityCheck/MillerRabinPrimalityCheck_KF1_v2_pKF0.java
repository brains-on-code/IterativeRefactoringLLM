package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimality {

    private static final int[] DETERMINISTIC_BASES =
        {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    private MillerRabinPrimality() {
        // Utility class; prevent instantiation
    }

    /**
     * Probabilistic Miller–Rabin primality test.
     *
     * @param n          number to test for primality
     * @param iterations number of random bases to test
     * @return {@code true} if n is probably prime, {@code false} if n is composite
     */
    public static boolean isProbablePrime(long n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        long d = n - 1;
        int s = 0;

        // Write n − 1 as 2^s * d with d odd
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long a = 2 + Math.floorMod(random.nextLong(), n - 3); // random base in [2, n-2]
            if (isCompositeWitness(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Deterministic Miller–Rabin primality test for 64-bit integers using
     * a fixed set of bases.
     *
     * @param n number to test for primality
     * @return {@code true} if n is prime, {@code false} if n is composite
     */
    public static boolean isPrime(long n) {
        if (n < 2) {
            return false;
        }

        long d = n - 1;
        int s = 0;

        // Write n − 1 as 2^s * d with d odd
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        for (int base : DETERMINISTIC_BASES) {
            if (n == base) {
                return true;
            }
            if (isCompositeWitness(n, base, d, s)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Miller–Rabin witness test.
     *
     * @param n number under test
     * @param a base
     * @param d odd part of n − 1 (n − 1 = 2^s * d)
     * @param s exponent of 2 in n − 1
     * @return {@code true} if a is a witness that n is composite, otherwise {@code false}
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
                result = modularMultiply(result, base, modulus);
            }
            exponent >>= 1;
            base = modularMultiply(base, base, modulus);
        }
        return result;
    }

    /**
     * Computes (a * b) mod modulus avoiding overflow using 24-bit splitting.
     */
    private static long modularMultiply(long a, long b, long modulus) {
        long mask24 = (1L << 24) - 1;

        long aHigh = a >> 24;
        long aLow = a & mask24;
        long bHigh = b >> 24;
        long bLow = b & mask24;

        long highProduct = ((((aHigh * bHigh << 16) % modulus) << 16) % modulus) << 16;
        long midProduct = ((aLow * bHigh + aHigh * bLow) << 24);
        long lowProduct = aLow * bLow;

        long result = highProduct + midProduct + lowProduct;
        return result % modulus;
    }
}