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
        if (input == null) {
            return 0;
        }

        String trimmedInput = input.trim();
        if (trimmedInput.isEmpty()) {
            return 0;
        }

        int index = 0;
        int length = trimmedInput.length();
        boolean isNegative = false;

        char firstChar = trimmedInput.charAt(index);
        if (isSign(firstChar)) {
            isNegative = (firstChar == '-');
            index++;
        }

        int result = 0;

        while (index < length) {
            char currentChar = trimmedInput.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            if (willOverflow(result, digit)) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return isNegative ? -result : result;
    }

    private static boolean isSign(char ch) {
        return ch == '-' || ch == '+';
    }

    private static boolean willOverflow(int currentValue, int nextDigit) {
        return currentValue > (Integer.MAX_VALUE - nextDigit) / 10;
    }
}