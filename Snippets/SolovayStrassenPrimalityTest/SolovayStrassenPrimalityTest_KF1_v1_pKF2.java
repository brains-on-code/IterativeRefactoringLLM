package com.thealgorithms.maths;

import java.util.Random;

/**
 * Utility class for probabilistic primality testing using
 * the Solovay–Strassen primality test.
 */
final class Class1 {

    /** Source of randomness for the primality test. */
    private final Random random;

    /**
     * Creates an instance with a deterministic random generator.
     *
     * @param seed seed for the random number generator
     */
    private Class1(int seed) {
        this.random = new Random(seed);
    }

    /**
     * Factory method to create a new instance.
     *
     * @param seed seed for the random number generator
     * @return new {@code Class1} instance
     */
    public static Class1 method1(int seed) {
        return new Class1(seed);
    }

    /**
     * Computes (base^exponent) mod modulus using fast modular exponentiation.
     *
     * @param base     the base
     * @param exponent the exponent (non‑negative)
     * @param modulus  the modulus (positive)
     * @return (base^exponent) mod modulus
     */
    private static long method2(long base, long exponent, long modulus) {
        long result = 1;
        long currentPower = base % modulus;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * currentPower) % modulus;
            }
            currentPower = (currentPower * currentPower) % modulus;
            exponent >>= 1;
        }

        return result % modulus;
    }

    /**
     * Computes the Jacobi symbol (a/n).
     *
     * The Jacobi symbol is a generalization of the Legendre symbol and is used
     * in various number‑theoretic algorithms, including the Solovay–Strassen
     * primality test.
     *
     * @param a integer whose Jacobi symbol with respect to n is to be computed
     * @param n odd positive integer (modulus)
     * @return -1, 0, or 1 representing the Jacobi symbol (a/n)
     */
    public int method3(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            while ((a & 1) == 0) {
                a >>= 1;
                long nMod8 = n & 7;
                if (nMod8 == 3 || nMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long temp = a;
            a = n;
            n = temp;

            if ((a & 3) == 3 && (n & 3) == 3) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    /**
     * Performs the Solovay–Strassen probabilistic primality test.
     *
     * @param n         number to test for primality
     * @param iterations number of random bases to test; higher values reduce
     *                   the probability of a composite being reported as prime
     * @return {@code true} if n is probably prime, {@code false} if n is composite
     */
    public boolean method4(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long randomBase = Math.abs(random.nextLong() % (n - 1)) + 2;
            long a = randomBase % (n - 1) + 1;

            long jacobi = (n + method3(a, n)) % n;
            long modExp = method2(a, (n - 1) / 2, n);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }
}