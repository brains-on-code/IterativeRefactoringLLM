package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 */
public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

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
                String.format(
                    "The number must be in the range [%d, %d]",
                    MIN_VALUE,
                    MAX_VALUE
                )
            );
        }

        int thousandsIndex = number / 1000;
        int hundredsIndex = (number % 1000) / 100;
        int tensIndex = (number % 100) / 10;
        int unitsIndex = number % 10;

        return THOUSANDS[thousandsIndex]
            + HUNDREDS[hundredsIndex]
            + TENS[tensIndex]
            + UNITS[unitsIndex];
    }
}