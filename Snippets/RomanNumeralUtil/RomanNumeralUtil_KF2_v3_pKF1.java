package com.thealgorithms.maths;

public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_ARABIC_VALUE = 1;
    private static final int MAX_ARABIC_VALUE = 5999;

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

    private static final String[] ONES_ROMAN = {
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

    public static String toRomanNumeral(int arabicValue) {
        if (arabicValue < MIN_ARABIC_VALUE || arabicValue > MAX_ARABIC_VALUE) {
            throw new IllegalArgumentException(
                String.format(
                    "The number must be in the range [%d, %d]",
                    MIN_ARABIC_VALUE,
                    MAX_ARABIC_VALUE
                )
            );
        }

        int thousandsDigit = arabicValue / 1000;
        int hundredsDigit = (arabicValue % 1000) / 100;
        int tensDigit = (arabicValue % 100) / 10;
        int onesDigit = arabicValue % 10;

        return THOUSANDS_ROMAN[thousandsDigit]
            + HUNDREDS_ROMAN[hundredsDigit]
            + TENS_ROMAN[tensDigit]
            + ONES_ROMAN[onesDigit];
    }
}