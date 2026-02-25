package com.thealgorithms.strings;

/**
 * Utility class that converts a string to a 32-bit signed integer
 * (similar to C/C++'s atoi function).
 */
public final class MyAtoi {

    private MyAtoi() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts the given string to a 32-bit signed integer.
     *
     * Rules:
     * <ul>
     *   <li>Ignores leading whitespace.</li>
     *   <li>Accepts an optional '+' or '-' sign.</li>
     *   <li>Parses consecutive digits until a non-digit is encountered.</li>
     *   <li>Ignores any remaining characters after the parsed number.</li>
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
        boolean negative = false;

        // Handle optional sign character
        char firstChar = trimmed.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            negative = (firstChar == '-');
            index++;
        }

        int number = 0;
        while (index < length) {
            char ch = trimmed.charAt(index);
            if (!Character.isDigit(ch)) {
                break;
            }

            int digit = ch - '0';

            // Check for overflow before multiplying and adding
            if (number > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            number = number * 10 + digit;
            index++;
        }

        return negative ? -number : number;
    }
}