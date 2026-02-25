package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers from any base (radix) to decimal.
 */
public final class AnyBaseToDecimal {

    /** ASCII offset for numeric characters ('0'–'9'). */
    private static final int CHAR_OFFSET_FOR_DIGIT = '0';

    /** ASCII offset for uppercase alphabetic characters ('A'–'Z'), representing 10–35. */
    private static final int CHAR_OFFSET_FOR_UPPERCASE = 'A' - 10;

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
        int power = 1;

        for (int i = input.length() - 1; i >= 0; i--) {
            int digit = valueOfChar(input.charAt(i));
            if (digit >= radix) {
                throw new NumberFormatException("For input string: " + input);
            }
            result += digit * power;
            power *= radix;
        }
        return result;
    }

    /**
     * Returns the numeric value of a single character digit.
     * <p>
     * Supported characters:
     * <ul>
     *   <li>'0'–'9' → 0–9</li>
     *   <li>'A'–'Z' → 10–35</li>
     * </ul>
     *
     * @param character the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a digit or an uppercase letter
     */
    private static int valueOfChar(char character) {
        if (Character.isDigit(character)) {
            return character - CHAR_OFFSET_FOR_DIGIT;
        }
        if (Character.isUpperCase(character)) {
            return character - CHAR_OFFSET_FOR_UPPERCASE;
        }
        throw new NumberFormatException("Invalid character: " + character);
    }
}