package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * A Java-based utility for converting numeric values into their English word
 * representations. Whether you need to convert a small number, a large number
 * with millions and billions, or even a number with decimal places, this utility
 * has you covered.
 */
public final class NumberToWords {

    private NumberToWords() {
        // Utility class; prevent instantiation
    }

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

    public static String convert(BigDecimal number) {
        if (number == null) {
            return INVALID_INPUT;
        }

        boolean isNegative = number.signum() < 0;
        BigDecimal[] parts = number.abs().divideAndRemainder(BigDecimal.ONE);

        BigDecimal wholePart = parts[0];
        String fractionalPart = extractFractionalPart(parts[1]);

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }

        result.append(convertWholeNumberToWords(wholePart));

        if (!fractionalPart.isEmpty()) {
            appendFractionalPartWords(result, fractionalPart);
        }

        return result.toString().trim();
    }

    private static String extractFractionalPart(BigDecimal fractionalPart) {
        if (fractionalPart.compareTo(BigDecimal.ZERO) <= 0) {
            return "";
        }
        // toPlainString() returns "0.xxx" for fractional part; substring(2) removes "0."
        return fractionalPart.toPlainString().substring(2);
    }

    private static void appendFractionalPartWords(StringBuilder result, String fractionalPart) {
        result.append(POINT);
        for (char digit : fractionalPart.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            result.append(" ")
                  .append(digitValue == 0 ? ZERO : UNITS[digitValue]);
        }
    }

    private static String convertWholeNumberToWords(BigDecimal number) {
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

    private static String convertChunk(int number) {
        if (number < 20) {
            return UNITS[number];
        }

        if (number < 100) {
            String tensPart = TENS[number / 10];
            String unitsPart = number % 10 > 0 ? " " + UNITS[number % 10] : "";
            return tensPart + unitsPart;
        }

        String hundredsPart = UNITS[number / 100] + " Hundred";
        int remainder = number % 100;

        if (remainder == 0) {
            return hundredsPart;
        }

        return hundredsPart + " " + convertChunk(remainder);
    }
}