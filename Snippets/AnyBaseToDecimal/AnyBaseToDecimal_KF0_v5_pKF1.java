package com.thealgorithms.conversions;

/**
 * @author Varun Upadhyay (<a href="https://github.com/varunu28">...</a>)
 */
public final class AnyBaseToDecimal {
    private static final int ZERO_CHAR_CODE = '0';
    private static final int UPPERCASE_LETTER_BASE = 'A' - 10;

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
        int currentPlaceMultiplier = 1;

        for (int position = numberString.length() - 1; position >= 0; position--) {
            int digitValue = parseDigit(numberString.charAt(position));
            if (digitValue >= radix) {
                throw new NumberFormatException("For input string: " + numberString);
            }
            decimalValue += digitValue * currentPlaceMultiplier;
            currentPlaceMultiplier *= radix;
        }
        return decimalValue;
    }

    /**
     * Convert a character to its integer value.
     *
     * @param digitChar the character to be converted
     * @return the integer value represented by the character
     * @throws NumberFormatException if the character is not an uppercase letter or a digit
     */
    private static int parseDigit(char digitChar) {
        if (Character.isDigit(digitChar)) {
            return digitChar - ZERO_CHAR_CODE;
        } else if (Character.isUpperCase(digitChar)) {
            return digitChar - UPPERCASE_LETTER_BASE;
        } else {
            throw new NumberFormatException("invalid character: " + digitChar);
        }
    }
}