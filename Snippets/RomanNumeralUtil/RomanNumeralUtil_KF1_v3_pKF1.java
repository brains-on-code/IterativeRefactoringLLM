package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 */
public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_SUPPORTED_VALUE = 1;
    private static final int MAX_SUPPORTED_VALUE = 5999;

    private static final String[] THOUSANDS_ROMAN = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    private static final String[] HUNDREDS_ROMAN = {
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

    private static final String[] TENS_ROMAN = {
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

    private static final String[] UNITS_ROMAN = {
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

    public static String toRoman(int value) {
        if (value < MIN_SUPPORTED_VALUE || value > MAX_SUPPORTED_VALUE) {
            throw new IllegalArgumentException(
                String.format(
                    "The number must be in the range [%d, %d]",
                    MIN_SUPPORTED_VALUE,
                    MAX_SUPPORTED_VALUE
                )
            );
        }

        int thousands = value / 1000;
        int hundreds = (value % 1000) / 100;
        int tens = (value % 100) / 10;
        int units = value % 10;

        return THOUSANDS_ROMAN[thousands]
            + HUNDREDS_ROMAN[hundreds]
            + TENS_ROMAN[tens]
            + UNITS_ROMAN[units];
    }
}