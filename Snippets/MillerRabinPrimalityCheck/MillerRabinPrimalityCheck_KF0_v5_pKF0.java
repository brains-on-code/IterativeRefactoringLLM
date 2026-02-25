package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private static final Random RANDOM = new Random();
    private static final int[] DETERMINISTIC_BASES =
        {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
    private static final long MASK_24_BITS = (1L << 24) - 1;

    private MillerRabinPrimalityCheck() {
        // Utility class; prevent instantiation
    }

    /**
     * Probabilistic Miller–Rabin primality test.
     *
     * @param n number to test
     * @param iterations number of iterations (k)
     * @return true if n is probably prime, false if composite
     */
    public static boolean millerRabin(long n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        long d = n - 1;
        int s = 0;

        while (isEven(d)) {
            d >>= 1;
            s++;
        }

        for (int i = 0; i < iterations; i++) {
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
     * @return true if n is prime, false otherwise
     */
    public static boolean deterministicMillerRabin(long n) {
        if (n < 2) {
            return false;
        }

        long d = n - 1;
        int s = 0;

        while (isEven(d)) {
            d >>= 1;
            s++;
        }

        for (int base : DETERMINISTIC_BASES) {
            if (n == base) {
                return true;
            }
            if (n % base == 0) {
                return false;
            }
            if (isComposite(n, base, d, s)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Check if n is composite for a given base a.
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
     * Compute (base^exponent) mod modulus using binary exponentiation.
     */
    private static long powerMod(long base, long exponent, long modulus) {
        long result = 1;
        base %= modulus;

        if (base == 0) {
            return 0;
        }

        while (exponent > 0) {
            if (isOdd(exponent)) {
                result = multiplyMod(result, base, modulus);
            }
            exponent >>= 1;
            base = multiplyMod(base, base, modulus);
        }

        return result;
    }

    /**
     * Compute (a * b) mod modulus without overflow using 24-bit splitting.
     */
    private static long multiplyMod(long a, long b, long modulus) {
        long aHigh = a >> 24;
        long aLow = a & MASK_24_BITS;
        long bHigh = b >> 24;
        long bLow = b & MASK_24_BITS;

        long high = (((aHigh * bHigh << 16) % modulus) << 16) % modulus;
        long mid = (aLow * bHigh + aHigh * bLow) << 24;
        long low = aLow * bLow;

        return (high + mid + low) % modulus;
    }

    private static boolean isEven(long value) {
        return (value & 1) == 0;
    }

    private static boolean isOdd(long value) {
        return (value & 1) == 1;
    }
}