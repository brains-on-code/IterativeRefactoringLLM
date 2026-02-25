package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {
    private AutomorphicNumber() {
    }


    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }
        long square = n * n;
        long t = n;
        long numberOfdigits = 0;
        while (t > 0) {
            numberOfdigits++;
            t /= 10;
        }
        long lastDigits = square % (long) Math.pow(10, numberOfdigits);
        return n == lastDigits;
    }


    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }
        long square = n * n;
        return String.valueOf(square).endsWith(String.valueOf(n));
    }


    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);
        if (n.signum() == -1) {
            return false;
        }
        BigInteger square = n.multiply(n);
        return String.valueOf(square).endsWith(String.valueOf(n));
    }
}
