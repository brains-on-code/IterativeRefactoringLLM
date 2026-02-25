package com.thealgorithms.maths;

/**
 * Utility class for Harshad (Niven) number checks.
 *
 * A Harshad number is an integer that is divisible by the sum of its digits.
 */
public final class HarshadNumberUtils {

    private HarshadNumberUtils() {
        // Prevent instantiation
    }

    /**
     * Checks whether the given long value is a Harshad (Niven) number.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is positive and divisible by the sum of its digits;
     *         {@code false} otherwise
     */
    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long temp = number;
        long digitSum = 0;

        while (temp > 0) {
            digitSum += temp % 10;
            temp /= 10;
        }

        return number % digitSum == 0;
    }

    /**
     * Checks whether the given numeric string represents a Harshad (Niven) number.
     *
     * @param numberStr the numeric string to check
     * @return {@code true} if {@code numberStr} represents a positive number that is divisible
     *         by the sum of its digits; {@code false} otherwise
     * @throws NumberFormatException if {@code numberStr} is not a valid long
     */
    public static boolean isHarshad(String numberStr) {
        final long number = Long.parseLong(numberStr);
        if (number <= 0) {
            return false;
        }

        int digitSum = 0;
        for (char ch : numberStr.toCharArray()) {
            digitSum += ch - '0';
        }

        return number % digitSum == 0;
    }
}