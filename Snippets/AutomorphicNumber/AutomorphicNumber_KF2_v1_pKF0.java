package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        long temp = n;
        int digitCount = 0;

        while (temp > 0) {
            digitCount++;
            temp /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long lastDigits = square % modulus;

        return n == lastDigits;
    }

    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        String squareStr = Long.toString(square);
        String nStr = Long.toString(n);

        return squareStr.endsWith(nStr);
    }

    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);
        if (n.signum() < 0) {
            return false;
        }

        BigInteger square = n.multiply(n);
        String squareStr = square.toString();
        String nStr = n.toString();

        return squareStr.endsWith(nStr);
    }
}