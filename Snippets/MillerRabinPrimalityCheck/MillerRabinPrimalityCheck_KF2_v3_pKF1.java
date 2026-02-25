package com.thealgorithms.maths.prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private MillerRabinPrimalityCheck() {
    }

    public static boolean isProbablePrime(long candidate, int iterations) {
        if (candidate < 4) {
            return candidate == 2 || candidate == 3;
        }

        long oddComponent = candidate - 1;
        int powerOfTwoFactor = 0;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            powerOfTwoFactor++;
        }

        Random random = new Random();
        for (int iteration = 0; iteration < iterations; iteration++) {
            long base = 2 + random.nextLong(candidate) % (candidate - 3);
            if (isCompositeWitness(candidate, base, oddComponent, powerOfTwoFactor)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeDeterministic(long candidate) {
        if (candidate < 2) {
            return false;
        }

        long oddComponent = candidate - 1;
        int powerOfTwoFactor = 0;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            powerOfTwoFactor++;
        }

        int[] deterministicBases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int base : deterministicBases) {
            if (candidate == base) {
                return true;
            }
            if (isCompositeWitness(candidate, base, oddComponent, powerOfTwoFactor)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCompositeWitness(long candidate, long base, long oddComponent, int powerOfTwoFactor) {
        long currentPower = modularExponentiation(base, oddComponent, candidate);
        if (currentPower == 1 || currentPower == candidate - 1) {
            return false;
        }
        for (int iteration = 1; iteration < powerOfTwoFactor; iteration++) {
            currentPower = modularExponentiation(currentPower, 2, candidate);
            if (currentPower == candidate - 1) {
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

        long result = ((((multiplicandHigh * multiplierHigh << 16) % modulus) << 16) % modulus) << 16;
        result += ((multiplicandLow * multiplierHigh + multiplicandHigh * multiplierLow) << 24)
                + multiplicandLow * multiplierLow;

        return result % modulus;
    }
}