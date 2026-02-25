package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
    }

    /**
     * Checks if a given long number is a Harshad (Niven) number.
     *
     * @param number the number to check
     * @return true if the number is a Harshad number, false otherwise
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
     * Checks if a given numeric string represents a Harshad (Niven) number.
     *
     * @param numberString the numeric string to check
     * @return true if the string represents a Harshad number, false otherwise
     * @throws NumberFormatException if the string cannot be parsed as a long
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