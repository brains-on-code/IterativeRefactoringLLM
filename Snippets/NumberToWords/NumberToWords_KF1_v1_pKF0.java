package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility class to convert numbers to their English word representation.
 */
public final class NumberToWordsConverter {

    private NumberToWordsConverter() {
    }

    private static final String[] UNITS_AND_TEENS = {
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

    private static final String[] TENS = {
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

    private static final String[] SCALE = {
        "",
        "Thousand",
        "Million",
        "Billion",
        "Trillion"
    };

    private static final String ZERO = "Zero";
    private static final String POINT = " Point";
    private static final String NEGATIVE = "Negative ";

    public static String convert(BigDecimal number) {
        if (number == null) {
            return "Invalid Input";
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        BigDecimal fractionalPart = integerAndFraction[1];

        String fractionalDigits =
            fractionalPart.compareTo(BigDecimal.ZERO) > 0
                ? fractionalPart.toPlainString().substring(2)
                : "";

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }
        result.append(convertIntegerPart(integerPart));

        if (!fractionalDigits.isEmpty()) {
            result.append(POINT);
            for (char digitChar : fractionalDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                result.append(" ").append(digit == 0 ? ZERO : UNITS_AND_TEENS[digit]);
            }
        }

        return result.toString().trim();
    }

    private static String convertIntegerPart(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder words = new StringBuilder();
        int scaleIndex = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = divisionResult[1].intValue();

            if (chunk > 0) {
                String chunkWords = convertChunk(chunk);
                if (scaleIndex > 0) {
                    words.insert(0, SCALE[scaleIndex] + " ");
                }
                words.insert(0, chunkWords + " ");
            }

            number = divisionResult[0];
            scaleIndex++;
        }

        return words.toString().trim();
    }

    private static String convertChunk(int number) {
        if (number < 20) {
            return UNITS_AND_TEENS[number];
        } else if (number < 100) {
            return TENS[number / 10]
                + (number % 10 > 0 ? " " + UNITS_AND_TEENS[number % 10] : "");
        } else {
            return UNITS_AND_TEENS[number / 100]
                + " Hundred"
                + (number % 100 > 0 ? " " + convertChunk(number % 100) : "");
        }
    }
}