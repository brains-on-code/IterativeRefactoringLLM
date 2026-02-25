package com.thealgorithms.conversions;

/**
 * Class for converting an Octal number to its Hexadecimal equivalent.
 *
 * @author Tanmay Joshi
 */
public final class OctalToHexadecimal {
    private static final int OCTAL_BASE = 8;
    private static final int HEXADECIMAL_BASE = 16;
    private static final String HEXADECIMAL_DIGITS = "0123456789ABCDEF";

    private OctalToHexadecimal() {
    }

    /**
     * Converts an Octal number (as a string) to its Decimal equivalent.
     *
     * @param octalNumber The Octal number as a string
     * @return The Decimal equivalent of the Octal number
     * @throws IllegalArgumentException if the input contains invalid octal digits
     */
    public static int convertOctalToDecimal(String octalNumber) {
        if (octalNumber == null || octalNumber.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalValue = 0;
        for (int position = 0; position < octalNumber.length(); position++) {
            char octalCharacter = octalNumber.charAt(position);
            if (octalCharacter < '0' || octalCharacter > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + octalCharacter);
            }
            int octalDigit = octalCharacter - '0';
            decimalValue = decimalValue * OCTAL_BASE + octalDigit;
        }

        return decimalValue;
    }

    /**
     * Converts a Decimal number to its Hexadecimal equivalent.
     *
     * @param decimalNumber The Decimal number
     * @return The Hexadecimal equivalent of the Decimal number
     */
    public static String convertDecimalToHexadecimal(int decimalNumber) {
        if (decimalNumber == 0) {
            return "0";
        }

        StringBuilder hexadecimalNumber = new StringBuilder();
        int remainingValue = decimalNumber;

        while (remainingValue > 0) {
            int hexadecimalDigitIndex = remainingValue % HEXADECIMAL_BASE;
            hexadecimalNumber.insert(0, HEXADECIMAL_DIGITS.charAt(hexadecimalDigitIndex));
            remainingValue /= HEXADECIMAL_BASE;
        }

        return hexadecimalNumber.toString();
    }
}