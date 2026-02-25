package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class NumberBaseConverter {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private NumberBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string to convert
     * @return the decimal integer representation of the hexadecimal string
     */
    public static int convertHexToDecimal(String hexString) {
        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char hexChar = normalizedHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(hexChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its octal integer representation.
     *
     * @param decimalValue the decimal integer to convert
     * @return the octal integer representation of the decimal value
     */
    public static int convertDecimalToOctal(int decimalValue) {
        int octalValue = 0;
        int placeMultiplier = 1;

        while (decimalValue > 0) {
            int remainder = decimalValue % 8;
            octalValue += remainder * placeMultiplier;
            decimalValue /= 8;
            placeMultiplier *= 10;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal string to its octal integer representation.
     *
     * @param hexString the hexadecimal string to convert
     * @return the octal integer representation of the hexadecimal value
     */
    public static int convertHexToOctal(String hexString) {
        int decimalValue = convertHexToDecimal(hexString);
        return convertDecimalToOctal(decimalValue);
    }
}