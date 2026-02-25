package com.thealgorithms.maths;

/**
 * Translates numbers into the Roman Numeral System.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numerals">Roman
 * numerals</a>
 */
public final class RomanNumeralUtil {

    private RomanNumeralUtil() {
    }

    private static final int MIN_ROMAN_VALUE = 1;
    private static final int MAX_ROMAN_VALUE = 5999;

    // Thousands: 1000–5000
    private static final String[] THOUSANDS_ROMAN_DIGITS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    // Hundreds: 100–900
    private static final String[] HUNDREDS_ROMAN_DIGITS = {
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

    // Tens: 10–90
    private static final String[] TENS_ROMAN_DIGITS = {
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

    // Ones: 1–9
    private static final String[] ONES_ROMAN_DIGITS = {
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

        int thousandsPlace = arabicNumber / 1000;
        int hundredsPlace = (arabicNumber % 1000) / 100;
        int tensPlace = (arabicNumber % 100) / 10;
        int onesPlace = arabicNumber % 10;

        return THOUSANDS_ROMAN_DIGITS[thousandsPlace]
            + HUNDREDS_ROMAN_DIGITS[hundredsPlace]
            + TENS_ROMAN_DIGITS[tensPlace]
            + ONES_ROMAN_DIGITS[onesPlace];
    }
}