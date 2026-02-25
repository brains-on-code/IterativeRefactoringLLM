package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * Utility class for checking whether numbers are automorphic.
 *
 * <p>An automorphic number is a number whose square ends with the same digits
 * as the number itself. For example, 5 (since 5² = 25) and 76 (since 76² = 5776)
 * are automorphic numbers.
 */
public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Prevent instantiation
    }

    /**
     * Checks if a non-negative {@code long} is an automorphic number using arithmetic.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is automorphic; {@code false} otherwise
     */
    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;

        int digitCount = (n == 0) ? 1 : 0;
        long temp = n;
        while (temp > 0) {
            digitCount++;
            temp /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long lastDigits = square % modulus;

        return n == lastDigits;
    }

    /**
     * Checks if a non-negative {@code long} is an automorphic number using string comparison.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is automorphic; {@code false} otherwise
     */
    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        String nStr = Long.toString(n);
        String squareStr = Long.toString(square);

        return squareStr.endsWith(nStr);
    }

    /**
     * Checks if a non-negative integer represented as a string is an automorphic number.
     *
     * @param s the string representation of the number to check
     * @return {@code true} if the number is automorphic; {@code false} otherwise
     * @throws NumberFormatException if the string does not contain a parsable integer
     */
    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);

        if (n.signum() < 0) {
            return false;
        }

        BigInteger square = n.multiply(n);
        String nStr = n.toString();
        String squareStr = square.toString();

        return squareStr.endsWith(nStr);
    }
}