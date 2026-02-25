package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {

    private static final int[] DETERMINISTIC_BASES =
            {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37};

    private MillerRabinPrimalityCheck() {
        // Utility class; prevent instantiation
    }

    public static boolean millerRabin(long n, int iterations) {
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
        while ((d & 1) == 0) {
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

    private static boolean isComposite(long n, long a, long d, int s) {
        long x = powerMod(a, d, n);
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
        long result = 1;
        base %= modulus;

        if (base == 0) {
            return 0;
        }

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = multiplyMod(result, base, modulus);
            }
            exponent >>= 1;
            base = multiplyMod(base, base, modulus);
        }
        return result;
    }

    private static long multiplyMod(long a, long b, long modulus) {
        long aHi = a >> 24;
        long aLo = a & ((1L << 24) - 1);
        long bHi = b >> 24;
        long bLo = b & ((1L << 24) - 1);

        long result = ((((aHi * bHi << 16) % modulus) << 16) % modulus) << 16;
        result += ((aLo * bHi + aHi * bLo) << 24) + aLo * bLo;

        return result % modulus;
    }
}