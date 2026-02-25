package com.thealgorithms.conversions;

/**
 * Utility class for number base conversions.
 *
 * <p>Provides methods to convert:
 * <ul>
 *     <li>Octal (base 8) string to decimal integer</li>
 *     <li>Decimal integer to hexadecimal (base 16) string</li>
 * </ul>
 */
public final class Class1 {
    private static final int OCTAL_BASE = 8;
    private static final int HEX_BASE = 16;
    private static final String HEX_DIGITS = "0123456789ABCDEF";

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts an octal (base 8) string to its decimal integer representation.
     *
     * @param octalString the string representing an octal number (digits 0–7)
     * @return the decimal integer value of the given octal string
     * @throws IllegalArgumentException if the input is null, empty, or contains
     *                                  characters other than digits 0–7
     */
    public static int method1(String octalString) {
        if (octalString == null || octalString.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or empty");
        }

        int decimalValue = 0;
        for (int i = 0; i < octalString.length(); i++) {
            char digitChar = octalString.charAt(i);
            if (digitChar < '0' || digitChar > '7') {
                throw new IllegalArgumentException("Incorrect octal digit: " + digitChar);
            }
            int digit = digitChar - '0';
            decimalValue = decimalValue * OCTAL_BASE + digit;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal integer to its hexadecimal (base 16) string representation.
     *
     * @param decimalValue the decimal integer to convert
     * @return the hexadecimal representation of the given decimal value
     */
    public static String method2(int decimalValue) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder hexBuilder = new StringBuilder();
        while (decimalValue > 0) {
            int remainder = decimalValue % HEX_BASE;
            hexBuilder.insert(0, HEX_DIGITS.charAt(remainder));
            decimalValue /= HEX_BASE;
        }

        return hexBuilder.toString();
    }
}