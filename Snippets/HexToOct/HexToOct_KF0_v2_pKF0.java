package com.thealgorithms.conversions;

/**
 * Utility class for converting hexadecimal numbers to octal.
 */
public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";
    private static final int HEX_BASE = 16;
    private static final int OCTAL_BASE = 8;
    private static final int DECIMAL_PLACE_BASE = 10;

    private HexToOct() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal number to a decimal number.
     *
     * @param hex the hexadecimal number as a String
     * @return the decimal equivalent as an int
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid hex characters
     */
    public static int hexToDecimal(String hex) {
        validateHexInput(hex);

        String normalizedHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            int digitValue = HEX_DIGITS.indexOf(normalizedHex.charAt(i));
            if (digitValue < 0) {
                throw new IllegalArgumentException("Invalid hex character: " + normalizedHex.charAt(i));
            }
            decimalValue = decimalValue * HEX_BASE + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal number to an octal number.
     *
     * @param decimal the decimal number as an int
     * @return the octal equivalent as an int
     */
    public static int decimalToOctal(int decimal) {
        if (decimal == 0) {
            return 0;
        }

        int octalValue = 0;
        int placeValue = 1;
        int remaining = decimal;

        while (remaining > 0) {
            int remainder = remaining % OCTAL_BASE;
            octalValue += remainder * placeValue;
            remaining /= OCTAL_BASE;
            placeValue *= DECIMAL_PLACE_BASE;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal number to an octal number.
     *
     * @param hex the hexadecimal number as a String
     * @return the octal equivalent as an int
     */
    public static int hexToOctal(String hex) {
        return decimalToOctal(hexToDecimal(hex));
    }

    private static void validateHexInput(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty.");
        }
    }
}