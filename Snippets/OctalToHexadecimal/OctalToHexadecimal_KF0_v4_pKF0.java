package com.thealgorithms.conversions;

/**
 * Utility class for converting an Octal number to its Hexadecimal equivalent.
 */
public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Prevent instantiation
    }

    /**
     * Converts an octal number (as a string) to its decimal equivalent.
     *
     * @param octalNumber the octal number as a string
     * @return the decimal equivalent of the octal number
     * @throws IllegalArgumentException if the input is null, empty, or contains invalid octal digits
     */
    public static int octalToDecimal(String octalNumber) {
        validateOctalInput(octalNumber);

        int decimalValue = 0;
        int length = octalNumber.length();

        for (int i = 0; i < length; i++) {
            int currentDigit = Character.digit(octalNumber.charAt(i), OCTAL_BASE);
            decimalValue = decimalValue * OCTAL_BASE + currentDigit;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal number to its hexadecimal equivalent.
     *
     * @param decimalNumber the decimal number
     * @return the hexadecimal equivalent of the decimal number
     */
    public static String decimalToHexadecimal(int decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder hexValue = new StringBuilder();
        int value = decimalNumber;

        while (value > 0) {
            int digit = value % HEX_BASE;
            hexValue.append(HEX_DIGITS.charAt(digit));
            value /= HEX_BASE;
        }

        return hexValue.reverse().toString();
    }

    private static void validateOctalInput(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int length = octalNumber.length();
        for (int i = 0; i < length; i++) {
            char currentChar = octalNumber.charAt(i);
            if (!isValidOctalDigit(currentChar)) {
                throw new IllegalArgumentException("Incorrect octal digit: " + currentChar);
            }
        }
    }

    private static boolean isValidOctalDigit(char digit) {
        return digit >= '0' && digit <= '7';
    }
}