package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks whether the given positive number is a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param n the number to test; must be positive
     * @return {@code true} if {@code n} is a Harshad number; {@code false} otherwise
     */
    public static boolean isHarshad(long n) {
        return n > 0 && isHarshadInternal(n);
    }

    /**
     * Checks whether the given numeric string represents a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param s the numeric string to test; must represent a positive {@code long}
     * @return {@code true} if the parsed number is a Harshad number; {@code false} otherwise
     * @throws NumberFormatException if {@code s} cannot be parsed as a {@code long}
     */
    public static boolean isHarshad(String s) {
        long n = Long.parseLong(s);
        return n > 0 && isHarshadInternal(n);
    }

    /**
     * Core Harshad check assuming {@code n} is positive.
     */
    private static boolean isHarshadInternal(long n) {
        long digitSum = sumOfDigits(n);
        return n % digitSum == 0;
    }

    /**
     * Computes the sum of the digits of the given non-negative number.
     *
     * @param n the number whose digits will be summed
     * @return the sum of the digits of {@code n}
     */
    private static long sumOfDigits(long n) {
        long sum = 0;

        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }

        return sum;
    }
}