package com.thealgorithms.maths;

import java.util.Random;

final class SolovayStrassenPrimalityTest {

    private final Random random;

    private SolovayStrassenPrimalityTest(int seed) {
        this.random = new Random(seed);
    }

    public static SolovayStrassenPrimalityTest create(int seed) {
        return new SolovayStrassenPrimalityTest(seed);
    }

    private static long modularExponentiation(long base, long exponent, long modulus) {
        long result = 1;
        long power = base % modulus;

        while (exponent > 0) {
            boolean isOddExponent = (exponent & 1) == 1;
            if (isOddExponent) {
                result = (result * power) % modulus;
            }
            power = (power * power) % modulus;
            exponent >>= 1;
        }

        return result % modulus;
    }

    public int jacobiSymbol(long a, long n) {
        if (n <= 0 || (n & 1) == 0) {
            return 0;
        }

        a %= n;
        int jacobi = 1;

        while (a != 0) {
            while ((a & 1) == 0) {
                a >>= 1;
                long nMod8 = n & 7;
                if (nMod8 == 3 || nMod8 == 5) {
                    jacobi = -jacobi;
                }
            }

            long temp = a;
            a = n;
            n = temp;

            if ((a & 3) == 3 && (n & 3) == 3) {
                jacobi = -jacobi;
            }

            a %= n;
        }

        return (n == 1) ? jacobi : 0;
    }

    public boolean isProbablePrime(long n, int iterations) {
        if (n <= 1) {
            return false;
        }
        if (n <= 3) {
            return true;
        }

        for (int i = 0; i < iterations; i++) {
            long randomValue = Math.abs(random.nextLong() % (n - 1)) + 2;
            long base = randomValue % (n - 1) + 1;

            long jacobi = (n + jacobiSymbol(base, n)) % n;
            long modExpResult = modularExponentiation(base, (n - 1) / 2, n);

            if (jacobi == 0 || modExpResult != jacobi) {
                return false;
            }
        }

        return true;
    }
}