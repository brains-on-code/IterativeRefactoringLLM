package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 */
public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    // 1000-5000
    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    // 100-900
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

    // 10-90
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

    // 1-9
    private static final String[] UNITS = {
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

    public static String toRoman(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }

        return THOUSANDS[number / 1000]
            + HUNDREDS[number % 1000 / 100]
            + TENS[number % 100 / 10]
            + UNITS[number % 10];
    }
}