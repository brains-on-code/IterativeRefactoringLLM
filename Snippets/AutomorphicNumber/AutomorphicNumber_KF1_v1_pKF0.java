package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * Utility class for checking automorphic numbers.
 *
 * An automorphic number is a number whose square ends with the same digits
 * as the number itself (e.g., 5, 6, 25, 76, 376).
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a non-negative long is automorphic using arithmetic operations.
     *
     * @param number the number to check
     * @return true if {@code number} is automorphic, false otherwise
     */
    public static boolean method1(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        long temp = number;
        long digits = 0;

        while (temp > 0) {
            digits++;
            temp /= 10;
        }

        long modulus = (long) Math.pow(10, digits);
        long suffix = square % modulus;

        return number == suffix;
    }

    /**
     * Checks if a non-negative long is automorphic using string comparison.
     *
     * @param number the number to check
     * @return true if {@code number} is automorphic, false otherwise
     */
    public static boolean method2(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        return String.valueOf(square).endsWith(String.valueOf(number));
    }

    /**
     * Checks if a non-negative integer (given as a string) is automorphic
     * using {@link BigInteger}.
     *
     * @param numberStr the number to check, as a string
     * @return true if the number is automorphic, false otherwise
     * @throws NumberFormatException if {@code numberStr} is not a valid integer
     */
    public static boolean method3(String numberStr) {
        BigInteger number = new BigInteger(numberStr);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger square = number.multiply(number);
        return square.toString().endsWith(number.toString());
    }
}