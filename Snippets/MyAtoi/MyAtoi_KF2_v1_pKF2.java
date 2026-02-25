package com.thealgorithms.strings;

public final class MyAtoi {

    private MyAtoi() {
        // Prevent instantiation
    }

    /**
     * Converts a string to a 32-bit signed integer (similar to C/C++'s atoi).
     * Rules:
     * - Discards leading whitespace
     * - Optional '+' or '-' sign
     * - Reads digits until a non-digit is found
     * - Clamps result to Integer.MIN_VALUE or Integer.MAX_VALUE on overflow
     *
     * @param s the input string
     * @return the parsed integer, or 0 if no valid conversion can be performed
     */
    public static int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        // Remove leading and trailing whitespace
        s = s.trim();
        int length = s.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean negative = false;

        // Handle optional sign
        char firstChar = s.charAt(index);
        if (firstChar == '-' || firstChar == '+') {
            negative = (firstChar == '-');
            index++;
        }

        int number = 0;

        // Process numeric characters
        while (index < length) {
            char ch = s.charAt(index);

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