package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers from a given base to decimal.
 */
public final class BaseConverter {
    private static final int DIGIT_OFFSET = '0';
    private static final int UPPERCASE_LETTER_OFFSET = 'A' - 10;

    private BaseConverter() {
    }

    /**
     * Parses a string representing a number in the specified base and returns its
     * decimal (base-10) integer value.
     *
     * @param number the string representation of the number
     * @param base   the base of the input number (e.g., 2 for binary, 16 for hex)
     * @return the decimal integer value of the input number
     * @throws NumberFormatException if the string contains invalid characters for the base
     */
    public static int parseNumber(String number, int base) {
        int decimalValue = 0;
        int currentPlaceValue = 1;

        for (int index = number.length() - 1; index >= 0; index--) {
            int digitValue = charToDigit(number.charAt(index));
            if (digitValue >= base) {
                throw new NumberFormatException("For input string: " + number);
            }
            decimalValue += digitValue * currentPlaceValue;
            currentPlaceValue *= base;
        }
        return decimalValue;
    }

    /**
     * Converts a single character to its corresponding digit value.
     * Supports '0'-'9' and 'A'-'Z'.
     *
     * @param character the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a valid digit
     */
    private static int charToDigit(char character) {
        if (Character.isDigit(character)) {
            return character - DIGIT_OFFSET;
        } else if (Character.isUpperCase(character)) {
            return character - UPPERCASE_LETTER_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + character);
        }
    }
}