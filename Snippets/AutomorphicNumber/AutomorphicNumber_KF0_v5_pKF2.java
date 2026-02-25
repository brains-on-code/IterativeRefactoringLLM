package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * <a href="https://en.wikipedia.org/wiki/Automorphic_number">Automorphic Number</a>
 *
 * <p>A number is automorphic if it appears as the last digit(s) of its own square.
 * Example: 25² = 625, and 25 is the last two digits of 625, so 25 is automorphic.
 */
public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a non-negative long value is an automorphic number using arithmetic.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is automorphic, otherwise {@code false}
     */
    public static boolean isAutomorphic(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;

        // Count decimal digits in n
        long digitCount = 0;
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
     * Checks if a non-negative long value is an automorphic number using string operations.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is automorphic, otherwise {@code false}
     */
    public static boolean isAutomorphic2(long n) {
        if (n < 0) {
            return false;
        }

        long square = n * n;
        String numberStr = Long.toString(n);
        String squareStr = Long.toString(square);

        return squareStr.endsWith(numberStr);
    }

    /**
     * Checks if a non-negative integer (provided as a string) is an automorphic number
     * using {@link BigInteger}.
     *
     * @param s the number to check, as a string
     * @return {@code true} if the parsed number is automorphic, otherwise {@code false}
     */
    public static boolean isAutomorphic3(String s) {
        BigInteger n = new BigInteger(s);

        if (n.signum() < 0) {
            return false;
        }

        BigInteger square = n.multiply(n);
        String numberStr = n.toString();
        String squareStr = square.toString();

        return squareStr.endsWith(numberStr);
    }
}