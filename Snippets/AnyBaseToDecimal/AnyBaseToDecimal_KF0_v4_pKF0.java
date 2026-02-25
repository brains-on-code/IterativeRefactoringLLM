package com.thealgorithms.conversions;

/**
 * @author Varun Upadhyay (<a href="https://github.com/varunu28">...</a>)
 */
public final class AnyBaseToDecimal {

    private static final int MIN_RADIX = 2;
    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
        // Utility class; prevent instantiation
    }

    /**
     * Convert any radix to a decimal number.
     *
     * @param input the string to be converted
     * @param radix the radix (base) of the input string
     * @return the decimal equivalent of the input string
     * @throws NumberFormatException if the input string or radix is invalid
     */
    public static int convertToDecimal(String input, int radix) {
        validateInput(input, radix);

        int result = 0;
        int power = 1;

        for (int index = input.length() - 1; index >= 0; index--) {
            int digit = charToDigit(input.charAt(index), input);

            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }

            result += digit * power;
            power *= radix;
        }

        return result;
    }

    private static void validateInput(String input, int radix) {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Input string must not be null or empty");
        }
        if (radix < MIN_RADIX) {
            throw new NumberFormatException("Radix must be at least " + MIN_RADIX);
        }
    }

    /**
     * Convert a character to its integer value.
     *
     * @param character the character to be converted
     * @param input     the original input string (for error context)
     * @return the integer value represented by the character
     * @throws NumberFormatException if the character is not an uppercase letter or a digit
     */
    private static int charToDigit(char character, String input) {
        if (Character.isDigit(character)) {
            return character - DIGIT_CHAR_OFFSET;
        }
        if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        }
        throw new NumberFormatException("Invalid character '" + character + "' in input string: " + input);
    }
}