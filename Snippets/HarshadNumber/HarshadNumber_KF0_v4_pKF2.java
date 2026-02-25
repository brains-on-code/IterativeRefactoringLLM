package com.thealgorithms.maths;

/**
 * Utility methods for working with Harshad (Niven) numbers.
 *
 * <p>A Harshad number is an integer that is divisible by the sum of its digits.
 * See: https://en.wikipedia.org/wiki/Harshad_number
 */
public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given number is a Harshad number.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a Harshad number; {@code false} otherwise
     */
    public static boolean isHarshad(long n) {
        if (n <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(n);
        return n % digitSum == 0;
    }

    /**
     * Checks whether the given decimal string represents a Harshad number.
     *
     * @param s the string representation of the number to check
     * @return {@code true} if the parsed number is a Harshad number; {@code false} otherwise
     * @throws NumberFormatException if {@code s} cannot be parsed as a {@code long}
     */
    public static boolean isHarshad(String s) {
        long n = Long.parseLong(s);
        if (n <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(s);
        return n % digitSum == 0;
    }

    /**
     * Returns the sum of the decimal digits of a non-negative number.
     *
     * @param n the number whose digits are to be summed
     * @return the sum of the digits of {@code n}
     */
    private static long sumOfDigits(long n) {
        long sum = 0;
        long remaining = n;

        while (remaining > 0) {
            sum += remaining % 10;
            remaining /= 10;
        }

        return sum;
    }

    /**
     * Returns the sum of the decimal digits in a numeric string.
     *
     * <p>Assumes {@code s} contains only digit characters.
     *
     * @param s the string whose digit characters are to be summed
     * @return the sum of the digits represented by {@code s}
     */
    private static long sumOfDigits(String s) {
        long sum = 0;
        for (char ch : s.toCharArray()) {
            sum += ch - '0';
        }
        return sum;
    }
}