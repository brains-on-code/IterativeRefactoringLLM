package com.thealgorithms.maths;

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

    public static String toRomanNumeral(int value) {
        if (value < MIN_VALUE || value > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format(
                    "The number must be in the range [%d, %d]",
                    MIN_VALUE,
                    MAX_VALUE
                )
            );
        }

        int thousands = value / 1000;
        int hundreds = (value % 1000) / 100;
        int tens = (value % 100) / 10;
        int ones = value % 10;

        return THOUSANDS[thousands]
            + HUNDREDS[hundreds]
            + TENS[tens]
            + ONES[ones];
    }
}