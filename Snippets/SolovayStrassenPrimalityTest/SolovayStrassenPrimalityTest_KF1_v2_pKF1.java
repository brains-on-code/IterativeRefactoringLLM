package com.thealgorithms.maths;

import java.util.Random;

final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    public static SolovayStrassenPrimalityTest createWithSeed(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            exponent >>= 1;
        }

        return result % modulus;
    }

    public int jacobiSymbol(long a, long n) {
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

    public boolean isProbablyPrime(long candidate, int iterations) {
        if (candidate <= 1) {
            return false;
        }
        if (candidate <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long randomBase = Math.abs(random.nextLong() % (candidate - 1)) + 2;
            long base = randomBase % (candidate - 1) + 1;

            long jacobi = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long modExp = modularExponentiation(base, (candidate - 1) / 2, candidate);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }
}