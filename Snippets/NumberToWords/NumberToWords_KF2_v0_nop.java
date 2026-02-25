package com.thealgorithms.conversions;

import java.math.BigDecimal;


public final class NumberToWords {

    private NumberToWords() {
    }

    private static final String[] UNITS = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};

    private static final String[] TENS = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

    private static final String[] POWERS = {"", "Thousand", "Million", "Billion", "Trillion"};

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
        String fractionalPartStr = parts[1].compareTo(BigDecimal.ZERO) > 0 ? parts[1].toPlainString().substring(2) : "";

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }
        result.append(convertWholeNumberToWords(wholePart));

        if (!fractionalPartStr.isEmpty()) {
            result.append(POINT);
            for (char digit : fractionalPartStr.toCharArray()) {
                int digitValue = Character.getNumericValue(digit);
                result.append(" ").append(digitValue == 0 ? ZERO : UNITS[digitValue]);
            }
        }

        return result.toString().trim();
    }

    private static String convertWholeNumberToWords(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder words = new StringBuilder();
        int power = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = divisionResult[1].intValue();

            if (chunk > 0) {
                String chunkWords = convertChunk(chunk);
                if (power > 0) {
                    words.insert(0, POWERS[power] + " ");
                }
                words.insert(0, chunkWords + " ");
            }

            number = divisionResult[0];
            power++;
        }

        return words.toString().trim();
    }

    private static String convertChunk(int number) {
        String chunkWords;

        if (number < 20) {
            chunkWords = UNITS[number];
        } else if (number < 100) {
            chunkWords = TENS[number / 10] + (number % 10 > 0 ? " " + UNITS[number % 10] : "");
        } else {
            chunkWords = UNITS[number / 100] + " Hundred" + (number % 100 > 0 ? " " + convertChunk(number % 100) : "");
        }

        return chunkWords;
    }
}
