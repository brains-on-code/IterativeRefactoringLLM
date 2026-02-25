package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a non-negative long is an automorphic number using arithmetic.
     * An automorphic number is a number whose square ends with the same digits as the number itself.
     *
     * @param n the number to check
     * @return true if n is automorphic, false otherwise
     */
    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;

        // Count digits in n
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

    /**
     * Checks if a non-negative long is an automorphic number using string comparison.
     *
     * @param n the number to check
     * @return true if n is automorphic, false otherwise
     */
    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        return String.valueOf(square).endsWith(String.valueOf(n));
    }

    /**
     * Checks if a non-negative integer represented as a string is an automorphic number.
     *
     * @param s the string representation of the number to check
     * @return true if the number is automorphic, false otherwise
     * @throws NumberFormatException if the string does not contain a parsable integer
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