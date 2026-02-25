package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * <a href="https://en.wikipedia.org/wiki/Automorphic_number">Automorphic Number</a>
 * A number is said to be Automorphic if it appears in the last digit(s)
 * of its square. Example: 25² = 625, and 25 appears in the last two digits,
 * so 25 is an Automorphic Number.
 */
public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is an Automorphic number.
     *
     * @param n the number to be checked
     * @return {@code true} if {@code n} is an Automorphic number, otherwise {@code false}
     */
    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        int digitCount = String.valueOf(n).length();
        long modulus = (long) Math.pow(10, digitCount);
        long lastDigits = square % modulus;

        return n == lastDigits;
    }

    /**
     * Checks if a number is an Automorphic number using string operations.
     *
     * @param n the number to be checked
     * @return {@code true} if {@code n} is an Automorphic number, otherwise {@code false}
     */
    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        return String.valueOf(square).endsWith(String.valueOf(n));
    }

    /**
     * Checks if a number is an Automorphic number using {@link BigInteger}.
     *
     * @param s the number (as a string) to be checked
     * @return {@code true} if the number is an Automorphic number, otherwise {@code false}
     */
    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);
        if (n.signum() < 0) {
            return false;
        }

        BigInteger square = n.multiply(n);
        return square.toString().endsWith(n.toString());
    }
}