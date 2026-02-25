package com.thealgorithms.conversions;

/**
 * Utility class for converting strings in a given base to integers.
 */
public final class BaseConverter {

    private static final int DIGIT_OFFSET = '0';
    private static final int UPPERCASE_LETTER_OFFSET = 'A' - 10;
    private static final String INPUT_PREFIX = "For input string: ";

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
        validateInput(value, base);

        int result = 0;
        int multiplier = 1;

        for (int index = value.length() - 1; index >= 0; index--) {
            int digit = charToDigit(value.charAt(index));
            if (digit >= base) {
                throw new NumberFormatException(INPUT_PREFIX + value);
            }
            result += digit * multiplier;
            multiplier *= base;
        }

        return result;
    }

    private static void validateInput(String value, int base) {
        if (value == null || value.isEmpty()) {
            throw new NumberFormatException("Input string must not be null or empty");
        }
        if (base < 2 || base > 36) {
            throw new IllegalArgumentException("Base must be in the range [2, 36], but was: " + base);
        }
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
        }
        if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_LETTER_OFFSET;
        }
        throw new NumberFormatException("Invalid character: " + ch);
    }
}