package com.thealgorithms.maths;

/**
 * Utility for translating integers into Roman numerals.
 *
 * <p>Supports values in the range [1, 5999].</p>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Roman_numerals">Roman numerals</a>
 */
public final class RomanNumeralUtil {

    private RomanNumeralUtil() {}

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

    /**
     * Converts an integer to its Roman numeral representation.
     *
     * @param number the value to convert; must be in the range [1, 5999]
     * @return the Roman numeral representation of {@code number}
     * @throws IllegalArgumentException if {@code number} is outside the valid range
     */
    public static String generate(int number) {
        validateRange(number);

        int thousandsDigit = number / 1000;
        int hundredsDigit = (number % 1000) / 100;
        int tensDigit = (number % 100) / 10;
        int onesDigit = number % 10;

        return THOUSANDS[thousandsDigit]
            + HUNDREDS[hundredsDigit]
            + TENS[tensDigit]
            + ONES[onesDigit];
    }

    /**
     * Ensures the provided number is within the supported range.
     *
     * @param number the value to validate
     * @throws IllegalArgumentException if {@code number} is outside the valid range
     */
    private static void validateRange(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }
    }
}