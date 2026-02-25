package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a given positive long number is a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param n the number to check
     * @return true if n is a Harshad number, false otherwise
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
     * Checks if a given numeric string represents a Harshad number.
     * A Harshad number is divisible by the sum of its digits.
     *
     * @param s the numeric string to check
     * @return true if the parsed number is a Harshad number, false otherwise
     * @throws NumberFormatException if the string cannot be parsed as a long
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