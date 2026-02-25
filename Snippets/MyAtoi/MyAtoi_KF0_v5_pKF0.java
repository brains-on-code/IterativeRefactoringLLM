package com.thealgorithms.strings;

/**
 * A utility class that provides a method to convert a string to a 32-bit signed integer
 * (similar to C/C++'s atoi function).
 */
public final class MyAtoi {

    private MyAtoi() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts the given string to a 32-bit signed integer.
     *
     * @param s the string to convert
     * @return the converted integer, or 0 if the string cannot be converted to a valid integer
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
        int limit = isNegative ? Integer.MIN_VALUE : -Integer.MAX_VALUE;
        int multMin = limit / 10;

        while (index < length) {
            char currentChar = trimmed.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            if (result < multMin) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            int nextResult = result * 10;
            int minAllowed = limit + digit;
            if (nextResult < minAllowed) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = nextResult - digit;
            index++;
        }

        return isNegative ? result : -result;
    }
}