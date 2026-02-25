package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 */
public final class NumberBaseConverter {

    private static final String HEXADECIMAL_DIGITS = "0123456789ABCDEF";

    private NumberBaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a hexadecimal string to its decimal integer value.
     *
     * @param hexadecimalString the hexadecimal string to convert
     * @return the decimal integer representation of the hexadecimal string
     */
    public static int convertHexToDecimal(String hexadecimalString) {
        String normalizedHexadecimal = hexadecimalString.toUpperCase();
        int decimalResult = 0;

        for (int index = 0; index < normalizedHexadecimal.length(); index++) {
            char currentHexCharacter = normalizedHexadecimal.charAt(index);
            int currentDigitValue = HEXADECIMAL_DIGITS.indexOf(currentHexCharacter);
            decimalResult = 16 * decimalResult + currentDigitValue;
        }

        return decimalResult;
    }

    /**
     * Converts a decimal integer to its octal integer representation.
     *
     * @param decimalValue the decimal integer to convert
     * @return the octal integer representation of the decimal value
     */
    public static int convertDecimalToOctal(int decimalValue) {
        int octalResult = 0;
        int currentPlaceMultiplier = 1;

        while (decimalValue > 0) {
            int currentRemainder = decimalValue % 8;
            octalResult += currentRemainder * currentPlaceMultiplier;
            decimalValue /= 8;
            currentPlaceMultiplier *= 10;
        }

        return octalResult;
    }

    /**
     * Converts a hexadecimal string to its octal integer representation.
     *
     * @param hexadecimalString the hexadecimal string to convert
     * @return the octal integer representation of the hexadecimal value
     */
    public static int convertHexToOctal(String hexadecimalString) {
        int decimalValue = convertHexToDecimal(hexadecimalString);
        return convertDecimalToOctal(decimalValue);
    }
}