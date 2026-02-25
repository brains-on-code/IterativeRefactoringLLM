package com.thealgorithms.conversions;

/**
 * Class for converting an Octal number to its Hexadecimal equivalent.
 *
 * @author Tanmay Joshi
 */
public final class OctalToHexadecimal {
    private static final int OCTAL_RADIX = 8;
    private static final int HEX_RADIX = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
    }

    /**
     * Converts an Octal number (as a string) to its Decimal equivalent.
     *
     * @param octalString The Octal number as a string
     * @return The Decimal equivalent of the Octal number
     * @throws IllegalArgumentException if the input contains invalid octal digits
     */
    public static int convertOctalToDecimal(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalValue = 0;
        for (int index = 0; index < octalString.length(); index++) {
            char octalChar = octalString.charAt(index);
            if (octalChar < '0' || octalChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + octalChar);
            }
            int octalDigit = octalChar - '0';
            decimalValue = decimalValue * OCTAL_RADIX + octalDigit;
        }

        return decimalValue;
    }

    /**
     * Converts a Decimal number to its Hexadecimal equivalent.
     *
     * @param decimalValue The Decimal number
     * @return The Hexadecimal equivalent of the Decimal number
     */
    public static String convertDecimalToHexadecimal(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder hexadecimalBuilder = new StringBuilder();
        int remainingValue = decimalValue;

        while (remainingValue > 0) {
            int hexDigitIndex = remainingValue % HEX_RADIX;
            hexadecimalBuilder.insert(0, HEX_DIGITS.charAt(hexDigitIndex));
            remainingValue /= HEX_RADIX;
        }

        return hexadecimalBuilder.toString();
    }
}