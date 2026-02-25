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

        int thousandsIndex = extractThousands(number);
        int hundredsIndex = extractHundreds(number);
        int tensIndex = extractTens(number);
        int onesIndex = extractOnes(number);

        return new StringBuilder()
            .append(THOUSANDS[thousandsIndex])
            .append(HUNDREDS[hundredsIndex])
            .append(TENS[tensIndex])
            .append(ONES[onesIndex])
            .toString();
    }

    private static int extractThousands(int number) {
        return number / 1000;
    }

    private static int extractHundreds(int number) {
        return (number % 1000) / 100;
    }

    private static int extractTens(int number) {
        return (number % 100) / 10;
    }

    private static int extractOnes(int number) {
        return number % 10;
    }

    private static void validateRange(int number) {
        if (number < MIN_VALUE || number > MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("The number must be in the range [%d, %d]", MIN_VALUE, MAX_VALUE)
            );
        }
    }
}