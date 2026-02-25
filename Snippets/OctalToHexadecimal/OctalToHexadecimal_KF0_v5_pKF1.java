package com.thealgorithms.conversions;

/**
 * Utility class for converting octal numbers to their hexadecimal equivalents.
 */
public final class OctalToHexadecimal {

    private static final int OCTAL_RADIX = 8;
    private static final int HEX_RADIX = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Prevent instantiation
    }

    /**
     * Converts an octal number (as a string) to its decimal equivalent.
     *
     * @param octalString the octal number as a string
     * @return the decimal equivalent of the octal number
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int convertOctalToDecimal(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalValue = 0;
        for (int index = 0; index < octalString.length(); index++) {
            char octalChar = octalString.charAt(index);
            if (octalChar < '0' || octalChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + octalChar);
            }
            int octalDigit = octalChar - '0';
            decimalValue = decimalValue * OCTAL_RADIX + octalDigit;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal number to its hexadecimal equivalent.
     *
     * @param decimalValue the decimal number
     * @return the hexadecimal equivalent of the decimal number
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