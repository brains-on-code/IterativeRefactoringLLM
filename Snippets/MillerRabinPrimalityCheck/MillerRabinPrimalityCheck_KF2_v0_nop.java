package com.thealgorithms.maths.Prime;

import java.util.Random;

public final class MillerRabinPrimalityCheck {
    private MillerRabinPrimalityCheck() {
    }



    public static boolean millerRabin(long n, int k) {
        if (n < 4) {
            return n == 2 || n == 3;
        }

        int s = 0;
        long d = n - 1;
        while ((d & 1) == 0) {
            d >>= 1;
            s++;
        }
        Random rnd = new Random();
        for (int i = 0; i < k; i++) {
            long a = 2 + rnd.nextLong(n) % (n - 3);
            if (checkComposite(n, a, d, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean deterministicMillerRabin(long n) {
        if (n < 2) {
            return false;
        }

        int r = 0;
        long d = n - 1;
        while ((d & 1) == 0) {
            d >>= 1;
            r++;
        }

        for (int a : new int[] {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}) {
            if (n == a) {
                return true;
            }
            if (checkComposite(n, a, d, r)) {
                return false;
            }
        }
        return true;
    }


    private static boolean checkComposite(long n, long a, long d, int s) {
        long x = powerModP(a, d, n);
        if (x == 1 || x == n - 1) {
            return false;
        }
        for (int r = 1; r < s; r++) {
            x = powerModP(x, 2, n);
            if (x == n - 1) {
                return false;
            }
        }
        return true;
    }

    private static long powerModP(long x, long y, long p) {
        long res = 1;

        x = x % p;

        if (x == 0) {
            return 0;
        }
        while (y > 0) {
            if ((y & 1) == 1) {
                res = multiplyModP(res, x, p);
            }

            y = y >> 1;
            x = multiplyModP(x, x, p);
        }
        return res;
    }

    private static long multiplyModP(long a, long b, long p) {
        long aHi = a >> 24;
        long aLo = a & ((1 << 24) - 1);
        long bHi = b >> 24;
        long bLo = b & ((1 << 24) - 1);
        long result = ((((aHi * bHi << 16) % p) << 16) % p) << 16;
        result += ((aLo * bHi + aHi * bLo) << 24) + aLo * bLo;
        return result % p;
    }
}
