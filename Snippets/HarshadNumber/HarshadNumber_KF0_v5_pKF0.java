package com.thealgorithms.maths;

/**
 * Utility class for checking Harshad (Niven) numbers.
 * A Harshad number is an integer that is divisible by the sum of its digits.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Harshad_number">Harshad number - Wikipedia</a>
 */
public final class HarshadNumber {

    private HarshadNumber() {
        // Prevent instantiation
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
        long digitSum = sumDigits(number);
        return digitSum != 0 && number % digitSum == 0;
    }

    /**
     * Checks if a number (given as a String) is a Harshad number.
     *
     * @param value the number in String form to be checked
     * @return {@code true} if the parsed number is a Harshad number, otherwise {@code false}
     * @throws NumberFormatException if the string does not contain a parsable long
     */
    public static boolean isHarshad(String value) {
        return isHarshad(Long.parseLong(value));
    }

    private static long sumDigits(long number) {
        long sum = 0;
        long remaining = Math.abs(number);

        while (remaining > 0) {
            sum += remaining % 10;
            remaining /= 10;
        }

        return sum;
    }
}