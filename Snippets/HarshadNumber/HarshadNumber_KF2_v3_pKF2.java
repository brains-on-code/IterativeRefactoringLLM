package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given positive long value is a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
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
     * Checks if the given numeric string represents a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param s the numeric string to check
     * @return {@code true} if the parsed number is a Harshad number; {@code false} otherwise
     * @throws NumberFormatException if {@code s} cannot be parsed as a {@code long}
     */
    public static boolean isHarshad(String s) {
        long n = Long.parseLong(s);
        if (n <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(n);
        return n % digitSum == 0;
    }

    /**
     * Returns the sum of the digits of the given non-negative long value.
     *
     * @param n the number whose digits will be summed
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
}