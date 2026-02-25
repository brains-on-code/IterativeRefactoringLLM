package com.thealgorithms.maths;

import java.util.Random;

/**
 * Probabilistic primality test using the Solovay–Strassen algorithm.
 */
final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    public static SolovayStrassenPrimalityTest withSeed(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    /**
     * Computes (base^exponent) mod modulus using fast modular exponentiation.
     */
    private static long modularExponentiation(long base, long exponent, long modulus) {
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
     * @param a integer whose Jacobi symbol with respect to n is to be computed
     * @param n odd positive integer (modulus)
     * @return -1, 0, or 1 representing the Jacobi symbol (a/n)
     */
    public int jacobiSymbol(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            jacobi = adjustForFactorsOfTwo(a, n, jacobi);
            long temp = a;
            a = n;
            n = temp;

            if (bothCongruentToThreeModuloFour(a, n)) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    private int adjustForFactorsOfTwo(long a, long n, int jacobi) {
        while ((a & 1) == 0) {
            a >>= 1;
            long nMod8 = n & 7;
            if (nMod8 == 3 || nMod8 == 5) {
                jacobi = -jacobi;
            }
        }
        return jacobi;
    }

    private boolean bothCongruentToThreeModuloFour(long a, long n) {
        return (a & 3) == 3 && (n & 3) == 3;
    }

    /**
     * Performs the Solovay–Strassen probabilistic primality test.
     *
     * @param n          number to test for primality
     * @param iterations number of random bases to test; higher values reduce
     *                   the probability of a composite being reported as prime
     * @return {@code true} if n is probably prime, {@code false} if n is composite
     */
    public boolean isProbablePrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long a = randomBaseInRange(n);

            long jacobi = (n + jacobiSymbol(a, n)) % n;
            long modExp = modularExponentiation(a, (n - 1) / 2, n);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }

    private long randomBaseInRange(long n) {
        return Math.abs(random.nextLong() % (n - 1)) + 2;
    }
}