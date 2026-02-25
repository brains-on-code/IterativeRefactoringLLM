package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 *
 * <p>Provides methods to convert:
 * <ul>
 *   <li>Hexadecimal string to decimal integer</li>
 *   <li>Decimal integer to octal integer (as a base-10 representation)</li>
 *   <li>Hexadecimal string directly to octal integer (via decimal)</li>
 * </ul>
 */
public final class NumberBaseConverter {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private NumberBaseConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string (case-insensitive, digits 0-9 and A-F)
     * @return the decimal integer representation of the given hexadecimal string
     * @throws IllegalArgumentException if the string is null, empty, or contains invalid hexadecimal characters
     */
    public static int hexToDecimal(String hexString) {
        validateHexString(hexString);

        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < normalizedHex.length(); i++) {
            char currentChar = normalizedHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(currentChar);

            if (digitValue == -1) {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + currentChar);
            }

            decimalValue = decimalValue * 16 + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its octal representation,
     * returned as an integer whose digits are in base 10.
     *
     * <p>Example: input {@code 10} (decimal) returns {@code 12} (octal).
     *
     * @param decimal the decimal integer to convert
     * @return the octal representation as an integer
     */
    public static int decimalToOctal(int decimal) {
        if (decimal == 0) {
            return 0;
        }

        int octalValue = 0;
        int place = 1;

        while (decimal > 0) {
            int remainder = decimal % 8;
            octalValue += remainder * place;
            decimal /= 8;
            place *= 10;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal string directly to its octal representation,
     * returned as an integer whose digits are in base 10.
     *
     * <p>This is equivalent to:
     * <pre>
     *   int decimal = hexToDecimal(hexString);
     *   return decimalToOctal(decimal);
     * </pre>
     *
     * @param hexString the hexadecimal string to convert
     * @return the octal representation as an integer
     * @throws IllegalArgumentException if the string is null, empty, or contains invalid hexadecimal characters
     */
    public static int hexToOctal(String hexString) {
        return decimalToOctal(hexToDecimal(hexString));
    }

    private static void validateHexString(String hexString) {
        if (hexString == null || hexString.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty");
        }
    }
}