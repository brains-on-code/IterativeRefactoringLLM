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
        long currentPower = base % modulus;

        while (exponent > 0) {
            boolean hasOddExponent = (exponent & 1) == 1;
            if (hasOddExponent) {
                result = (result * currentPower) % modulus;
            }
            currentPower = (currentPower * currentPower) % modulus;
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

            long temp = numerator;
            numerator = denominator;
            denominator = temp;

            if ((numerator & 3) == 3 && (denominator & 3) == 3) {
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

            long jacobiValue = (candidate + jacobiSymbol(base, candidate)) % candidate;
            long modularExponentiationResult =
                    modularExponentiation(base, (candidate - 1) / 2, candidate);

            if (jacobiValue == 0 || modularExponentiationResult != jacobiValue) {
                return false;
            }
        }

        return true;
    }
}