package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 A Java-based utility for converting numeric values into their English word
 representations. Whether you need to convert a small number, a large number
 with millions and billions, or even a number with decimal places, this utility
 has you covered.
 *
 */
public final class NumberToWords {

    private NumberToWords() {
    }

    private static final String[] UNIT_WORDS = {
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

    private static final String ZERO_WORD = "Zero";
    private static final String POINT_WORD = " Point";
    private static final String NEGATIVE_PREFIX = "Negative ";

    public static String convert(BigDecimal number) {
        if (number == null) {
            return "Invalid Input";
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        String fractionalPart =
            integerAndFraction[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFraction[1].toPlainString().substring(2)
                : "";

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE_PREFIX);
        }
        result.append(convertIntegerPartToWords(integerPart));

        if (!fractionalPart.isEmpty()) {
            result.append(POINT_WORD);
            for (char digitChar : fractionalPart.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                result.append(" ").append(digit == 0 ? ZERO_WORD : UNIT_WORDS[digit]);
            }
        }

        return result.toString().trim();
    }

    private static String convertIntegerPartToWords(BigDecimal integerPart) {
        if (integerPart.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_WORD;
        }

        StringBuilder words = new StringBuilder();
        int scaleIndex = 0;

        BigDecimal remaining = integerPart;
        while (remaining.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = remaining.divideAndRemainder(BigDecimal.valueOf(1000));
            int threeDigitChunk = divisionResult[1].intValue();

            if (threeDigitChunk > 0) {
                String chunkWords = convertThreeDigitChunkToWords(threeDigitChunk);
                if (scaleIndex > 0) {
                    words.insert(0, SCALE_WORDS[scaleIndex] + " ");
                }
                words.insert(0, chunkWords + " ");
            }

            remaining = divisionResult[0];
            scaleIndex++;
        }

        return words.toString().trim();
    }

    private static String convertThreeDigitChunkToWords(int number) {
        if (number < 20) {
            return UNIT_WORDS[number];
        }

        if (number < 100) {
            return TENS_WORDS[number / 10]
                + (number % 10 > 0 ? " " + UNIT_WORDS[number % 10] : "");
        }

        return UNIT_WORDS[number / 100]
            + " Hundred"
            + (number % 100 > 0 ? " " + convertThreeDigitChunkToWords(number % 100) : "");
    }
}