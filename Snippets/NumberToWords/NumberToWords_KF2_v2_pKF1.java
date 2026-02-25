package com.thealgorithms.conversions;

import java.math.BigDecimal;

public final class NumberToWords {

    private NumberToWords() {}

    private static final String[] UNIT_NAMES = {
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

    private static final String[] TENS_NAMES = {
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

    private static final String[] SCALE_NAMES = {
        "",
        "Thousand",
        "Million",
        "Billion",
        "Trillion"
    };

    private static final String ZERO_NAME = "Zero";
    private static final String POINT_NAME = " Point";
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
            wordsBuilder.append(POINT_NAME);
            for (char digitChar : fractionalPartDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                wordsBuilder.append(" ").append(digit == 0 ? ZERO_NAME : UNIT_NAMES[digit]);
            }
        }

        return wordsBuilder.toString().trim();
    }

    private static String convertIntegerPartToWords(BigDecimal integerPart) {
        if (integerPart.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO_NAME;
        }

        StringBuilder integerWordsBuilder = new StringBuilder();
        int scaleIndex = 0;
        BigDecimal remainingValue = integerPart;

        while (remainingValue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] divisionResult = remainingValue.divideAndRemainder(BigDecimal.valueOf(1000));
            int currentThreeDigitGroup = divisionResult[1].intValue();

            if (currentThreeDigitGroup > 0) {
                String groupWords = convertThreeDigitGroupToWords(currentThreeDigitGroup);
                if (scaleIndex > 0) {
                    integerWordsBuilder.insert(0, SCALE_NAMES[scaleIndex] + " ");
                }
                integerWordsBuilder.insert(0, groupWords + " ");
            }

            remainingValue = divisionResult[0];
            scaleIndex++;
        }

        return integerWordsBuilder.toString().trim();
    }

    private static String convertThreeDigitGroupToWords(int threeDigitNumber) {
        if (threeDigitNumber < 20) {
            return UNIT_NAMES[threeDigitNumber];
        }

        if (threeDigitNumber < 100) {
            String tensWord = TENS_NAMES[threeDigitNumber / 10];
            String unitWord = threeDigitNumber % 10 > 0 ? " " + UNIT_NAMES[threeDigitNumber % 10] : "";
            return tensWord + unitWord;
        }

        String hundredsWord = UNIT_NAMES[threeDigitNumber / 100] + " Hundred";
        String remainderWords =
            threeDigitNumber % 100 > 0 ? " " + convertThreeDigitGroupToWords(threeDigitNumber % 100) : "";
        return hundredsWord + remainderWords;
    }
}