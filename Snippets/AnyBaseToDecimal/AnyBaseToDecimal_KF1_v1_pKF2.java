package com.thealgorithms.conversions;

/**
 * Utility class for converting strings representing numbers in an arbitrary base
 * (up to base 36 using digits 0–9 and uppercase letters A–Z) to their integer
 * (base-10) representation.
 */
public final class Class1 {

    /** ASCII code for character '0'. */
    private static final int DIGIT_ZERO = '0';

    /** Offset so that 'A' maps to 10, 'B' to 11, ..., 'Z' to 35. */
    private static final int UPPER_A_OFFSET = 'A' - 10;

    private Class1() {
        // Prevent instantiation
    }

    /**
     * Converts a string representing a number in the given base to its integer value.
     *
     * The string may contain:
     * - Digits '0'–'9'
     * - Uppercase letters 'A'–'Z' (representing values 10–35)
     *
     * @param value the string representation of the number
     * @param base  the base of the input number (between 2 and 36)
     * @return the integer (base-10) value of the input string
     * @throws NumberFormatException if the string contains invalid characters
     *                               for the given base
     */
    public static int method1(String value, int base) {
        int result = 0;
        int multiplier = 1;

        for (int i = value.length() - 1; i >= 0; i--) {
            int digit = method2(value.charAt(i));
            if (digit >= base) {
                throw new NumberFormatException("For input string: " + value);
            }
            result += digit * multiplier;
            multiplier *= base;
        }
        return result;
    }

    /**
     * Converts a single character to its numeric value.
     *
     * Supported characters:
     * - '0'–'9' → 0–9
     * - 'A'–'Z' → 10–35
     *
     * @param ch the character to convert
     * @return the numeric value of the character
     * @throws NumberFormatException if the character is not a digit or
     *                               an uppercase letter
     */
    private static int method2(char ch) {
        if (Character.isDigit(ch)) {
            return ch - DIGIT_ZERO;
        } else if (Character.isUpperCase(ch)) {
            return ch - UPPER_A_OFFSET;
        } else {
            throw new NumberFormatException("invalid character: " + ch);
        }
    }
}