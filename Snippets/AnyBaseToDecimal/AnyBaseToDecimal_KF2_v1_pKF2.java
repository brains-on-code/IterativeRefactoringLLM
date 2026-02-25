package com.thealgorithms.conversions;

public final class AnyBaseToDecimal {

    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a string representation of a number in the given radix to its decimal value.
     *
     * @param input the string representation of the number
     * @param radix the base of the input number (e.g., 2 for binary, 16 for hexadecimal)
     * @return the decimal (base-10) integer value
     * @throws NumberFormatException if the input contains invalid characters for the given radix
     */
    public static int convertToDecimal(String input, int radix) {
        int result = 0;
        int positionalMultiplier = 1;

        for (int index = input.length() - 1; index >= 0; index--) {
            int digitValue = charToDigit(input.charAt(index));

            if (digitValue >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }

            result += digitValue * positionalMultiplier;
            positionalMultiplier *= radix;
        }

        return result;
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
            return character - DIGIT_CHAR_OFFSET;
        }

        if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        }

        throw new NumberFormatException("Invalid character: " + character);
    }
}