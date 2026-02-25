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
        int digitCount = String.valueOf(n).length();
        long modulus = (long) Math.pow(10, digitCount);

        return square % modulus == n;
    }

    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        String nStr = Long.toString(n);
        String squareStr = Long.toString(n * n);

        return squareStr.endsWith(nStr);
    }

    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);
        if (n.signum() < 0) {
            return false;
        }

        String nStr = n.toString();
        String squareStr = n.multiply(n).toString();

        return squareStr.endsWith(nStr);
    }
}