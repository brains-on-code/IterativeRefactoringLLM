package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility for converting numeric values into their English word representations.
 */
public final class NumberToWords {

    private NumberToWords() {}

    private static final String[] UNITS = {
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

    private static final String[] POWERS = {
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

    /**
     * Converts a BigDecimal number to its English word representation.
     *
     * @param number the number to convert
     * @return the English word representation of the number
     */
    public static String convert(BigDecimal number) {
        if (number == null) {
            return INVALID_INPUT;
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        String fractionDigits =
            integerAndFraction[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFraction[1].toPlainString().substring(2)
                : "";

        StringBuilder result = new StringBuilder();

        if (isNegative) {
            result.append(NEGATIVE);
        }

        result.append(convertIntegerPartToWords(integerPart));

        if (!fractionDigits.isEmpty()) {
            result.append(POINT);
            appendFractionDigitsAsWords(result, fractionDigits);
        }

        return result.toString().trim();
    }

    /**
     * Appends the word representation of the fractional digits (after the decimal point).
     *
     * @param result        the StringBuilder to append to
     * @param fractionDigits the fractional digits as a string
     */
    private static void appendFractionDigitsAsWords(StringBuilder result, String fractionDigits) {
        for (char digit : fractionDigits.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            result
                .append(" ")
                .append(digitValue == 0 ? ZERO : UNITS[digitValue]);
        }
    }

    /**
     * Converts the integer part of a number to words.
     *
     * @param number the integer part as a BigDecimal
     * @return the English word representation of the integer part
     */
    private static String convertIntegerPartToWords(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder words = new StringBuilder();
        int powerIndex = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = divisionResult[1].intValue();

            if (chunk > 0) {
                String chunkWords = convertChunk(chunk);
                if (powerIndex > 0) {
                    words.insert(0, POWERS[powerIndex] + " ");
                }
                words.insert(0, chunkWords + " ");
            }

            number = divisionResult[0];
            powerIndex++;
        }

        return words.toString().trim();
    }

    /**
     * Converts a number from 0 to 999 into words.
     *
     * @param number the number to convert (0–999)
     * @return the English word representation of the number
     */
    private static String convertChunk(int number) {
        if (number < 20) {
            return UNITS[number];
        }

        if (number < 100) {
            return TENS[number / 10]
                + (number % 10 > 0 ? " " + UNITS[number % 10] : "");
        }

        return UNITS[number / 100]
            + " Hundred"
            + (number % 100 > 0 ? " " + convertChunk(number % 100) : "");
    }
}