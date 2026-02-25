package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class NumberBaseConverter {

    private static final String HEX_DIGITS = "0123456789ABCDEF";
    private static final int HEX_BASE = 16;
    private static final int OCTAL_BASE = 8;
    private static final int DECIMAL_RADIX = 10;

    private NumberBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string (0-9, A-F or a-f)
     * @return the decimal integer representation
     * @throws IllegalArgumentException if the string is null, empty, or contains invalid hex characters
     */
    public static int hexToDecimal(String hexString) {
        validateHexInput(hexString);

        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char currentChar = normalizedHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(currentChar);

            if (digitValue < 0) {
                throw new IllegalArgumentException("Invalid hex character: " + currentChar);
            }

            decimalValue = (decimalValue * HEX_BASE) + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its octal representation as an integer.
     * (Note: leading zeros are not preserved.)
     *
     * @param decimal the decimal integer
     * @return the octal representation as an integer
     * @throws IllegalArgumentException if the decimal value is negative
     */
    public static int decimalToOctal(int decimal) {
        if (decimal < 0) {
            throw new IllegalArgumentException("Decimal value must be non-negative");
        }

        if (decimal == 0) {
            return 0;
        }

        int octalValue = 0;
        int placeValue = 1;

        while (decimal > 0) {
            int remainder = decimal % OCTAL_BASE;
            octalValue += remainder * placeValue;
            decimal /= OCTAL_BASE;
            placeValue *= DECIMAL_RADIX;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal string to its octal representation as an integer.
     *
     * @param hexString the hexadecimal string
     * @return the octal representation as an integer
     * @throws IllegalArgumentException if the hex string is invalid
     */
    public static int hexToOctal(String hexString) {
        return decimalToOctal(hexToDecimal(hexString));
    }

    private static void validateHexInput(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("Input hex string must not be null or empty");
        }
    }
}