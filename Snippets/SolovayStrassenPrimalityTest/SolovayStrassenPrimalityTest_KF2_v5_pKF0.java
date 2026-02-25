package com.thealgorithms.maths;

import java.util.Random;

final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    public static SolovayStrassenPrimalityTest getSolovayStrassenPrimalityTest(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base % modulus;
        long currentExponent = exponent;

        while (currentExponent > 0) {
            if ((currentExponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            currentExponent >>= 1;
        }

        return result % modulus;
    }

    public int calculateJacobi(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        long currentA = a % n;
        long currentN = n;
        int jacobi = 1;

        while (currentA != 0) {
            currentA = removeFactorsOfTwo(currentA, currentN);
            jacobi = adjustJacobiForTwoFactors(currentN, jacobi);

            long temp = currentA;
            currentA = currentN;
            currentN = temp;

            if (areBothThreeModFour(currentA, currentN)) {
                jacobi = -jacobi;
            }

            currentA %= currentN;
        }

        return currentN == 1 ? jacobi : 0;
    }

    private int adjustJacobiForTwoFactors(long n, int jacobi) {
        long nMod8 = n & 7;
        if (nMod8 == 3 || nMod8 == 5) {
            return -jacobi;
        }
        return jacobi;
    }

    private long removeFactorsOfTwo(long a, long n) {
        long currentA = a;
        while ((currentA & 1) == 0) {
            currentA >>= 1;
        }
        return currentA;
    }

    private boolean areBothThreeModFour(long a, long n) {
        return (a & 3) == 3 && (n & 3) == 3;
    }

    public boolean isProbablePrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long a = getRandomBase(n);

            long jacobiSymbol = (n + calculateJacobi(a, n)) % n;
            long modExpResult = modularExponentiation(a, (n - 1) / 2, n);

            if (jacobiSymbol == 0 || modExpResult != jacobiSymbol) {
                return false;
            }
        }

        return true;
    }

    private long getRandomBase(long n) {
        return 2 + Math.abs(random.nextLong()) % (n - 3);
    }
}