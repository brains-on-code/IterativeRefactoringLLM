package com.thealgorithms.maths;

public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_ROMAN_VALUE = 1;
    private static final int MAX_ROMAN_VALUE = 5999;

    private static final String[] THOUSANDS_DIGITS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    private static final String[] HUNDREDS_DIGITS = {
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

    private static final String[] TENS_DIGITS = {
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

    private static final String[] ONES_DIGITS = {
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

    public static String toRomanNumeral(int arabicNumber) {
        if (arabicNumber < MIN_ROMAN_VALUE || arabicNumber > MAX_ROMAN_VALUE) {
            throw new IllegalArgumentException(
                String.format(
                    "The number must be in the range [%d, %d]",
                    MIN_ROMAN_VALUE,
                    MAX_ROMAN_VALUE
                )
            );
        }

        int thousandsIndex = arabicNumber / 1000;
        int hundredsIndex = (arabicNumber % 1000) / 100;
        int tensIndex = (arabicNumber % 100) / 10;
        int onesIndex = arabicNumber % 10;

        return THOUSANDS_DIGITS[thousandsIndex]
            + HUNDREDS_DIGITS[hundredsIndex]
            + TENS_DIGITS[tensIndex]
            + ONES_DIGITS[onesIndex];
    }
}