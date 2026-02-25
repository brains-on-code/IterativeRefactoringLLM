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

        String trimmedInput = input.trim();
        int inputLength = trimmedInput.length();
        if (inputLength == 0) {
            return 0;
        }

        int currentIndex = 0;
        boolean isNegative = false;

        // Check for optional sign
        char signChar = trimmedInput.charAt(currentIndex);
        if (signChar == '-' || signChar == '+') {
            isNegative = signChar == '-';
            currentIndex++;
        }

        int parsedValue = 0;
        while (currentIndex < inputLength) {
            char currentChar = trimmedInput.charAt(currentIndex);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digitValue = currentChar - '0';

            // Check for overflow/underflow before multiplying by 10 and adding digit
            if (parsedValue > (Integer.MAX_VALUE - digitValue) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            parsedValue = parsedValue * 10 + digitValue;
            currentIndex++;
        }

        return isNegative ? -parsedValue : parsedValue;
    }
}