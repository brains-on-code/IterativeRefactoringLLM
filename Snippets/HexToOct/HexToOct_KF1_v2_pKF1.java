package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class NumberBaseConverter {

    private NumberBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string to convert
     * @return the decimal integer representation of the hexadecimal string
     */
    public static int hexToDecimal(String hexString) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int index = 0; index < normalizedHex.length(); index++) {
            char currentChar = normalizedHex.charAt(index);
            int digitValue = HEX_DIGITS.indexOf(currentChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its octal integer representation.
     *
     * @param decimalNumber the decimal integer to convert
     * @return the octal integer representation of the decimal value
     */
    public static int decimalToOctal(int decimalNumber) {
        int octalValue = 0;
        int placeMultiplier = 1;

        while (decimalNumber > 0) {
            int remainder = decimalNumber % 8;
            octalValue += remainder * placeMultiplier;
            decimalNumber /= 8;
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
    public static int hexToOctal(String hexString) {
        int decimalValue = hexToDecimal(hexString);
        return decimalToOctal(decimalValue);
    }
}