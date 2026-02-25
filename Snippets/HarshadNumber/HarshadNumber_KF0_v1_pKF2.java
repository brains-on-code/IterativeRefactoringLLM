package com.thealgorithms.maths;

/**
 * Utility class for checking Harshad (Niven) numbers.
 *
 * <p>A Harshad number is an integer that is divisible by the sum of its digits.
 * See: https://en.wikipedia.org/wiki/Harshad_number
 */
public final class HarshadNumber {

    private HarshadNumber() {
        // Prevent instantiation
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

        long temp = n;
        long digitSum = 0;

        while (temp > 0) {
            digitSum += temp % 10;
            temp /= 10;
        }

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

        int digitSum = 0;
        for (char ch : s.toCharArray()) {
            digitSum += ch - '0';
        }

        return n % digitSum == 0;
    }
}