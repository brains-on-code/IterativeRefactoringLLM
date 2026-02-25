package com.thealgorithms.strings;

/**
 * Utility class for parsing integers from strings.
 */
public final class Class1 {

    private Class1() {
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

        // Handle optional sign character
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

            // Check for overflow before multiplying and adding
            if (result > (Integer.MAX_VALUE - digit) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return isNegative ? -result : result;
    }
}