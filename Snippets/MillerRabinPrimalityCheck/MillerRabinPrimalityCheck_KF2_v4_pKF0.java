package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private static final int[] DETERMINISTIC_BASES = {
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37
    };

    private MillerRabinPrimalityCheck() {
        // Utility class; prevent instantiation
    }

    public static boolean millerRabin(long n, int iterations) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        long d = n - 1;
        int s = 0;
        while (isEven(d)) {
            d >>= 1;
            s++;
        }

        Random random = new Random();
        for (int i = 0; i < iterations; i++) {
            long a = 2 + Math.floorMod(random.nextLong(), n - 3);
            if (isComposite(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean deterministicMillerRabin(long n) {
        if (n < 2) {
            return false;
        }

        long d = n - 1;
        int s = 0;
        while (isEven(d)) {
            d >>= 1;
            s++;
        }

        for (int base : DETERMINISTIC_BASES) {
            if (n == base) {
                return true;
            }
            if (n > base && isComposite(n, base, d, s)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isComposite(long n, long base, long d, int s) {
        long x = powerMod(base, d, n);
        if (x == 1 || x == n - 1) {
            return false;
        }

        for (int r = 1; r < s; r++) {
            x = powerMod(x, 2, n);
            if (x == n - 1) {
                return false;
            }
        }
        return true;
    }

    private static long powerMod(long base, long exponent, long modulus) {
        if (modulus == 1) {
            return 0;
        }

        long result = 1;
        base %= modulus;

        if (base == 0) {
            return 0;
        }

        while (exponent > 0) {
            if (isOdd(exponent)) {
                result = multiplyMod(result, base, modulus);
            }
            exponent >>= 1;
            base = multiplyMod(base, base, modulus);
        }
        return result;
    }

    private static long multiplyMod(long a, long b, long modulus) {
        long aHigh = a >> 24;
        long aLow = a & ((1L << 24) - 1);
        long bHigh = b >> 24;
        long bLow = b & ((1L << 24) - 1);

        long highProduct = aHigh * bHigh;
        long crossProduct = aLow * bHigh + aHigh * bLow;
        long lowProduct = aLow * bLow;

        long result = ((((highProduct << 16) % modulus) << 16) % modulus) << 16;
        result += (crossProduct << 24) + lowProduct;

        return result % modulus;
    }

    private static boolean isEven(long value) {
        return (value & 1) == 0;
    }

    private static boolean isOdd(long value) {
        return (value & 1) == 1;
    }
}