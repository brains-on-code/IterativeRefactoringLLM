package com.thealgorithms.maths;

/**
 * Translates numbers into the Roman Numeral System.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numerals">Roman numerals</a>
 */
public final class RomanNumeralUtil {

    private RomanNumeralUtil() {
        // Utility class; prevent instantiation
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM"
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
        "CM"
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
        "XC"
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
        "IX"
    };

    public static String generate(int number) {
        validateRange(number);

        int[] digits = splitIntoDigits(number);

        return THOUSANDS[digits[0]]
            + HUNDREDS[digits[1]]
            + TENS[digits[2]]
            + ONES[digits[3]];
    }

    private static int[] splitIntoDigits(int number) {
        int thousands = number / 1000;
        int hundreds = (number % 1000) / 100;
        int tens = (number % 100) / 10;
        int ones = number % 10;
        return new int[] {thousands, hundreds, tens, ones};
    }

    private static void validateRange(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }
    }
}