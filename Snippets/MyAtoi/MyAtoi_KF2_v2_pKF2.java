package com.thealgorithms.strings;

public final class MyAtoi {

    private MyAtoi() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a string to a 32-bit signed integer (similar to C/C++'s atoi).
     * <p>
     * Behavior:
     * <ul>
     *     <li>Ignores leading and trailing whitespace</li>
     *     <li>Supports an optional leading '+' or '-' sign</li>
     *     <li>Parses consecutive digits until a non-digit character is found</li>
     *     <li>On overflow, returns {@link Integer#MIN_VALUE} or {@link Integer#MAX_VALUE}</li>
     *     <li>Returns 0 if no valid conversion can be performed</li>
     * </ul>
     *
     * @param s the input string
     * @return the parsed integer, or 0 if no valid conversion can be performed
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

        // Parse optional sign
        char firstChar = trimmed.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            negative = (firstChar == '-');
            index++;
        }

        int number = 0;

        // Parse digits and build the number
        while (index < length) {
            char currentChar = trimmed.charAt(index);

            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            // Detect overflow before applying the next digit
            if (number > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            number = number * 10 + digit;
            index++;
        }

        return negative ? -number : number;
    }
}