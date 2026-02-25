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
        long currentBase = base % modulus;
        long remainingExponent = exponent;

        while (remainingExponent > 0) {
            if ((remainingExponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            remainingExponent >>= 1;
        }

        return result;
    }

    public int jacobiSymbol(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        long currentA = a;
        long currentN = n;

        while (currentA != 0) {
            while ((currentA & 1) == 0) {
                currentA >>= 1;
                long nMod8 = currentN & 7;
                if (nMod8 == 3 || nMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long temp = currentA;
            currentA = currentN;
            currentN = temp;

            if ((currentA & 3) == 3 && (currentN & 3) == 3) {
                jacobi = -jacobi;
            }

            currentA %= currentN;
        }

        return (currentN == 1) ? jacobi : 0;
    }

    public boolean isProbablyPrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long randomBase = Math.abs(random.nextLong() % (n - 1)) + 2;
            long a = randomBase % (n - 1) + 1;

            long jacobi = (n + jacobiSymbol(a, n)) % n;
            long exponent = (n - 1) / 2;
            long modExpResult = modularExponentiation(a, exponent, n);

            if (jacobi == 0 || modExpResult != jacobi) {
                return false;
            }
        }

        return true;
    }
}