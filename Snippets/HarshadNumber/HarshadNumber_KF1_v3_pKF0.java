package com.thealgorithms.maths;

/**
 * Utility class for Harshad (Niven) number checks.
 *
 * A Harshad number is an integer that is divisible by the sum of its digits.
 */
public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if the given long value is a Harshad number.
     *
     * @param number the number to check
     * @return {@code true} if {@code number} is a Harshad number; {@code false} otherwise
     */
    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(number);
        return isDivisibleByDigitSum(number, digitSum);
    }

    /**
     * Checks if the given numeric string represents a Harshad number.
     *
     * @param numberString the numeric string to check
     * @return {@code true} if the parsed number is a Harshad number; {@code false} otherwise
     * @throws NumberFormatException if {@code numberString} is not a valid long
     */
    public static boolean isHarshad(String numberString) {
        long number = Long.parseLong(numberString);
        if (number <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(numberString);
        return isDivisibleByDigitSum(number, digitSum);
    }

    private static boolean isDivisibleByDigitSum(long number, long digitSum) {
        return digitSum != 0 && number % digitSum == 0;
    }

    private static long sumOfDigits(long number) {
        long digitSum = 0;

        while (number > 0) {
            digitSum += number % 10;
            number /= 10;
        }

        return digitSum;
    }

    private static long sumOfDigits(String numberString) {
        long digitSum = 0;

        for (int i = 0; i < numberString.length(); i++) {
            digitSum += numberString.charAt(i) - '0';
        }

        return digitSum;
    }
}