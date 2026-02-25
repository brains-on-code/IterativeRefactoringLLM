package com.thealgorithms.conversions;

/**
 * Utility class for converting hexadecimal numbers to octal.
 */
public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private HexToOct() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hex hexadecimal number as a string (case-insensitive)
     * @return decimal representation of the given hexadecimal number
     * @throws IllegalArgumentException if {@code hex} is null, empty, or contains invalid characters
     */
    public static int hexToDecimal(String hex) {
        validateHexInput(hex);

        String normalizedHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char hexChar = normalizedHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(hexChar);

            if (digitValue == -1) {
                throw new IllegalArgumentException("Invalid hex character: " + hexChar);
            }

            decimalValue = (decimalValue * 16) + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a non-negative decimal integer to its octal representation.
     *
     * @param decimal non-negative decimal integer
     * @return octal representation of the given decimal number
     * @throws IllegalArgumentException if {@code decimal} is negative
     */
    public static int decimalToOctal(int decimal) {
        if (decimal < 0) {
            throw new IllegalArgumentException("Decimal value must be non-negative.");
        }

        int octalValue = 0;
        int placeValue = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octalValue += remainder * placeValue;
            decimal /= 8;
            placeValue *= 10;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal string directly to its octal integer representation.
     *
     * @param hex hexadecimal number as a string (case-insensitive)
     * @return octal representation of the given hexadecimal number
     * @throws IllegalArgumentException if {@code hex} is null, empty, or contains invalid characters
     */
    public static int hexToOctal(String hex) {
        return decimalToOctal(hexToDecimal(hex));
    }

    /**
     * Validates that the hexadecimal input is neither null nor empty.
     *
     * @param hex hexadecimal number as a string
     * @throws IllegalArgumentException if {@code hex} is null or empty
     */
    private static void validateHexInput(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty.");
        }
    }
}