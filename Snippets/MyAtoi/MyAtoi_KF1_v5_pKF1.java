package com.thealgorithms.strings;

/**
 * Utility class for string to integer conversion.
 */
public final class StringToIntegerParser {

    private StringToIntegerParser() {
    }

    /**
     * Parses the given string into an integer, emulating behavior similar to atoi.
     * Handles:
     * - Leading/trailing whitespace
     * - Optional leading '+' or '-' sign
     * - Non-digit characters after the numeric portion
     * - Integer overflow and underflow
     *
     * @param input the string to parse
     * @return the parsed integer value, or clamped Integer.MIN_VALUE / Integer.MAX_VALUE on overflow
     */
    public static int parseStringToInt(String input) {
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

        // Check for optional sign
        char firstChar = trimmed.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            negative = firstChar == '-';
            index++;
        }

        int result = 0;
        while (index < length) {
            char ch = trimmed.charAt(index);
            if (!Character.isDigit(ch)) {
                break;
            }

            int digit = ch - '0';

            // Check for overflow/underflow before multiplying by 10 and adding digit
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return negative ? -result : result;
    }
}