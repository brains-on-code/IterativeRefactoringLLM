package com.thealgorithms.maths;

/**
 * Utility methods for working with Harshad (Niven) numbers.
 *
 * <p>A Harshad number is a positive integer that is divisible by the sum of its digits.</p>
 */
public final class HarshadNumberUtils {

    private HarshadNumberUtils() {
        // Utility class; prevent instantiation.
    }

    /**
     * Returns whether the given value is a Harshad (Niven) number.
     *
     * @param number the value to test
     * @return {@code true} if {@code number} is positive and divisible by the sum of its digits;
     *         {@code false} otherwise
     */
    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(number);
        return number % digitSum == 0;
    }

    /**
     * Returns whether the given numeric string represents a Harshad (Niven) number.
     *
     * @param numberStr the numeric string to test
     * @return {@code true} if {@code numberStr} represents a positive number that is divisible
     *         by the sum of its digits; {@code false} otherwise
     * @throws NumberFormatException if {@code numberStr} is not a valid {@code long}
     */
    public static boolean isHarshad(String numberStr) {
        long number = Long.parseLong(numberStr);
        if (number <= 0) {
            return false;
        }

        long digitSum = sumOfDigits(number);
        return number % digitSum == 0;
    }

    /**
     * Returns the sum of the decimal digits of the given number.
     *
     * @param number the number whose digits will be summed
     * @return the sum of the digits of {@code number}
     */
    private static long sumOfDigits(long number) {
        long sum = 0;
        long remaining = Math.abs(number);

        while (remaining > 0) {
            sum += remaining % 10;
            remaining /= 10;
        }

        return sum;
    }
}