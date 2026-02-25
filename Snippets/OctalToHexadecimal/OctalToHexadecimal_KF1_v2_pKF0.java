package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class NumberBaseConverter {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private NumberBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts an octal string to its decimal integer representation.
     *
     * @param octalString the octal number as a string
     * @return the decimal integer value
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int octalToDecimal(String octalString) {
        validateOctalInput(octalString);

        int decimalValue = 0;
        for (int i = 0; i < octalString.length(); i++) {
            int digitValue = octalString.charAt(i) - '0';
            decimalValue = decimalValue * OCTAL_BASE + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its hexadecimal string representation.
     *
     * @param decimalValue the decimal integer value
     * @return the hexadecimal representation as a string
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

    private static void validateOctalInput(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (int i = 0; i < octalString.length(); i++) {
            char digitChar = octalString.charAt(i);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + digitChar);
            }
        }
    }
}