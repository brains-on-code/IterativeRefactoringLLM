package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * Utility class for checking automorphic numbers.
 *
 * An automorphic number is a number whose square ends with the same digits
 * as the number itself (e.g., 5, 6, 25, 76, 376).
 */
public final class AutomorphicNumberChecker {

    private AutomorphicNumberChecker() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a non-negative long is automorphic using arithmetic operations.
     *
     * @param number the number to check
     * @return true if {@code number} is automorphic, false otherwise
     */
    public static boolean isAutomorphicByArithmetic(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        long digits = countDigits(number);

        long modulus = (long) Math.pow(10, digits);
        long suffix = square % modulus;

        return number == suffix;
    }

    /**
     * Counts the number of decimal digits in a non-negative long.
     *
     * @param number the number whose digits are to be counted
     * @return the number of digits
     */
    private static long countDigits(long number) {
        if (number == 0) {
            return 1;
        }

        long digits = 0;
        long temp = number;

        while (temp > 0) {
            digits++;
            temp /= 10;
        }

        return digits;
    }

    /**
     * Checks if a non-negative long is automorphic using string comparison.
     *
     * @param number the number to check
     * @return true if {@code number} is automorphic, false otherwise
     */
    public static boolean isAutomorphicByString(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        String numberStr = String.valueOf(number);
        String squareStr = String.valueOf(square);

        return squareStr.endsWith(numberStr);
    }

    /**
     * Checks if a non-negative integer (given as a string) is automorphic
     * using {@link BigInteger}.
     *
     * @param numberStr the number to check, as a string
     * @return true if the number is automorphic, false otherwise
     * @throws NumberFormatException if {@code numberStr} is not a valid integer
     */
    public static boolean isAutomorphicBigInteger(String numberStr) {
        BigInteger number = new BigInteger(numberStr);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger square = number.multiply(number);
        String numberAsString = number.toString();
        String squareAsString = square.toString();

        return squareAsString.endsWith(numberAsString);
    }
}