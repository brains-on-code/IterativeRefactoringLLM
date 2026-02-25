package com.thealgorithms.conversions;

public final class HexToOct {

    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private HexToOct() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal (base-10) representation.
     *
     * @param hex hexadecimal string (case-insensitive, without prefix like "0x")
     * @return decimal value of the given hexadecimal string
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid characters
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

            decimalValue = (decimalValue * 16) + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal (base-10) integer to its octal (base-8) representation.
     *
     * @param decimal decimal value to convert
     * @return octal representation of the given decimal value
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
     * @param hex hexadecimal string (case-insensitive, without prefix like "0x")
     * @return octal representation of the given hexadecimal string
     */
    public static int hexToOctal(String hex) {
        return decimalToOctal(hexToDecimal(hex));
    }
}