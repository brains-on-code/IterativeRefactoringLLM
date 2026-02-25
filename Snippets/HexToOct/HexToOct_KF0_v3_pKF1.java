package com.thealgorithms.conversions;

/**
 * Converts any Hexadecimal Number to Octal
 *
 * @author Tanmay Joshi
 */
public final class HexToOct {

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a hexadecimal number to a decimal number.
     *
     * @param hexString The hexadecimal number as a String.
     * @return The decimal equivalent as an integer.
     */
    public static int convertHexToDecimal(String hexString) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHex = hexString.toUpperCase();
        int decimalValue = 0;

        for (int position = 0; position < normalizedHex.length(); position++) {
            char currentHexChar = normalizedHex.charAt(position);
            int digitValue = HEX_DIGITS.indexOf(currentHexChar);
            decimalValue = 16 * decimalValue + digitValue;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal number to an octal number.
     *
     * @param decimalValue The decimal number as an integer.
     * @return The octal equivalent as an integer.
     */
    public static int convertDecimalToOctal(int decimalValue) {
        int octalValue = 0;
        int placeMultiplier = 1;

        int remainingValue = decimalValue;
        while (remainingValue > 0) {
            int remainder = remainingValue % 8;
            octalValue += remainder * placeMultiplier;
            remainingValue /= 8;
            placeMultiplier *= 10;
        }

        return octalValue;
    }

    /**
     * Converts a hexadecimal number to an octal number.
     *
     * @param hexString The hexadecimal number as a String.
     * @return The octal equivalent as an integer.
     */
    public static int convertHexToOctal(String hexString) {
        int decimalValue = convertHexToDecimal(hexString);
        return convertDecimalToOctal(decimalValue);
    }
}