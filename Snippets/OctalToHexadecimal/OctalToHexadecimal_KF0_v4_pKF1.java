package com.thealgorithms.conversions;

/**
 * Utility class for converting octal numbers to their hexadecimal equivalents.
 */
public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Prevent instantiation
    }

    /**
     * Converts an octal number (as a string) to its decimal equivalent.
     *
     * @param octal the octal number as a string
     * @return the decimal equivalent of the octal number
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int convertOctalToDecimal(String octal) {
        if (octal == null || octal.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimal = 0;
        for (int position = 0; position < octal.length(); position++) {
            char octalChar = octal.charAt(position);
            if (octalChar < '0' || octalChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + octalChar);
            }
            int octalDigit = octalChar - '0';
            decimal = decimal * OCTAL_BASE + octalDigit;
        }

        return decimal;
    }

    /**
     * Converts a decimal number to its hexadecimal equivalent.
     *
     * @param decimal the decimal number
     * @return the hexadecimal equivalent of the decimal number
     */
    public static String convertDecimalToHexadecimal(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder hexadecimal = new StringBuilder();
        int remaining = decimal;

        while (remaining > 0) {
            int hexDigitIndex = remaining % HEX_BASE;
            hexadecimal.insert(0, HEX_DIGITS.charAt(hexDigitIndex));
            remaining /= HEX_BASE;
        }

        return hexadecimal.toString();
    }
}