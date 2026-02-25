package com.thealgorithms.conversions;

public final class OctalToHexadecimal {

    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
        // Prevent instantiation
    }

    /**
     * Converts a non-negative octal number (as a string) to its decimal representation.
     *
     * @param octalNumber octal number as a string (e.g., "17")
     * @return decimal value of the given octal number
     * @throws IllegalArgumentException if input is null, empty, or contains non-octal digits
     */
    public static int octalToDecimal(String octalNumber) {
        validateOctalInput(octalNumber);

        int decimalValue = 0;
        for (int i = 0; i < octalNumber.length(); i++) {
            int currentDigit = octalNumber.charAt(i) - '0';
            decimalValue = decimalValue * OCTAL_BASE + currentDigit;
        }
        return decimalValue;
    }

    /**
     * Converts a non-negative decimal number to its hexadecimal representation.
     *
     * @param decimalNumber decimal number to convert
     * @return uppercase hexadecimal representation of the given decimal number
     */
    public static String decimalToHexadecimal(int decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder hexValue = new StringBuilder();
        int value = decimalNumber;

        while (value > 0) {
            int digit = value % HEX_BASE;
            hexValue.insert(0, HEX_DIGITS.charAt(digit));
            value /= HEX_BASE;
        }

        return hexValue.toString();
    }

    /**
     * Validates that the input string is a non-empty octal number.
     *
     * @param octalNumber input string to validate
     * @throws IllegalArgumentException if input is null, empty, or contains non-octal digits
     */
    private static void validateOctalInput(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        for (int i = 0; i < octalNumber.length(); i++) {
            char currentChar = octalNumber.charAt(i);
            if (!isOctalDigit(currentChar)) {
                throw new IllegalArgumentException("Incorrect octal digit: " + currentChar);
            }
        }
    }

    /**
     * Checks whether the given character is a valid octal digit (0–7).
     *
     * @param ch character to check
     * @return true if the character is an octal digit, false otherwise
     */
    private static boolean isOctalDigit(char ch) {
        return ch >= '0' && ch <= '7';
    }
}