package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * Utility class for checking Automorphic numbers.
 *
 * <p>An Automorphic number is a number whose square ends with the same digits
 * as the number itself (e.g., 5, 6, 25, 76, 376).
 */
public final class AutomorphicNumberChecker {

    private AutomorphicNumberChecker() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a non-negative {@code long} value is an Automorphic number using
     * arithmetic operations.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is Automorphic, {@code false} otherwise
     */
    public static boolean isAutomorphicArithmetic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;

        long digits = countDigits(n);
        long modulus = (long) Math.pow(10, digits);
        long suffix = square % modulus;

        return n == suffix;
    }

    /**
     * Counts the number of decimal digits in a non-negative {@code long}.
     *
     * @param n the number whose digits are to be counted
     * @return the number of digits in {@code n}
     */
    private static long countDigits(long n) {
        if (n == 0) {
            return 1;
        }

        long digits = 0;
        long temp = n;
        while (temp > 0) {
            digits++;
            temp /= 10;
        }
        return digits;
    }

    /**
     * Checks if a non-negative {@code long} value is an Automorphic number using
     * string operations.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is Automorphic, {@code false} otherwise
     */
    public static boolean isAutomorphicString(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        return String.valueOf(square).endsWith(String.valueOf(n));
    }

    /**
     * Checks if a non-negative integer represented as a string is an
     * Automorphic number using {@link BigInteger}.
     *
     * @param value the number to check, as a string
     * @return {@code true} if {@code value} is Automorphic, {@code false} otherwise
     * @throws NumberFormatException if {@code value} is not a valid integer
     */
    public static boolean isAutomorphicBigInteger(String value) {
        BigInteger n = new BigInteger(value);

        if (n.signum() < 0) {
            return false;
        }

        BigInteger square = n.multiply(n);
        return square.toString().endsWith(n.toString());
    }
}