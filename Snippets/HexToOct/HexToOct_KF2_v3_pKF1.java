package com.thealgorithms.conversions;

public final class HexToOct {

    private HexToOct() {
        // Utility class; prevent instantiation
    }

    public static int convertHexToDecimal(String hexValue) {
        final String HEX_DIGITS = "0123456789ABCDEF";
        String normalizedHexValue = hexValue.toUpperCase();
        int decimalValue = 0;

        for (int index = 0; index < normalizedHexValue.length(); index++) {
            char currentHexChar = normalizedHexValue.charAt(index);
            int currentDigitValue = HEX_DIGITS.indexOf(currentHexChar);
            decimalValue = 16 * decimalValue + currentDigitValue;
        }

        return decimalValue;
    }

    public static int convertDecimalToOctal(int decimalValue) {
        int octalValue = 0;
        int currentPlaceValue = 1;

        while (decimalValue > 0) {
            int remainder = decimalValue % 8;
            octalValue += remainder * currentPlaceValue;
            decimalValue /= 8;
            currentPlaceValue *= 10;
        }

        return octalValue;
    }

    public static int convertHexToOctal(String hexValue) {
        int decimalValue = convertHexToDecimal(hexValue);
        return convertDecimalToOctal(decimalValue);
    }
}