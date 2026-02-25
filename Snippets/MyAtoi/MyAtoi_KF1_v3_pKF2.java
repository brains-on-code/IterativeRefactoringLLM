package com.thealgorithms.strings;

/**
 * Utility class for parsing integers from strings.
 */
public final class StringToIntParser {

    private StringToIntParser() {
        // Prevent instantiation
    }

    /**
     * Parses the given string into an integer, emulating the behavior of
     * {@link Integer#parseInt(String)} but with explicit overflow handling.
     *
     * <ul>
     *   <li>Leading and trailing whitespace is ignored.</li>
     *   <li>An optional leading '+' or '-' sign is supported.</li>
     *   <li>Parsing stops at the first non-digit character.</li>
     *   <li>If the parsed value overflows, {@link Integer#MAX_VALUE} or
     *       {@link Integer#MIN_VALUE} is returned.</li>
     *   <li>If the string is null, empty, or contains no digits, 0 is returned.</li>
     * </ul>
     *
     * @param input the string to parse
     * @return the parsed integer value, clamped to the integer range on overflow
     */
    public static int parseIntSafely(String input) {
        if (input == null) {
            return 0;
        }

        String trimmed = input.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }

        int length = trimmed.length();
        int index = 0;
        boolean isNegative = false;

        char firstChar = trimmed.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            isNegative = (firstChar == '-');
            index++;
        }

        int result = 0;
        boolean hasDigits = false;

        while (index < length) {
            char currentChar = trimmed.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            hasDigits = true;
            int digit = currentChar - '0';

            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        if (!hasDigits) {
            return 0;
        }

        return isNegative ? -result : result;
    }
}