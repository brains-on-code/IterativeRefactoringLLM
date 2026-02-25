package com.thealgorithms.maths;

import java.util.Random;

/**
 * This class implements the Solovay-Strassen primality test,
 * which is a probabilistic algorithm to determine whether a number is prime.
 * The algorithm is based on properties of the Jacobi symbol and modular exponentiation.
 *
 * For more information, go to {@link https://en.wikipedia.org/wiki/Solovay%E2%80%93Strassen_primality_test}
 */
final class SolovayStrassenPrimalityTest {

    private final Random random;

    /**
     * Constructs a SolovayStrassenPrimalityTest instance with a specified seed for randomness.
     *
     * @param seed the seed for generating random numbers
     */
    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    /**
     * Factory method to create an instance of SolovayStrassenPrimalityTest.
     *
     * @param seed the seed for generating random numbers
     * @return a new instance of SolovayStrassenPrimalityTest
     */
    public static SolovayStrassenPrimalityTest getSolovayStrassenPrimalityTest(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    /**
     * Calculates modular exponentiation using exponentiation by squaring.
     *
     * @param base the base number
     * @param exponent the exponent
     * @param mod the modulus
     * @return (base^exponent) mod mod
     */
    private static long calculateModularExponentiation(long base, long exponent, long mod) {
        long result = 1L;
        long currentBase = base % mod;

        while (exponent > 0) {
            if ((exponent & 1L) == 1L) {
                result = (result * currentBase) % mod;
            }
            currentBase = (currentBase * currentBase) % mod;
            exponent >>= 1;
        }

        return result;
    }

    /**
     * Computes the Jacobi symbol (a/n), which is a generalization of the Legendre symbol.
     *
     * @param a the numerator
     * @param n the denominator (must be an odd positive integer)
     * @return the Jacobi symbol value: 1, -1, or 0
     */
    public int calculateJacobi(long a, long n) {
        if (n <= 0 || (n & 1L) == 0L) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            while ((a & 1L) == 0L) {
                a >>= 1;
                long nMod8 = n & 7L;
                if (nMod8 == 3L || nMod8 == 5L) {
                    jacobi = -jacobi;
                }
            }

            long temp = a;
            a = n;
            n = temp;

            if ((a & 3L) == 3L && (n & 3L) == 3L) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    /**
     * Performs the Solovay-Strassen primality test on a given number.
     *
     * @param num the number to be tested for primality
     * @param iterations the number of iterations to run for accuracy
     * @return true if num is likely prime, false if it is composite
     */
    public boolean solovayStrassen(long num, int iterations) {
        if (num <= 1) {
            return false;
        }
        if (num <= 3) {
            return true;
        }

        long exponent = (num - 1) / 2;

        for (int i = 0; i < iterations; i++) {
            long base = getRandomBase(num);
            long jacobi = (num + calculateJacobi(base, num)) % num;
            long modExpResult = calculateModularExponentiation(base, exponent, num);

            if (jacobi == 0 || modExpResult != jacobi) {
                return false;
            }
        }

        return true;
    }

    /**
     * Generates a random base 'a' in the range [1, num - 1].
     *
     * @param num the upper bound (exclusive) for the random base
     * @return a random base in [1, num - 1]
     */
    private long getRandomBase(long num) {
        long bound = num - 1;
        long randomValue = Math.abs(random.nextLong() % bound);
        return randomValue + 1;
    }
}