package com.thealgorithms.maths.prime;

import java.util.Random;

public final class MillerRabinPrimality {

    private MillerRabinPrimality() {
    }

    public static boolean isProbablePrime(long number, int iterations) {
        if (number < 4) {
            return number == 2 || number == 3;
        }

        long oddComponent = number - 1;
        int powerOfTwo = 0;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            powerOfTwo++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long base =
                    2 + Math.floorMod(random.nextLong(), number - 3);
            if (isCompositeWitness(number, base, oddComponent, powerOfTwo)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isDeterministicPrime(long number) {
        if (number < 2) {
            return false;
        }

        long oddComponent = number - 1;
        int powerOfTwo = 0;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            powerOfTwo++;
        }

        int[] deterministicBases =
                new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int base : deterministicBases) {
            if (number == base) {
                return true;
            }
            if (isCompositeWitness(number, base, oddComponent, powerOfTwo)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCompositeWitness(
            long number, long base, long oddComponent, int powerOfTwo) {
        long current = modularExponentiation(base, oddComponent, number);
        if (current == 1 || current == number - 1) {
            return false;
        }
        for (int iteration = 1; iteration < powerOfTwo; iteration++) {
            current = modularExponentiation(current, 2, number);
            if (current == number - 1) {
                return false;
            }
        }
        return true;
    }

    private static long modularExponentiation(
            long base, long exponent, long modulus) {
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

    private static long modularMultiplication(
            long multiplicand, long multiplier, long modulus) {
        long multiplicandHigh = multiplicand >> 24;
        long multiplicandLow = multiplicand & ((1 << 24) - 1);
        long multiplierHigh = multiplier >> 24;
        long multiplierLow = multiplier & ((1 << 24) - 1);

        long highProduct =
                ((((multiplicandHigh * multiplierHigh << 16) % modulus) << 16)
                                % modulus)
                        << 16;
        highProduct +=
                ((multiplicandLow * multiplierHigh
                                        + multiplicandHigh * multiplierLow)
                                << 24)
                        + multiplicandLow * multiplierLow;
        return highProduct % modulus;
    }
}