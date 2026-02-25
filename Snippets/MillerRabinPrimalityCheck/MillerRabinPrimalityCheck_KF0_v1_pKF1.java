package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {
    private MillerRabinPrimalityCheck() {}

    /**
     * Probabilistic Miller–Rabin primality test.
     *
     * @param number number to test for primality
     * @param iterations number of test rounds
     * @return true if the number is probably prime, false if it is composite
     */
    public static boolean millerRabin(long number, int iterations) {
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

    /**
     * Deterministic Miller–Rabin primality test for 64-bit integers.
     *
     * @param number number to test for primality
     * @return true if the number is prime, false otherwise
     */
    public static boolean deterministicMillerRabin(long number) {
        if (number < 2) {
            return false;
        }

        int exponentOfTwo = 0;
        long oddComponent = number - 1;
        while ((oddComponent & 1) == 0) {
            oddComponent >>= 1;
            exponentOfTwo++;
        }

        int[] deterministicBases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int base : deterministicBases) {
            if (number == base) {
                return true;
            }
            if (isComposite(number, base, oddComponent, exponentOfTwo)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if number is composite using a single Miller–Rabin round.
     *
     * @param number number to test
     * @param base witness base
     * @param oddComponent odd component d in number - 1 = 2^s * d
     * @param exponentOfTwo exponent s in number - 1 = 2^s * d
     * @return true if number is composite, false otherwise
     */
    private static boolean isComposite(long number, long base, long oddComponent, int exponentOfTwo) {
        long current = modularExponentiation(base, oddComponent, number);
        if (current == 1 || current == number - 1) {
            return false;
        }
        for (int round = 1; round < exponentOfTwo; round++) {
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

        long result =
                ((((multiplicandHigh * multiplierHigh << 16) % modulus) << 16) % modulus) << 16;
        result += ((multiplicandLow * multiplierHigh + multiplicandHigh * multiplierLow) << 24)
                + multiplicandLow * multiplierLow;

        return result % modulus;
    }
}