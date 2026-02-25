package com.thealgorithms.conversions;

/**
 * Utility class for converting strings representing numbers in an arbitrary base
 * (from 2 to 36, using digits 0–9 and uppercase letters A–Z) to their decimal
 * (base-10) representation.
 */
public final class BaseConverter {

    private static final int DIGIT_ZERO = '0';
    private static final int UPPER_A_OFFSET = 'A' - 10;

    private BaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a string representing a number in the given base to its decimal value.
     *
     * Valid characters:
     * <ul>
     *   <li>'0'–'9' → 0–9</li>
     *   <li>'A'–'Z' → 10–35</li>
     * </ul>
     *
     * @param value the string representation of the number
     * @param base  the base of the input number (between 2 and 36)
     * @return the decimal (base-10) value of the input string
     * @throws NumberFormatException if the string contains invalid characters
     *                               for the given base
     */
    public static int toDecimal(String value, int base) {
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
     * Converts a single character to its numeric value.
     *
     * @param ch the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a digit or
     *                               an uppercase letter
     */
    private static int charToDigit(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_ZERO;
        }
        if (Character.isUpperCase(ch)) {
            return ch - UPPER_A_OFFSET;
        }
        throw new NumberFormatException("invalid character: " + ch);
    }
}