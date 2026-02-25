package com.thealgorithms.conversions;

/**
 * @author Varun Upadhyay (<a href="https://github.com/varunu28">...</a>)
 */
public final class AnyBaseToDecimal {
    private static final int DIGIT_CHAR_OFFSET = '0';
    private static final int UPPERCASE_CHAR_OFFSET = 'A' - 10;

    private AnyBaseToDecimal() {
    }

    /**
     * Convert any radix to a decimal number.
     *
     * @param numberString the string to be converted
     * @param radix the radix (base) of the input string
     * @return the decimal equivalent of the input string
     * @throws NumberFormatException if the input string or radix is invalid
     */
    public static int convertToDecimal(String numberString, int radix) {
        int decimalValue = 0;
        int positionalMultiplier = 1;

        for (int index = numberString.length() - 1; index >= 0; index--) {
            int digitValue = toDigitValue(numberString.charAt(index));
            if (digitValue >= radix) {
                throw new NumberFormatException("For input string: " + numberString);
            }
            decimalValue += digitValue * positionalMultiplier;
            positionalMultiplier *= radix;
        }
        return decimalValue;
    }

    /**
     * Convert a character to its integer value.
     *
     * @param character the character to be converted
     * @return the integer value represented by the character
     * @throws NumberFormatException if the character is not an uppercase letter or a digit
     */
    private static int toDigitValue(char character) {
        if (Character.isDigit(character)) {
            return character - DIGIT_CHAR_OFFSET;
        } else if (Character.isUpperCase(character)) {
            return character - UPPERCASE_CHAR_OFFSET;
        } else {
            throw new NumberFormatException("invalid character: " + character);
        }
    }
}