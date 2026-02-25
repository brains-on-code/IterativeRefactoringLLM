package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * Utility class for checking automorphic numbers.
 *
 * An automorphic number is a number whose square ends with the same digits
 * as the number itself (e.g., 5, 6, 25, 76, 376, 625, ...).
 */
public final class AutomorphicNumber {

    private AutomorphicNumber() {}

    /**
     * Checks if a non-negative long value is an automorphic number using
     * arithmetic operations.
     *
     * @param number the number to check
     * @return true if the number is automorphic, false otherwise
     */
    public static boolean isAutomorphicByMath(long number) {
        if (number < 0) {
            return false;
        }

        long squaredNumber = number * number;
        long tempNumber = number;
        long digitCount = 0;

        while (tempNumber > 0) {
            digitCount++;
            tempNumber /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long squaredSuffix = squaredNumber % modulus;

        return number == squaredSuffix;
    }

    /**
     * Checks if a non-negative long value is an automorphic number using
     * string operations.
     *
     * @param number the number to check
     * @return true if the number is automorphic, false otherwise
     */
    public static boolean isAutomorphicByString(long number) {
        if (number < 0) {
            return false;
        }

        long squaredNumber = number * number;
        String squaredNumberString = String.valueOf(squaredNumber);
        String numberString = String.valueOf(number);

        return squaredNumberString.endsWith(numberString);
    }

    /**
     * Checks if a non-negative integer represented as a string is an
     * automorphic number using BigInteger and string operations.
     *
     * @param numberString the number to check, as a string
     * @return true if the number is automorphic, false otherwise
     */
    public static boolean isAutomorphicBigInteger(String numberString) {
        BigInteger number = new BigInteger(numberString);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger squaredNumber = number.multiply(number);
        String squaredNumberString = squaredNumber.toString();
        String originalNumberString = number.toString();

        return squaredNumberString.endsWith(originalNumberString);
    }
}