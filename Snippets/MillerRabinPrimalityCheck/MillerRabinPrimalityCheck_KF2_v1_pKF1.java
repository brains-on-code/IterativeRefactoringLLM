package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private MillerRabinPrimalityCheck() {
    }

    public static boolean isProbablePrime(long number, int iterations) {
        if (number < 4) {
            return number == 2 || number == 3;
        }

        int exponentOfTwo = 0;
        long oddComponent = number - 1;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            exponentOfTwo++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long base = 2 + random.nextLong(number) % (number - 3);
            if (isComposite(number, base, oddComponent, exponentOfTwo)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeDeterministic(long number) {
        if (number < 2) {
            return false;
        }

        int exponentOfTwo = 0;
        long oddComponent = number - 1;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            exponentOfTwo++;
        }

        int[] bases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int base : bases) {
            if (number == base) {
                return true;
            }
            if (isComposite(number, base, oddComponent, exponentOfTwo)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isComposite(long number, long base, long oddComponent, int exponentOfTwo) {
        long current = modularExponentiation(base, oddComponent, number);
        if (current == 1 || current == number - 1) {
            return false;
        }
        for (int i = 1; i < exponentOfTwo; i++) {
            current = modularExponentiation(current, 2, number);
            if (current == number - 1) {
                return false;
            }
        }
        return true;
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;

        base = base % modulus;

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