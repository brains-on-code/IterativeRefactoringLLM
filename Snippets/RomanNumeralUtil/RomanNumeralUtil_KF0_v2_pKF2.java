package com.thealgorithms.maths;

/**
 * Utility for translating integers into Roman numerals.
 *
 * <p>Supports values in the range [1, 5999].</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numerals">Roman numerals</a>
 */
public final class RomanNumeralUtil {

    private RomanNumeralUtil() {
        // Prevent instantiation
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    /** Roman numeral fragments for the thousands place (0–5). */
    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    /** Roman numeral fragments for the hundreds place (0–9). */
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

    /** Roman numeral fragments for the tens place (0–9). */
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

    /** Roman numeral fragments for the ones place (0–9). */
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

    /**
     * Converts an integer to its Roman numeral representation.
     *
     * @param number the value to convert; must be in the range [1, 5999]
     * @return the Roman numeral representation of {@code number}
     * @throws IllegalArgumentException if {@code number} is outside the valid range
     */
    public static String generate(int number) {
        validateRange(number);

        int thousandsIndex = number / 1000;
        int hundredsIndex = (number % 1000) / 100;
        int tensIndex = (number % 100) / 10;
        int onesIndex = number % 10;

        return THOUSANDS[thousandsIndex]
            + HUNDREDS[hundredsIndex]
            + TENS[tensIndex]
            + ONES[onesIndex];
    }

    private static void validateRange(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }
    }
}