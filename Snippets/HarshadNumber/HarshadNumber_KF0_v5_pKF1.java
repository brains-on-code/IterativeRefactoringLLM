package com.thealgorithms.maths;

// Wikipedia for Harshad Number : https://en.wikipedia.org/wiki/Harshad_number

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is a Harshad number.
     *
     * @param number the number to be checked
     * @return {@code true} if {@code number} is a Harshad number, otherwise {@code false}
     */
    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long remaining = number;
        long digitSum = 0;
        while (remaining > 0) {
            digitSum += remaining % 10;
            remaining /= 10;
        }

        return number % digitSum == 0;
    }

    /**
     * Checks if a number (given as a String) is a Harshad number.
     *
     * @param numberString the number in String form to be checked
     * @return {@code true} if the parsed number is a Harshad number, otherwise {@code false}
     */
    public static boolean isHarshad(String numberString) {
        final long number = Long.parseLong(numberString);
        if (number <= 0) {
            return false;
        }

        int digitSum = 0;
        for (char digitChar : numberString.toCharArray()) {
            digitSum += digitChar - '0';
        }

        return number % digitSum == 0;
    }
}