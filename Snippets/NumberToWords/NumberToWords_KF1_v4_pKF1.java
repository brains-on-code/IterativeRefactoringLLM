package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Converts numeric values to their English word representation.
 */
public final class NumberToWordsConverter {

    private NumberToWordsConverter() {
    }

    private static final String[] UNIT_AND_TEEN_WORDS = {
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

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE_PREFIX);
        }
        result.append(convertIntegerPart(integerPart));

        if (!fractionalPartDigits.isEmpty()) {
            result.append(POINT_WORD);
            for (char digitChar : fractionalPartDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                result.append(" ").append(digit == 0 ? ZERO_WORD : UNIT_AND_TEEN_WORDS[digit]);
            }
        }

        return result.toString().trim();
    }

    private static String convertIntegerPart(BigDecimal integerPart) {
        if (integerPart.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_WORD;
        }

        StringBuilder integerWords = new StringBuilder();
        int scaleIndex = 0;

        BigDecimal remainingValue = integerPart;
        while (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] quotientAndRemainder = remainingValue.divideAndRemainder(BigDecimal.valueOf(1000));
            int currentThreeDigitGroup = quotientAndRemainder[1].intValue();

            if (currentThreeDigitGroup > 0) {
                String groupWords = convertThreeDigitGroup(currentThreeDigitGroup);
                if (scaleIndex > 0) {
                    integerWords.insert(0, SCALE_WORDS[scaleIndex] + " ");
                }
                integerWords.insert(0, groupWords + " ");
            }

            remainingValue = quotientAndRemainder[0];
            scaleIndex++;
        }

        return integerWords.toString().trim();
    }

    private static String convertThreeDigitGroup(int threeDigitNumber) {
        String words;

        if (threeDigitNumber < 20) {
            words = UNIT_AND_TEEN_WORDS[threeDigitNumber];
        } else if (threeDigitNumber < 100) {
            words =
                TENS_WORDS[threeDigitNumber / 10]
                    + (threeDigitNumber % 10 > 0 ? " " + UNIT_AND_TEEN_WORDS[threeDigitNumber % 10] : "");
        } else {
            words =
                UNIT_AND_TEEN_WORDS[threeDigitNumber / 100]
                    + " Hundred"
                    + (threeDigitNumber % 100 > 0 ? " " + convertThreeDigitGroup(threeDigitNumber % 100) : "");
        }

        return words;
    }
}