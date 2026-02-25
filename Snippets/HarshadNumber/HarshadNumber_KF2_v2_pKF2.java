package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Prevent instantiation of utility class
    }

    /**
     * Returns whether the given positive long value is a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param n the number to check
     * @return {@code true} if {@code n} is a Harshad number; {@code false} otherwise
     */
    public static boolean isHarshad(long n) {
        if (n <= 0) {
            return false;
        }

        long digitSum = 0;
        long remaining = n;

        while (remaining > 0) {
            digitSum += remaining % 10;
            remaining /= 10;
        }

        return n % digitSum == 0;
    }

    /**
     * Returns whether the given numeric string represents a Harshad number.
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

        int digitSum = 0;
        for (char digitChar : s.toCharArray()) {
            digitSum += digitChar - '0';
        }

        return n % digitSum == 0;
    }
}