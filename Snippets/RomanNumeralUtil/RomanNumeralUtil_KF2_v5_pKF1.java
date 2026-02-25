package com.thealgorithms.maths;

public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_ROMAN_VALUE = 1;
    private static final int MAX_ROMAN_VALUE = 5999;

    private static final String[] THOUSANDS_DIGIT_ROMANS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    private static final String[] HUNDREDS_DIGIT_ROMANS = {
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

    private static final String[] TENS_DIGIT_ROMANS = {
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

    private static final String[] ONES_DIGIT_ROMANS = {
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

        int thousandsDigit = arabicNumber / 1000;
        int hundredsDigit = (arabicNumber % 1000) / 100;
        int tensDigit = (arabicNumber % 100) / 10;
        int onesDigit = arabicNumber % 10;

        return THOUSANDS_DIGIT_ROMANS[thousandsDigit]
            + HUNDREDS_DIGIT_ROMANS[hundredsDigit]
            + TENS_DIGIT_ROMANS[tensDigit]
            + ONES_DIGIT_ROMANS[onesDigit];
    }
}