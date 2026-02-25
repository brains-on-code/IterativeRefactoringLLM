package com.thealgorithms.maths;

/**
 * Utility class for converting integers to Roman numerals.
 *
 * <p>Supported range: 1 to 5999 (inclusive).</p>
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    private static final int MIN_VALUE = 1;
    private static final int MAX_VALUE = 5999;

    // Thousands: 0, 1000, 2000, 3000, 4000, 5000
    private static final String[] THOUSANDS = {
        "",
        "M",
        "MM",
        "MMM",
        "MMMM",
        "MMMMM",
    };

    // Hundreds: 0, 100, 200, ..., 900
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

    // Tens: 0, 10, 20, ..., 90
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

    // Ones: 0, 1, 2, ..., 9
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
     * @param number the integer to convert (must be between 1 and 5999 inclusive)
     * @return the Roman numeral representation of {@code number}
     * @throws IllegalArgumentException if {@code number} is outside the supported range
     */
    public static String method1(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }

        return THOUSANDS[number / 1000]
            + HUNDREDS[(number % 1000) / 100]
            + TENS[(number % 100) / 10]
            + ONES[number % 10];
    }
}