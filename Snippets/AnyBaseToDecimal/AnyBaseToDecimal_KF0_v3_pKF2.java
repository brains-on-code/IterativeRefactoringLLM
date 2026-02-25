package com.thealgorithms.conversions;

/**
 * Utility for converting numbers from an arbitrary base (radix) to decimal.
 */
public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Prevent instantiation
    }

    /**
     * Converts a string representation of a number in the given radix to its decimal value.
     *
     * @param input the string representation of the number
     * @param radix the base of the input number (e.g., 2 for binary, 16 for hexadecimal)
     * @return the decimal (base-10) value of the input
     * @throws NumberFormatException if the input contains invalid characters for the given radix
     */
    public static int convertToDecimal(String input, int radix) {
        int result = 0;
        int positionalMultiplier = 1;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = toDigit(input.charAt(i));
            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }
            result += digit * positionalMultiplier;
            positionalMultiplier *= radix;
        }
        return result;
    }

    /**
     * Converts a single character to its numeric digit value.
     * Supported characters:
     * '0'–'9' → 0–9
     * 'A'–'Z' → 10–35
     *
     * @param character the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a supported digit
     */
    private static int toDigit(char character) {
        if (Character.isDigit(character)) {
            return character - DIGIT_CHAR_OFFSET;
        }
        if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        }
        throw new NumberFormatException("Invalid character: " + character);
    }
}