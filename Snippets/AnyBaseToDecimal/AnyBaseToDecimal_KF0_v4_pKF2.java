package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers from an arbitrary base (radix) to decimal.
 */
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
     * @return the decimal (base-10) value of the input
     * @throws NumberFormatException if the input is null, empty, has an invalid radix,
     *                               or contains invalid characters for the given radix
     */
    public static int convertToDecimal(String input, int radix) {
        validateInput(input, radix);

        int decimalValue = 0;
        int positionalMultiplier = 1;

        for (int index = input.length() - 1; index >= 0; index--) {
            int digit = toDigit(input.charAt(index));
            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }
            decimalValue += digit * positionalMultiplier;
            positionalMultiplier *= radix;
        }

        return decimalValue;
    }

    private static void validateInput(String input, int radix) {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Input string must not be null or empty");
        }
        if (radix < Character.MIN_RADIX || radix > Character.MAX_RADIX) {
            throw new NumberFormatException("Invalid radix: " + radix);
        }
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