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
            char currentChar = octalString.charAt(index);
            if (currentChar < '0' || currentChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + currentChar);
            }
            int digitValue = currentChar - '0';
            decimalValue = decimalValue * OCTAL_BASE + digitValue;
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

        StringBuilder hexBuilder = new StringBuilder();
        int value = decimalValue;

        while (value > 0) {
            int remainder = value % HEX_BASE;
            hexBuilder.insert(0, HEX_DIGITS.charAt(remainder));
            value /= HEX_BASE;
        }

        return hexBuilder.toString();
    }
}