package com.thealgorithms.maths;

import java.util.Random;

final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    public static SolovayStrassenPrimalityTest create(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long power = base % modulus;

        while (exponent > 0) {
            boolean isExponentOdd = (exponent & 1) == 1;
            if (isExponentOdd) {
                result = (result * power) % modulus;
            }
            power = (power * power) % modulus;
            exponent >>= 1;
        }

        return result % modulus;
    }

    public int jacobiSymbol(long numerator, long denominator) {
        if (denominator <= 0 || (denominator & 1) == 0) {
            return 0;
        }

        numerator %= denominator;
        int jacobi = 1;

        while (numerator != 0) {
            while ((numerator & 1) == 0) {
                numerator >>= 1;
                long denominatorMod8 = denominator & 7;
                if (denominatorMod8 == 3 || denominatorMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long previousNumerator = numerator;
            numerator = denominator;
            denominator = previousNumerator;

            boolean isNumeratorCongruent3Mod4 = (numerator & 3) == 3;
            boolean isDenominatorCongruent3Mod4 = (denominator & 3) == 3;
            if (isNumeratorCongruent3Mod4 && isDenominatorCongruent3Mod4) {
                jacobi = -jacobi;
            }

            numerator %= denominator;
        }

        return (denominator == 1) ? jacobi : 0;
    }

    public boolean isProbablePrime(long candidate, int iterations) {
        if (candidate <= 1) {
            return false;
        }
        if (candidate <= 3) {
            return true;
        }

        for (int iteration = 0; iteration < iterations; iteration++) {
            long randomBase = Math.abs(random.nextLong() % (candidate - 1)) + 2;
            long base = randomBase % (candidate - 1) + 1;

            long adjustedJacobiSymbol = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long exponentiationResult =
                    modularExponentiation(base, (candidate - 1) / 2, candidate);

            if (adjustedJacobiSymbol == 0 || exponentiationResult != adjustedJacobiSymbol) {
                return false;
            }
        }

        return true;
    }
}