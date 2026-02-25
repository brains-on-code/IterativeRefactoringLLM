package com.thealgorithms.conversions;

public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal (base-10) representation.
     *
     * @param hex the hexadecimal string (case-insensitive, without prefix like "0x")
     * @return the decimal value of the given hexadecimal string
     * @throws IllegalArgumentException if the input contains invalid hexadecimal characters
     */
    public static int hexToDecimal(String hex) {
        if (hex == null || hex.isEmpty()) {
            throw new IllegalArgumentException("Hex string must not be null or empty");
        }

        String upperHex = hex.toUpperCase();
        int decimalValue = 0;

        for (int i = 0; i < upperHex.length(); i++) {
            char hexChar = upperHex.charAt(i);
            int digitValue = HEX_DIGITS.indexOf(hexChar);

            if (digitValue == -1) {
                throw new IllegalArgumentException("Invalid hexadecimal character: " + hexChar);
            }

            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal (base-10) integer to its octal (base-8) representation.
     *
     * @param decimal the decimal value to convert
     * @return the octal representation of the given decimal value
     */
    public static int decimalToOctal(int decimal) {
        if (decimal == 0) {
            return 0;
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
     * Converts a hexadecimal string directly to its octal (base-8) representation.
     *
     * @param hex the hexadecimal string (case-insensitive, without prefix like "0x")
     * @return the octal representation of the given hexadecimal string
     */
    public static int hexToOctal(String hex) {
        int decimalValue = hexToDecimal(hex);
        return decimalToOctal(decimalValue);
    }
}