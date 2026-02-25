package com.thealgorithms.strings;

/**
 * A utility class that provides a method to convert a string to a 32-bit signed integer (similar to C/C++'s atoi function).
 */
public final class MyAtoi {

    private MyAtoi() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts the given string to a 32-bit signed integer.
     * The conversion discards any leading whitespace characters until the first non-whitespace character is found.
     * Then, it takes an optional initial plus or minus sign followed by as many numerical digits as possible and interprets them as a numerical value.
     * The string can contain additional characters after those that form the integral number, which are ignored and have no effect on the behavior of this function.
     *
     * If the number is out of the range of a 32-bit signed integer:
     * - Returns {@code Integer.MAX_VALUE} if the value exceeds {@code Integer.MAX_VALUE}.
     * - Returns {@code Integer.MIN_VALUE} if the value is less than {@code Integer.MIN_VALUE}.
     *
     * If no valid conversion could be performed, a zero is returned.
     *
     * @param input the string to convert
     * @return the converted integer, or 0 if the string cannot be converted to a valid integer
     */
    public static int myAtoi(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String trimmed = input.trim();
        int length = trimmed.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean negative = false;

        char signChar = trimmed.charAt(index);
        if (signChar == '-' || signChar == '+') {
            negative = signChar == '-';
            index++;
        }

        int value = 0;
        while (index < length) {
            char currentChar = trimmed.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            if (value > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            value = value * 10 + digit;
            index++;
        }

        return negative ? -value : value;
    }
}