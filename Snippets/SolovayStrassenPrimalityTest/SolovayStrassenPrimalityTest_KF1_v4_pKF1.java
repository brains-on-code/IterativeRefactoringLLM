package com.thealgorithms.maths;

import java.util.Random;

final class SolovayStrassenPrimalityTest {

    private final Random randomGenerator;

    private SolovayStrassenPrimalityTest(int seed) {
        this.randomGenerator = new Random(seed);
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

    public int jacobiSymbol(long numerator, long denominator) {
        if (denominator <= 0 || (denominator & 1) == 0) {
            return 0;
        }

        numerator %= denominator;
        int jacobiValue = 1;

        long currentNumerator = numerator;
        long currentDenominator = denominator;

        while (currentNumerator != 0) {
            while ((currentNumerator & 1) == 0) {
                currentNumerator >>= 1;
                long denominatorMod8 = currentDenominator & 7;
                if (denominatorMod8 == 3 || denominatorMod8 == 5) {
                    jacobiValue = -jacobiValue;
                }
            }

            long previousNumerator = currentNumerator;
            currentNumerator = currentDenominator;
            currentDenominator = previousNumerator;

            if ((currentNumerator & 3) == 3 && (currentDenominator & 3) == 3) {
                jacobiValue = -jacobiValue;
            }

            currentNumerator %= currentDenominator;
        }

        return (currentDenominator == 1) ? jacobiValue : 0;
    }

    public boolean isProbablyPrime(long candidate, int iterations) {
        if (candidate <= 1) {
            return false;
        }
        if (candidate <= 3) {
            return true;
        }

        for (int iterationIndex = 0; iterationIndex < iterations; iterationIndex++) {
            long randomBase = Math.abs(randomGenerator.nextLong() % (candidate - 1)) + 2;
            long base = randomBase % (candidate - 1) + 1;

            long jacobiValue = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long exponent = (candidate - 1) / 2;
            long powerModuloCandidate = modularExponentiation(base, exponent, candidate);

            if (jacobiValue == 0 || powerModuloCandidate != jacobiValue) {
                return false;
            }
        }

        return true;
    }
}