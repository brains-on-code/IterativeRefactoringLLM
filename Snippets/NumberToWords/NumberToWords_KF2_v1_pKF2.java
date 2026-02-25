package com.thealgorithms.conversions;

import java.math.BigDecimal;

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

    public static String convert(BigDecimal number) {
        if (number == null) {
            return "Invalid Input";
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

    private static String extractFractionalPart(BigDecimal fractional) {
        if (fractional.compareTo(BigDecimal.ZERO) <= 0) {
            return "";
        }
        return fractional.toPlainString().substring(2);
    }

    private static void appendFractionalPartWords(StringBuilder result, String fractionalPart) {
        result.append(POINT);
        for (char digit : fractionalPart.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            result.append(" ").append(digitValue == 0 ? ZERO : UNITS[digitValue]);
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
            return convertTens(number);
        }

        return convertHundreds(number);
    }

    private static String convertTens(int number) {
        int tensPart = number / 10;
        int unitsPart = number % 10;

        if (unitsPart == 0) {
            return TENS[tensPart];
        }

        return TENS[tensPart] + " " + UNITS[unitsPart];
    }

    private static String convertHundreds(int number) {
        int hundredsPart = number / 100;
        int remainder = number % 100;

        if (remainder == 0) {
            return UNITS[hundredsPart] + " Hundred";
        }

        return UNITS[hundredsPart] + " Hundred " + convertChunk(remainder);
    }
}