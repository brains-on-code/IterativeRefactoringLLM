package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 */
public final class RomanNumeralConverter {

    private RomanNumeralConverter() {
    }

    private static final int MIN_ROMAN_VALUE = 1;
    private static final int MAX_ROMAN_VALUE = 5999;

    // 1000-5000
    private static final String[] THOUSANDS_DIGIT_ROMAN = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    // 100-900
    private static final String[] HUNDREDS_DIGIT_ROMAN = {
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
    private static final String[] TENS_DIGIT_ROMAN = {
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
    private static final String[] UNITS_DIGIT_ROMAN = {
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

    public static String toRoman(int arabicNumber) {
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
        int unitsDigit = arabicNumber % 10;

        return THOUSANDS_DIGIT_ROMAN[thousandsDigit]
            + HUNDREDS_DIGIT_ROMAN[hundredsDigit]
            + TENS_DIGIT_ROMAN[tensDigit]
            + UNITS_DIGIT_ROMAN[unitsDigit];
    }
}