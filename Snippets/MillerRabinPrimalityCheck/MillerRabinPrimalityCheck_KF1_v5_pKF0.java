package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimality {

    private static final int[] DETERMINISTIC_BASES =
        {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    private static final Random RANDOM = new Random();
    private static final long MASK_24_BITS = (1L << 24) - 1;

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

        FactorizationResult factorization = factorOutPowersOfTwo(n - 1);
        long d = factorization.oddPart;
        int s = factorization.powerOfTwo;

        for (int i = 0; i < iterations; i++) {
            long base = randomBase(n);
            if (isCompositeWitness(n, base, d, s)) {
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

        FactorizationResult factorization = factorOutPowersOfTwo(n - 1);
        long d = factorization.oddPart;
        int s = factorization.powerOfTwo;

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
        long aHigh = a >> 24;
        long aLow = a & MASK_24_BITS;
        long bHigh = b >> 24;
        long bLow = b & MASK_24_BITS;

        long highProduct = ((((aHigh * bHigh << 16) % modulus) << 16) % modulus) << 16;
        long midProduct = ((aLow * bHigh + aHigh * bLow) << 24);
        long lowProduct = aLow * bLow;

        long result = highProduct + midProduct + lowProduct;
        return result % modulus;
    }

    /**
     * Represents the factorization of a number into an odd part and a power of two:
     * value = 2^powerOfTwo * oddPart, where oddPart is odd.
     */
    private static final class FactorizationResult {
        private final long oddPart;
        private final int powerOfTwo;

        private FactorizationResult(long oddPart, int powerOfTwo) {
            this.oddPart = oddPart;
            this.powerOfTwo = powerOfTwo;
        }
    }

    /**
     * Factor out powers of two from a positive number.
     * Returns d and s such that n = 2^s * d with d odd.
     */
    private static FactorizationResult factorOutPowersOfTwo(long n) {
        long oddPart = n;
        int powerOfTwo = 0;

        while ((oddPart & 1) == 0) {
            oddPart >>= 1;
            powerOfTwo++;
        }

        return new FactorizationResult(oddPart, powerOfTwo);
    }

    /**
     * Returns a random base in the range [2, n - 2].
     */
    private static long randomBase(long n) {
        return 2 + Math.floorMod(RANDOM.nextLong(), n - 3);
    }
}