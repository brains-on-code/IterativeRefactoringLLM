package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility class for converting numeric values to their English word representation.
 */
public final class NumberToWordsConverter {

    private NumberToWordsConverter() {
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
    private static final String INVALID_INPUT = "Invalid Input";

    /**
     * Converts a BigDecimal number to its English word representation.
     *
     * @param number the number to convert
     * @return the English word representation of the number, or "Invalid Input" if null
     */
    public static String toWords(BigDecimal number) {
        if (number == null) {
            return INVALID_INPUT;
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        String fractionPart = extractFractionPart(integerAndFraction[1]);

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }
        result.append(convertIntegerPartToWords(integerPart));

        if (!fractionPart.isEmpty()) {
            appendFractionWords(result, fractionPart);
        }

        return result.toString().trim();
    }

    /**
     * Extracts the fractional part of a BigDecimal as a string of digits.
     *
     * @param fraction the fractional part (0 <= fraction < 1)
     * @return the fractional digits as a string, or an empty string if there is no fraction
     */
    private static String extractFractionPart(BigDecimal fraction) {
        if (fraction.compareTo(BigDecimal.ZERO) <= 0) {
            return "";
        }
        return fraction.toPlainString().substring(2);
    }

    /**
     * Appends the word representation of the fractional part to the result.
     *
     * @param result       the StringBuilder to append to
     * @param fractionPart the fractional digits as a string
     */
    private static void appendFractionWords(StringBuilder result, String fractionPart) {
        result.append(POINT);
        for (char digitChar : fractionPart.toCharArray()) {
            int digit = Character.getNumericValue(digitChar);
            result.append(" ").append(digit == 0 ? ZERO : NUMBER_WORDS_0_TO_19[digit]);
        }
    }

    /**
     * Converts the integer part of a number to words, handling thousands, millions, etc.
     *
     * @param number the non-negative integer part as BigDecimal
     * @return the English word representation of the integer part
     */
    private static String convertIntegerPartToWords(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder result = new StringBuilder();
        int scaleIndex = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divided = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = divided[1].intValue();

            if (chunk > 0) {
                String chunkWords = convertChunkToWords(chunk);
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
    private static String convertChunkToWords(int number) {
        if (number < 20) {
            return NUMBER_WORDS_0_TO_19[number];
        }
        if (number < 100) {
            return convertTwoDigitNumberToWords(number);
        }
        return convertThreeDigitNumberToWords(number);
    }

    /**
     * Converts a two-digit number (20–99) into words.
     *
     * @param number the number to convert
     * @return the English word representation of the number
     */
    private static String convertTwoDigitNumberToWords(int number) {
        String tens = TENS_WORDS[number / 10];
        int units = number % 10;
        if (units == 0) {
            return tens;
        }
        return tens + " " + NUMBER_WORDS_0_TO_19[units];
    }

    /**
     * Converts a three-digit number (100–999) into words.
     *
     * @param number the number to convert
     * @return the English word representation of the number
     */
    private static String convertThreeDigitNumberToWords(int number) {
        String hundreds = NUMBER_WORDS_0_TO_19[number / 100] + " Hundred";
        int remainder = number % 100;
        if (remainder == 0) {
            return hundreds;
        }
        return hundreds + " " + convertChunkToWords(remainder);
    }
}