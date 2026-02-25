package com.thealgorithms.maths.prime;

import java.util.Random;

public final class MillerRabinPrimality {

    private MillerRabinPrimality() {
    }

    public static boolean isProbablePrime(long candidate, int iterations) {
        if (candidate < 4) {
            return candidate == 2 || candidate == 3;
        }

        long oddFactor = candidate - 1;
        int powerOfTwoFactor = 0;
        while ((oddFactor & 1) == 0) {
            oddFactor >>= 1;
            powerOfTwoFactor++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long base = 2 + Math.floorMod(random.nextLong(), candidate - 3);
            if (isCompositeWitness(candidate, base, oddFactor, powerOfTwoFactor)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDeterministicPrime(long candidate) {
        if (candidate < 2) {
            return false;
        }

        long oddFactor = candidate - 1;
        int powerOfTwoFactor = 0;
        while ((oddFactor & 1) == 0) {
            oddFactor >>= 1;
            powerOfTwoFactor++;
        }

        int[] deterministicBases = new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int base : deterministicBases) {
            if (candidate == base) {
                return true;
            }
            if (isCompositeWitness(candidate, base, oddFactor, powerOfTwoFactor)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCompositeWitness(
            long candidate, long base, long oddFactor, int powerOfTwoFactor) {
        long currentValue = modularExponentiation(base, oddFactor, candidate);
        if (currentValue == 1 || currentValue == candidate - 1) {
            return false;
        }
        for (int i = 1; i < powerOfTwoFactor; i++) {
            currentValue = modularExponentiation(currentValue, 2, candidate);
            if (currentValue == candidate - 1) {
                return false;
            }
        }
        return true;
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;

        base %= modulus;

        if (base == 0) {
            return 0;
        }
        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = modularMultiplication(result, base, modulus);
            }

            exponent >>= 1;
            base = modularMultiplication(base, base, modulus);
        }
        return result;
    }

    private static long modularMultiplication(long multiplicand, long multiplier, long modulus) {
        long multiplicandHigh = multiplicand >> 24;
        long multiplicandLow = multiplicand & ((1 << 24) - 1);
        long multiplierHigh = multiplier >> 24;
        long multiplierLow = multiplier & ((1 << 24) - 1);

        long highProduct =
                ((((multiplicandHigh * multiplierHigh << 16) % modulus) << 16) % modulus) << 16;
        highProduct +=
                ((multiplicandLow * multiplierHigh + multiplicandHigh * multiplierLow) << 24)
                        + multiplicandLow * multiplierLow;
        return highProduct % modulus;
    }
}