package com.thealgorithms.conversions;

/**
 * Utility class for converting between octal and hexadecimal representations.
 */
public final class OctalToHexConverter {
    private static final int OCTAL_RADIX = 8;
    private static final int HEX_RADIX = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexConverter() {
    }

    /**
     * Converts an octal string to its decimal integer representation.
     *
     * @param octalString the octal string to convert
     * @return the decimal integer value of the octal string
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int octalToDecimal(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalValue = 0;
        for (int index = 0; index < octalString.length(); index++) {
            char octalDigitChar = octalString.charAt(index);
            if (octalDigitChar < '0' || octalDigitChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + octalDigitChar);
            }
            int octalDigitValue = octalDigitChar - '0';
            decimalValue = decimalValue * OCTAL_RADIX + octalDigitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its hexadecimal string representation.
     *
     * @param decimalValue the decimal integer to convert
     * @return the hexadecimal string representation of the decimal value
     */
    public static String decimalToHex(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder hexStringBuilder = new StringBuilder();
        int remainingValue = decimalValue;

        while (remainingValue > 0) {
            int hexDigitIndex = remainingValue % HEX_RADIX;
            hexStringBuilder.insert(0, HEX_DIGITS.charAt(hexDigitIndex));
            remainingValue /= HEX_RADIX;
        }

        return hexStringBuilder.toString();
    }
}