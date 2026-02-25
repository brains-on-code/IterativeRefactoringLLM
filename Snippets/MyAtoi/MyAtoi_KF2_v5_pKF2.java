package com.thealgorithms.strings;

public final class MyAtoi {

    private MyAtoi() {
        // Prevent instantiation of utility class
    }

    /**
     * Converts a string to a 32-bit signed integer (similar to C/C++'s atoi).
     *
     * <p>Behavior:
     * <ul>
     *     <li>Ignores leading and trailing whitespace</li>
     *     <li>Accepts an optional leading '+' or '-' sign</li>
     *     <li>Parses consecutive digits until a non-digit character is encountered</li>
     *     <li>On overflow, returns {@link Integer#MIN_VALUE} or {@link Integer#MAX_VALUE}</li>
     *     <li>Returns 0 if no valid conversion can be performed</li>
     * </ul>
     *
     * @param s the input string
     * @return the parsed integer, or 0 if no valid conversion can be performed
     */
    public static int myAtoi(String s) {
        if (s == null) {
            return 0;
        }

        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }

        int index = 0;
        int length = trimmed.length();
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