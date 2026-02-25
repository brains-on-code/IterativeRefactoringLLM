package com.thealgorithms.conversions;

/**
 * Utility class for converting between octal and hexadecimal representations.
 */
public final class OctalToHexConverter {
    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexConverter() {
    }

    /**
     * Converts an octal string to its decimal integer representation.
     *
     * @param octal the octal string to convert
     * @return the decimal integer value of the octal string
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int octalToDecimal(String octal) {
        if (octal == null || octal.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimal = 0;
        for (int position = 0; position < octal.length(); position++) {
            char digitChar = octal.charAt(position);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + digitChar);
            }
            int digitValue = digitChar - '0';
            decimal = decimal * OCTAL_BASE + digitValue;
        }

        return decimal;
    }

    /**
     * Converts a decimal integer to its hexadecimal string representation.
     *
     * @param decimal the decimal integer to convert
     * @return the hexadecimal string representation of the decimal value
     */
    public static String decimalToHex(int decimal) {
        if (decimal == 0) {
            return "0";
        }

        StringBuilder hexBuilder = new StringBuilder();
        int value = decimal;

        while (value > 0) {
            int digitIndex = value % HEX_BASE;
            hexBuilder.insert(0, HEX_DIGITS.charAt(digitIndex));
            value /= HEX_BASE;
        }

        return hexBuilder.toString();
    }
}