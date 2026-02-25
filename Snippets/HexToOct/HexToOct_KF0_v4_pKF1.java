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
        String uppercaseHexString = hexString.toUpperCase();
        int decimalNumber = 0;

        for (int index = 0; index < uppercaseHexString.length(); index++) {
            char hexCharacter = uppercaseHexString.charAt(index);
            int digitValue = HEX_DIGITS.indexOf(hexCharacter);
            decimalNumber = 16 * decimalNumber + digitValue;
        }

        return decimalNumber;
    }

    /**
     * Converts a decimal number to an octal number.
     *
     * @param decimalNumber The decimal number as an integer.
     * @return The octal equivalent as an integer.
     */
    public static int convertDecimalToOctal(int decimalNumber) {
        int octalNumber = 0;
        int placeValue = 1;

        int remainingDecimal = decimalNumber;
        while (remainingDecimal > 0) {
            int remainder = remainingDecimal % 8;
            octalNumber += remainder * placeValue;
            remainingDecimal /= 8;
            placeValue *= 10;
        }

        return octalNumber;
    }

    /**
     * Converts a hexadecimal number to an octal number.
     *
     * @param hexString The hexadecimal number as a String.
     * @return The octal equivalent as an integer.
     */
    public static int convertHexToOctal(String hexString) {
        int decimalNumber = convertHexToDecimal(hexString);
        return convertDecimalToOctal(decimalNumber);
    }
}