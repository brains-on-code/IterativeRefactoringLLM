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
        long currentExponent = exponent;

        while (currentExponent > 0) {
            if ((currentExponent & 1) == 1) {
                result = (result * currentBase) % modulus;
            }
            currentBase = (currentBase * currentBase) % modulus;
            currentExponent >>= 1;
        }

        return result;
    }

    public int jacobiSymbol(long numerator, long denominator) {
        if (denominator <= 0 || (denominator & 1) == 0) {
            return 0;
        }

        numerator %= denominator;
        int jacobi = 1;

        long currentNumerator = numerator;
        long currentDenominator = denominator;

        while (currentNumerator != 0) {
            while ((currentNumerator & 1) == 0) {
                currentNumerator >>= 1;
                long denominatorMod8 = currentDenominator & 7;
                if (denominatorMod8 == 3 || denominatorMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long temp = currentNumerator;
            currentNumerator = currentDenominator;
            currentDenominator = temp;

            if ((currentNumerator & 3) == 3 && (currentDenominator & 3) == 3) {
                jacobi = -jacobi;
            }

            currentNumerator %= currentDenominator;
        }

        return (currentDenominator == 1) ? jacobi : 0;
    }

    public boolean isProbablyPrime(long candidate, int iterations) {
        if (candidate <= 1) {
            return false;
        }
        if (candidate <= 3) {
            return true;
        }

        for (int iteration = 0; iteration < iterations; iteration++) {
            long randomBase = Math.abs(random.nextLong() % (candidate - 1)) + 2;
            long base = randomBase % (candidate - 1) + 1;

            long jacobiValue = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long exponent = (candidate - 1) / 2;
            long modularExponentiationResult = modularExponentiation(base, exponent, candidate);

            if (jacobiValue == 0 || modularExponentiationResult != jacobiValue) {
                return false;
            }
        }

        return true;
    }
}