package com.thealgorithms.maths;

import java.util.Random;

/**
 * Utility class providing number-theoretic operations, including
 * modular exponentiation, Jacobi symbol computation, and a
 * probabilistic primality test (Solovay–Strassen).
 */
final class Class1 {

    private final Random random;

    private Class1(int seed) {
        this.random = new Random(seed);
    }

    public static Class1 createWithSeed(int seed) {
        return new Class1(seed);
    }

    /**
     * Computes (base^exponent) mod modulus using fast modular exponentiation.
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

        return result % modulus;
    }

    /**
     * Computes the Jacobi symbol (a/n).
     * Returns:
     *  1  if a is a quadratic residue modulo n and gcd(a, n) = 1
     * -1  if a is a quadratic non-residue modulo n and gcd(a, n) = 1
     *  0  if gcd(a, n) > 1
     */
    public int jacobiSymbol(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int result = 1;

        while (a != 0) {
            result = adjustForPowersOfTwo(a, n, result);
            a = removePowersOfTwo(a);

            long temp = a;
            a = n;
            n = temp;

            if (bothCongruentToThreeModuloFour(a, n)) {
                result = -result;
            }

            a %= n;
        }

        return (n == 1) ? result : 0;
    }

    private int adjustForPowersOfTwo(long a, long n, int result) {
        while ((a & 1) == 0) {
            a >>= 1;
            long nMod8 = n & 7;
            if (nMod8 == 3 || nMod8 == 5) {
                result = -result;
            }
        }
        return result;
    }

    private long removePowersOfTwo(long a) {
        while ((a & 1) == 0) {
            a >>= 1;
        }
        return a;
    }

    private boolean bothCongruentToThreeModuloFour(long a, long n) {
        return (a & 3) == 3 && (n & 3) == 3;
    }

    /**
     * Probabilistic primality test using the Solovay–Strassen algorithm.
     *
     * @param n          number to test for primality
     * @param iterations number of iterations (higher => lower error probability)
     * @return true if n is probably prime, false if composite
     */
    public boolean isProbablePrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long base = getRandomBase(n);
            long jacobi = (n + jacobiSymbol(base, n)) % n;
            long modExp = modularExponentiation(base, (n - 1) / 2, n);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns a random base in the range [2, n - 1].
     */
    private long getRandomBase(long n) {
        long bound = n - 2;
        long randomValue = Math.abs(random.nextLong()) % bound;
        return randomValue + 2;
    }
}