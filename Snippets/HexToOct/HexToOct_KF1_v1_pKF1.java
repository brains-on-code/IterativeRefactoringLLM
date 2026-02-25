package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexString the hexadecimal string to convert
     * @return the decimal integer representation of the hexadecimal string
     */
    public static int hexToDecimal(String hexString) {
        String hexDigits = "0123456789ABCDEF";
        hexString = hexString.toUpperCase();
        int decimalValue = 0;

        for (int index = 0; index < hexString.length(); index++) {
            char currentChar = hexString.charAt(index);
            int digitValue = hexDigits.indexOf(currentChar);
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
    public static int decimalToOctal(int decimalValue) {
        int octalValue = 0;
        int placeValue = 1;

        while (decimalValue > 0) {
            int remainder = decimalValue % 8;
            octalValue += remainder * placeValue;
            decimalValue /= 8;
            placeValue *= 10;
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