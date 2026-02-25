package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers from a given base to decimal.
 */
public final class BaseConverter {
    private static final int DIGIT_ZERO_CHAR_CODE = '0';
    private static final int UPPERCASE_A_CHAR_CODE_OFFSET = 'A' - 10;

    private BaseConverter() {
    }

    /**
     * Parses a string representing a number in the specified base and returns its
     * decimal (base-10) integer value.
     *
     * @param numberInBase the string representation of the number
     * @param base         the base of the input number (e.g., 2 for binary, 16 for hex)
     * @return the decimal integer value of the input number
     * @throws NumberFormatException if the string contains invalid characters for the base
     */
    public static int parseNumber(String numberInBase, int base) {
        int decimalValue = 0;
        int placeValue = 1;

        for (int index = numberInBase.length() - 1; index >= 0; index--) {
            int digitValue = toDigit(numberInBase.charAt(index));
            if (digitValue >= base) {
                throw new NumberFormatException("For input string: " + numberInBase);
            }
            decimalValue += digitValue * placeValue;
            placeValue *= base;
        }
        return decimalValue;
    }

    /**
     * Converts a single character to its corresponding digit value.
     * Supports '0'-'9' and 'A'-'Z'.
     *
     * @param ch the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a valid digit
     */
    private static int toDigit(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_ZERO_CHAR_CODE;
        } else if (Character.isUpperCase(ch)) {
            return ch - UPPERCASE_A_CHAR_CODE_OFFSET;
        } else {
            throw new NumberFormatException("Invalid character: " + ch);
        }
    }
}