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
        String fractionalPartDigits =
            integerAndFractionParts[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFractionParts[1].toPlainString().substring(2)
                : "";

        StringBuilder wordsBuilder = new StringBuilder();
        if (isNegative) {
            wordsBuilder.append(NEGATIVE_PREFIX);
        }
        wordsBuilder.append(convertIntegerPartToWords(integerPart));

        if (!fractionalPartDigits.isEmpty()) {
            wordsBuilder.append(POINT_WORD);
            for (char digitChar : fractionalPartDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                wordsBuilder.append(" ").append(digit == 0 ? ZERO_WORD : UNIT_WORDS[digit]);
            }
        }

        return wordsBuilder.toString().trim();
    }

    private static String convertIntegerPartToWords(BigDecimal integerPart) {
        if (integerPart.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_WORD;
        }

        StringBuilder integerWordsBuilder = new StringBuilder();
        int scaleIndex = 0;

        BigDecimal remainingValue = integerPart;
        while (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = remainingValue.divideAndRemainder(BigDecimal.valueOf(1000));
            int threeDigitGroupValue = divisionResult[1].intValue();

            if (threeDigitGroupValue > 0) {
                String threeDigitGroupWords = convertThreeDigitGroupToWords(threeDigitGroupValue);
                if (scaleIndex > 0) {
                    integerWordsBuilder.insert(0, SCALE_WORDS[scaleIndex] + " ");
                }
                integerWordsBuilder.insert(0, threeDigitGroupWords + " ");
            }

            remainingValue = divisionResult[0];
            scaleIndex++;
        }

        return integerWordsBuilder.toString().trim();
    }

    private static String convertThreeDigitGroupToWords(int threeDigitNumber) {
        if (threeDigitNumber < 20) {
            return UNIT_WORDS[threeDigitNumber];
        }

        if (threeDigitNumber < 100) {
            int tensDigit = threeDigitNumber / 10;
            int unitDigit = threeDigitNumber % 10;
            return TENS_WORDS[tensDigit] + (unitDigit > 0 ? " " + UNIT_WORDS[unitDigit] : "");
        }

        int hundredsDigit = threeDigitNumber / 100;
        int remainder = threeDigitNumber % 100;

        return UNIT_WORDS[hundredsDigit]
            + " Hundred"
            + (remainder > 0 ? " " + convertThreeDigitGroupToWords(remainder) : "");
    }
}