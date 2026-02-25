package com.thealgorithms.maths;

import java.util.Random;

final class Class1 {

    private final Random random;

    private Class1(int seed) {
        random = new Random(seed);
    }

    public static Class1 createWithSeed(int seed) {
        return new Class1(seed);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long currentBase = base;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = result * currentBase % modulus;
            }
            currentBase = currentBase * currentBase % modulus;
            exponent = exponent / 2;
        }

        return result % modulus;
    }

    public int jacobiSymbol(long a, long n) {
        if (n <= 0 || n % 2 == 0) {
            return 0;
        }

        a = a % n;
        int result = 1;

        while (a != 0) {
            while (a % 2 == 0) {
                a /= 2;
                long nMod8 = n % 8;
                if (nMod8 == 3 || nMod8 == 5) {
                    result = -result;
                }
            }

            long temp = a;
            a = n;
            n = temp;

            if (a % 4 == 3 && n % 4 == 3) {
                result = -result;
            }

            a = a % n;
        }

        return (n == 1) ? result : 0;
    }

    public boolean isProbablyPrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long randomBaseRaw = Math.abs(random.nextLong() % (n - 1)) + 2;
            long base = randomBaseRaw % (n - 1) + 1;

            long jacobi = (n + jacobiSymbol(base, n)) % n;
            long modExp = modularExponentiation(base, (n - 1) / 2, n);

            if (jacobi == 0 || modExp != jacobi) {
                return false;
            }
        }

        return true;
    }
}