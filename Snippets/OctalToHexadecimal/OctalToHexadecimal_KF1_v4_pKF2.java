package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 *
 * <p>Provides methods to convert:
 * <ul>
 *     <li>Octal (base 8) string to decimal integer</li>
 *     <li>Decimal integer to hexadecimal (base 16) string</li>
 * </ul>
 */
public final class NumberBaseConverter {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private NumberBaseConverter() {
        // Utility class; prevent instantiation.
    }

    /**
     * Converts an octal (base 8) string to its decimal integer representation.
     *
     * @param octalString string representing an octal number (digits 0–7)
     * @return decimal integer value of the given octal string
     * @throws IllegalArgumentException if the input is null, empty, or contains
     *                                  characters other than digits 0–7
     */
    public static int octalToDecimal(String octalString) {
        validateOctalInput(octalString);

        int decimalValue = 0;
        for (int i = 0; i < octalString.length(); i++) {
            int digit = octalString.charAt(i) - '0';
            decimalValue = decimalValue * OCTAL_BASE + digit;
        }
        return decimalValue;
    }

    /**
     * Converts a decimal integer to its hexadecimal (base 16) string representation.
     *
     * @param decimalValue decimal integer to convert
     * @return hexadecimal representation of the given decimal value
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

    /**
     * Validates that the given string is a non-empty octal number (digits 0–7).
     *
     * @param octalString string to validate
     * @throws IllegalArgumentException if the string is null, empty, or contains
     *                                  non-octal characters
     */
    private static void validateOctalInput(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (int i = 0; i < octalString.length(); i++) {
            char digitChar = octalString.charAt(i);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Invalid octal digit: " + digitChar);
            }
        }
    }
}