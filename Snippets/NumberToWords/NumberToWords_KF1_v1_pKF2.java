package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility class for converting numeric values to their English word representation.
 */
public final class Class1 {

    private Class1() {
        // Prevent instantiation
    }

    private static final String[] NUMBER_WORDS_0_TO_19 = {
        "",
        "One",
        "Two",
        "Three",
        "Four",
        "Five",
        "Six",
        "Seven",
        "Eight",
        "Nine",
        "Ten",
        "Eleven",
        "Twelve",
        "Thirteen",
        "Fourteen",
        "Fifteen",
        "Sixteen",
        "Seventeen",
        "Eighteen",
        "Nineteen"
    };

    private static final String[] TENS_WORDS = {
        "",
        "",
        "Twenty",
        "Thirty",
        "Forty",
        "Fifty",
        "Sixty",
        "Seventy",
        "Eighty",
        "Ninety"
    };

    private static final String[] SCALE_WORDS = {
        "",
        "Thousand",
        "Million",
        "Billion",
        "Trillion"
    };

    private static final String ZERO = "Zero";
    private static final String POINT = " Point";
    private static final String NEGATIVE = "Negative ";

    /**
     * Converts a BigDecimal number to its English word representation.
     *
     * @param number the number to convert
     * @return the English word representation of the number, or "Invalid Input" if null
     */
    public static String method1(BigDecimal number) {
        if (number == null) {
            return "Invalid Input";
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        String fractionPart =
            integerAndFraction[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFraction[1].toPlainString().substring(2)
                : "";

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }
        result.append(method2(integerPart));

        if (!fractionPart.isEmpty()) {
            result.append(POINT);
            for (char digitChar : fractionPart.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                result.append(" ").append(digit == 0 ? ZERO : NUMBER_WORDS_0_TO_19[digit]);
            }
        }

        return result.toString().trim();
    }

    /**
     * Converts the integer part of a number to words, handling thousands, millions, etc.
     *
     * @param number the non-negative integer part as BigDecimal
     * @return the English word representation of the integer part
     */
    private static String method2(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder result = new StringBuilder();
        int scaleIndex = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divided = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = divided[1].intValue();

            if (chunk > 0) {
                String chunkWords = method3(chunk);
                if (scaleIndex > 0) {
                    result.insert(0, SCALE_WORDS[scaleIndex] + " ");
                }
                result.insert(0, chunkWords + " ");
            }

            number = divided[0];
            scaleIndex++;
        }

        return result.toString().trim();
    }

    /**
     * Converts a number from 1 to 999 into words.
     *
     * @param number the number to convert (1–999)
     * @return the English word representation of the number
     */
    private static String method3(int number) {
        String words;

        if (number < 20) {
            words = NUMBER_WORDS_0_TO_19[number];
        } else if (number < 100) {
            words =
                TENS_WORDS[number / 10]
                    + (number % 10 > 0 ? " " + NUMBER_WORDS_0_TO_19[number % 10] : "");
        } else {
            words =
                NUMBER_WORDS_0_TO_19[number / 100]
                    + " Hundred"
                    + (number % 100 > 0 ? " " + method3(number % 100) : "");
        }

        return words;
    }
}