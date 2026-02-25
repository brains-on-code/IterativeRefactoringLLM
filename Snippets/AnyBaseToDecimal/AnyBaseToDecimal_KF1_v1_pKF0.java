package com.thealgorithms.conversions;

/**
 * Utility class for converting strings in a given base to integers.
 */
public final class BaseConverter {

    private static final int DIGIT_OFFSET = '0';
    private static final int UPPERCASE_LETTER_OFFSET = 'A' - 10;

    private BaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a string representation of a number in the specified base to an integer.
     *
     * @param value the string representation of the number
     * @param base  the base of the number system (e.g., 2 for binary, 16 for hexadecimal)
     * @return the integer value of the given string in the specified base
     * @throws NumberFormatException if the string contains invalid characters for the base
     */
    public static int parseInt(String value, int base) {
        int result = 0;
        int multiplier = 1;

        for (int i = value.length() - 1; i >= 0; i--) {
            int digit = charToDigit(value.charAt(i));
            if (digit >= base) {
                throw new NumberFormatException("For input string: " + value);
            }
            result += digit * multiplier;
            multiplier *= base;
        }
        return result;
    }

    /**
     * Converts a single character to its numeric digit value.
     *
     * @param ch the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a valid digit or uppercase letter
     */
    private static int charToDigit(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_OFFSET;
        } else if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_LETTER_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + ch);
        }
    }
}