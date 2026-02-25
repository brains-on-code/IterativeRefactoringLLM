package com.thealgorithms.maths.prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private MillerRabinPrimalityCheck() {
    }

    public static boolean isProbablePrime(long n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long a = 2 + random.nextLong(n) % (n - 3);
            if (isCompositeWitness(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPrimeDeterministic(long n) {
        if (n < 2) {
            return false;
        }

        long d = n - 1;
        int s = 0;
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }

        int[] testBases = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};
        for (int a : testBases) {
            if (n == a) {
                return true;
            }
            if (isCompositeWitness(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isCompositeWitness(long n, long base, long d, int s) {
        long x = modularExponentiation(base, d, n);
        if (x == 1 || x == n - 1) {
            return false;
        }
        for (int r = 1; r < s; r++) {
            x = modularExponentiation(x, 2, n);
            if (x == n - 1) {
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

    private static long modularMultiplication(long x, long y, long modulus) {
        long xHigh = x >> 24;
        long xLow = x & ((1 << 24) - 1);
        long yHigh = y >> 24;
        long yLow = y & ((1 << 24) - 1);

        long result = ((((xHigh * yHigh << 16) % modulus) << 16) % modulus) << 16;
        result += ((xLow * yHigh + xHigh * yLow) << 24) + xLow * yLow;

        return result % modulus;
    }
}