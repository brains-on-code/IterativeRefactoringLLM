package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 *
 * <p>Supported range: 1 to 5999 (inclusive).</p>
 */
public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
        // Utility class; prevent instantiation
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    /**
     * Roman numeral fragments for the thousands place (0–5000).
     */
    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    /**
     * Roman numeral fragments for the hundreds place (0–900).
     */
    private static final String[] HUNDREDS = {
        "",
        "C",
        "CC",
        "CCC",
        "CD",
        "D",
        "DC",
        "DCC",
        "DCCC",
        "CM",
    };

    /**
     * Roman numeral fragments for the tens place (0–90).
     */
    private static final String[] TENS = {
        "",
        "X",
        "XX",
        "XXX",
        "XL",
        "L",
        "LX",
        "LXX",
        "LXXX",
        "XC",
    };

    /**
     * Roman numeral fragments for the ones place (0–9).
     */
    private static final String[] ONES = {
        "",
        "I",
        "II",
        "III",
        "IV",
        "V",
        "VI",
        "VII",
        "VIII",
        "IX",
    };

    /**
     * Converts an integer to its Roman numeral representation.
     *
     * @param number the integer to convert (must be between 1 and 5999 inclusive)
     * @return the Roman numeral representation of {@code number}
     * @throws IllegalArgumentException if {@code number} is outside the supported range
     */
    public static String toRoman(int number) {
        validateRange(number);

        int thousands = number / 1000;
        int hundreds = (number % 1000) / 100;
        int tens = (number % 100) / 10;
        int ones = number % 10;

        return THOUSANDS[thousands]
            + HUNDREDS[hundreds]
            + TENS[tens]
            + ONES[ones];
    }

    private static void validateRange(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }
    }
}