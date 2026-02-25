package com.thealgorithms.strings;

/**
 * Utility class that converts a string to a 32-bit signed integer
 * (similar to C/C++'s atoi function).
 */
public final class MyAtoi {

    private MyAtoi() {
        // Prevent instantiation
    }

    /**
     * Converts the given string to a 32-bit signed integer.
     *
     * Behavior:
     * <ul>
     *   <li>Skips leading and trailing whitespace.</li>
     *   <li>Reads an optional '+' or '-' sign.</li>
     *   <li>Parses consecutive digits until a non-digit is found.</li>
     *   <li>Ignores any remaining characters after the digits.</li>
     *   <li>Clamps to {@code Integer.MAX_VALUE} or {@code Integer.MIN_VALUE}
     *       on overflow.</li>
     *   <li>Returns 0 if no valid conversion can be performed.</li>
     * </ul>
     *
     * @param s the string to convert
     * @return the converted integer, or 0 if the string cannot be converted
     */
    public static int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        String trimmed = s.trim();
        int length = trimmed.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean isNegative = false;

        char firstChar = trimmed.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            isNegative = (firstChar == '-');
            index++;
        }

        int result = 0;

        while (index < length) {
            char currentChar = trimmed.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return isNegative ? -result : result;
    }
}