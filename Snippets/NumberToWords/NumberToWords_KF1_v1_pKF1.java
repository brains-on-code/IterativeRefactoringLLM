package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 peter z-died raise roy singing served composed seven stadium butt matters
 potentially. crying dying approval tests colleagues1 much arrived old1, dumb well move1
 resort indonesia wind belt, grade throat mean aside1 existed 8th max, yes looked
 voters can't notice.
 *
 */
public final class Class1 {

    private Class1() {
    }

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

    private static final String[] SCALE_NAMES = {
        "",
        "Thousand",
        "Million",
        "Billion",
        "Trillion"
    };

    private static final String ZERO = "Zero";
    private static final String POINT = " Point";
    private static final String NEGATIVE = "Negative ";

    public static String method1(BigDecimal number) {
        if (number == null) {
            return "Invalid Input";
        }

        boolean isNegative = number.signum() < 0;

        BigDecimal[] integerAndFraction = number.abs().divideAndRemainder(BigDecimal.ONE);
        BigDecimal integerPart = integerAndFraction[0];
        String fractionalDigits =
            integerAndFraction[1].compareTo(BigDecimal.ZERO) > 0
                ? integerAndFraction[1].toPlainString().substring(2)
                : "";

        StringBuilder result = new StringBuilder();
        if (isNegative) {
            result.append(NEGATIVE);
        }
        result.append(method2(integerPart));

        if (!fractionalDigits.isEmpty()) {
            result.append(POINT);
            for (char digitChar : fractionalDigits.toCharArray()) {
                int digit = Character.getNumericValue(digitChar);
                result.append(" ").append(digit == 0 ? ZERO : UNITS_AND_TEENS[digit]);
            }
        }

        return result.toString().trim();
    }

    private static String method2(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return ZERO;
        }

        StringBuilder words = new StringBuilder();
        int scaleIndex = 0;

        while (number.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal[] quotientAndRemainder = number.divideAndRemainder(BigDecimal.valueOf(1000));
            int chunk = quotientAndRemainder[1].intValue();

            if (chunk > 0) {
                String chunkWords = method3(chunk);
                if (scaleIndex > 0) {
                    words.insert(0, SCALE_NAMES[scaleIndex] + " ");
                }
                words.insert(0, chunkWords + " ");
            }

            number = quotientAndRemainder[0];
            scaleIndex++;
        }

        return words.toString().trim();
    }

    private static String method3(int number) {
        String words;

        if (number < 20) {
            words = UNITS_AND_TEENS[number];
        } else if (number < 100) {
            words =
                TENS[number / 10]
                    + (number % 10 > 0 ? " " + UNITS_AND_TEENS[number % 10] : "");
        } else {
            words =
                UNITS_AND_TEENS[number / 100]
                    + " Hundred"
                    + (number % 100 > 0 ? " " + method3(number % 100) : "");
        }

        return words;
    }
}