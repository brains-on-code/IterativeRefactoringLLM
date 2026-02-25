package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * A Java-based utility for converting numeric values into their English word
 * representations. Whether you need to convert a small number, a large number
 * with millions and billions, or even a number with decimal places, this utility
 * has you covered.
 */
public final class NumberToWords {

    private NumberToWords() {}

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

        BigDecimal[] integerAndFractionParts = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFractionParts[0];
        String fractionalDigits =
            integerAndFractionParts[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFractionParts[1].toPlainString().substring(2)
                : "";

        StringBuilder wordResult = new StringBuilder();
        if (isNegative) {
            wordResult.append(NEGATIVE_PREFIX);
        }
        wordResult.append(convertIntegerPartToWords(integerPart));

        if (!fractionalDigits.isEmpty()) {
            wordResult.append(POINT_WORD);
            for (char digitChar : fractionalDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                wordResult.append(" ").append(digit == 0 ? ZERO_WORD : UNIT_WORDS[digit]);
            }
        }

        return wordResult.toString().trim();
    }

    private static String convertIntegerPartToWords(BigDecimal integerPart) {
        if (integerPart.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_WORD;
        }

        StringBuilder integerWords = new StringBuilder();
        int scaleIndex = 0;

        BigDecimal remainingValue = integerPart;
        while (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = remainingValue.divideAndRemainder(BigDecimal.valueOf(1000));
            int threeDigitGroup = divisionResult[1].intValue();

            if (threeDigitGroup > 0) {
                String groupWords = convertThreeDigitGroupToWords(threeDigitGroup);
                if (scaleIndex > 0) {
                    integerWords.insert(0, SCALE_WORDS[scaleIndex] + " ");
                }
                integerWords.insert(0, groupWords + " ");
            }

            remainingValue = divisionResult[0];
            scaleIndex++;
        }

        return integerWords.toString().trim();
    }

    private static String convertThreeDigitGroupToWords(int threeDigitNumber) {
        if (threeDigitNumber < 20) {
            return UNIT_WORDS[threeDigitNumber];
        }

        if (threeDigitNumber < 100) {
            return TENS_WORDS[threeDigitNumber / 10]
                + (threeDigitNumber % 10 > 0 ? " " + UNIT_WORDS[threeDigitNumber % 10] : "");
        }

        return UNIT_WORDS[threeDigitNumber / 100]
            + " Hundred"
            + (threeDigitNumber % 100 > 0 ? " " + convertThreeDigitGroupToWords(threeDigitNumber % 100) : "");
    }
}