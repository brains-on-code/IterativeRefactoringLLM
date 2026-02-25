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
    private static final String[] THOUSANDS_DIGITS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    // Hundreds: 100–900
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

    // Tens: 10–90
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

    // Ones: 1–9
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

    public static String toRoman(int arabicNumber) {
        if (arabicNumber < MIN_ROMAN_VALUE || arabicNumber > MAX_ROMAN_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_ROMAN_VALUE, MAX_ROMAN_VALUE)
            );
        }

        int thousandsDigit = arabicNumber / 1000;
        int hundredsDigit = (arabicNumber % 1000) / 100;
        int tensDigit = (arabicNumber % 100) / 10;
        int onesDigit = arabicNumber % 10;

        return THOUSANDS_DIGITS[thousandsDigit]
            + HUNDREDS_DIGITS[hundredsDigit]
            + TENS_DIGITS[tensDigit]
            + ONES_DIGITS[onesDigit];
    }
}