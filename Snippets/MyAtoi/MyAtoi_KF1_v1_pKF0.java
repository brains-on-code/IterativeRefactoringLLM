package com.thealgorithms.strings;

/**
 * Utility class for string-related operations.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts the given string to an integer, emulating the behavior of atoi.
     * <p>
     * - Ignores leading and trailing whitespace.
     * - Supports an optional leading '+' or '-' sign.
     * - Stops parsing at the first non-digit character.
     * - Clamps the result to {@link Integer#MIN_VALUE} or {@link Integer#MAX_VALUE}
     *   if overflow or underflow occurs.
     *
     * @param input the string to parse
     * @return the parsed integer value, or 0 if the string is null, empty,
     *         or contains no parsable integer
     */
    public static int method1(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        input = input.trim();
        int length = input.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean isNegative = false;

        // Handle optional sign
        char firstChar = input.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            isNegative = (firstChar == '-');
            index++;
        }

        int result = 0;

        while (index < length) {
            char currentChar = input.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            // Check for overflow/underflow before multiplying by 10
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return isNegative ? -result : result;
    }
}