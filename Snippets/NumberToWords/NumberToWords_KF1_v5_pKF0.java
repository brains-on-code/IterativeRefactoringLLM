package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility class to convert numbers to their English word representation.
 */
public final class NumberToWordsConverter {

    private NumberToWordsConverter() {}

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
    private static final String INVALID_INPUT = "Invalid Input";

    private static final BigDecimal ONE_THOUSAND = BigDecimal.valueOf(1000);
    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal ZERO_DECIMAL = BigDecimal.ZERO;

    public static String convert(BigDecimal number) {
        if (number == null) {
            return INVALID_INPUT;
        }

        boolean isNegative = number.signum() < 0;
        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(ONE);
        BigDecimal integerPart = integerAndFraction[0];
        BigDecimal fractionalPart = integerAndFraction[1];

        String fractionalDigits = extractFractionalDigits(fractionalPart);

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }

        result.append(convertIntegerPart(integerPart));

        if (!fractionalDigits.isEmpty()) {
            appendFractionalPart(result, fractionalDigits);
        }

        return result.toString().trim();
    }

    private static String extractFractionalDigits(BigDecimal fractionalPart) {
        if (fractionalPart.compareTo(ZERO_DECIMAL) <= 0) {
            return "";
        }
        return fractionalPart.toPlainString().substring(2);
    }

    private static void appendFractionalPart(StringBuilder result, String fractionalDigits) {
        result.append(POINT);
        for (char digitChar : fractionalDigits.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            result.append(" ").append(digit == 0 ? ZERO : UNITS_AND_TEENS[digit]);
        }
    }

    private static String convertIntegerPart(BigDecimal number) {
        if (number.compareTo(ZERO_DECIMAL) == 0) {
            return ZERO;
        }

        StringBuilder words = new StringBuilder();
        int scaleIndex = 0;

        while (number.compareTo(ZERO_DECIMAL) > 0) {
            BigDecimal[] divisionResult = number.divideAndRemainder(ONE_THOUSAND);
            int chunk = divisionResult[1].intValue();

            if (chunk > 0) {
                prependChunkWithScale(words, chunk, scaleIndex);
            }

            number = divisionResult[0];
            scaleIndex++;
        }

        return words.toString().trim();
    }

    private static void prependChunkWithScale(StringBuilder words, int chunk, int scaleIndex) {
        String chunkWords = convertChunk(chunk);
        if (scaleIndex > 0) {
            words.insert(0, SCALE[scaleIndex] + " ");
        }
        words.insert(0, chunkWords + " ");
    }

    private static String convertChunk(int number) {
        if (number < 20) {
            return UNITS_AND_TEENS[number];
        }
        if (number < 100) {
            return convertTens(number);
        }
        return convertHundreds(number);
    }

    private static String convertTens(int number) {
        int tensPart = number / 10;
        int unitsPart = number % 10;

        String tensWord = TENS[tensPart];
        if (unitsPart == 0) {
            return tensWord;
        }

        return tensWord + " " + UNITS_AND_TEENS[unitsPart];
    }

    private static String convertHundreds(int number) {
        int hundredsPart = number / 100;
        int remainder = number % 100;

        String hundredsWord = UNITS_AND_TEENS[hundredsPart] + " Hundred";
        if (remainder == 0) {
            return hundredsWord;
        }

        return hundredsWord + " " + convertChunk(remainder);
    }
}