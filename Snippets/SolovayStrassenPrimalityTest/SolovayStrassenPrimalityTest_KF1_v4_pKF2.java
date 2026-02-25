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
            jacobi = applyTwoFactorAdjustments(a, n, jacobi);

            long previousA = a;
            a = n;
            n = previousA;

            if (areBothThreeModuloFour(a, n)) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    /**
     * Adjusts the Jacobi symbol for factors of 2 in {@code a}.
     */
    private int applyTwoFactorAdjustments(long a, long n, int jacobi) {
        while ((a & 1) == 0) {
            a >>= 1;
            long nMod8 = n & 7;
            if (nMod8 == 3 || nMod8 == 5) {
                jacobi = -jacobi;
            }
        }
        return jacobi;
    }

    /**
     * Checks if both {@code a} and {@code n} are congruent to 3 modulo 4.
     */
    private boolean areBothThreeModuloFour(long a, long n) {
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
            long base = randomBaseInRange(n);

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
    private long randomBaseInRange(long n) {
        return Math.abs(random.nextLong() % (n - 1)) + 2;
    }
}