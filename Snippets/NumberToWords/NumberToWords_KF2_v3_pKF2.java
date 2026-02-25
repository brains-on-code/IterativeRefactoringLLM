package com.thealgorithms.conversions;

import java.math.BigDecimal;

/**
 * Utility class for converting numeric values to their English word representation.
 */
public final class NumberToWords {

    private NumberToWords() {
        // Utility class; prevent instantiation
    }

    /**
     * Word representations for numbers from 0 to 19.
     * Index corresponds directly to the number.
     */
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

    /**
     * Word representations for multiples of ten from 20 to 90.
     * Index corresponds to the tens digit (e.g., index 2 -> "Twenty").
     */
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

    /**
     * Scale words for powers of 1000.
     * Index corresponds to the power (1000^index).
     */
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

    /**
     * Converts a {@link BigDecimal} number to its English word representation.
     *
     * <p>Examples:
     * <ul>
     *   <li>123 -> "One Hundred Twenty Three"</li>
     *   <li>-45.67 -> "Negative Forty Five Point Six Seven"</li>
     * </ul>
     *
     * @param number the number to convert
     * @return the English word representation of the number,
     *         or "Invalid Input" if {@code number} is null
     */
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

    /**
     * Extracts the fractional part of a {@link BigDecimal} as a string of digits.
     *
     * <p>For example, for 0.456 it returns "456".
     * If the fractional part is zero or negative, an empty string is returned.
     *
     * @param fractional the fractional part of a number (0 <= fractional < 1)
     * @return the fractional digits as a string, or an empty string if there is no fractional part
     */
    private static String extractFractionalPart(BigDecimal fractional) {
        if (fractional.compareTo(BigDecimal.ZERO) <= 0) {
            return "";
        }
        return fractional.toPlainString().substring(2);
    }

    /**
     * Appends the word representation of the fractional part to the given {@link StringBuilder}.
     *
     * <p>Each digit is converted individually (e.g., "0.45" -> "Point Four Five").</p>
     *
     * @param result         the {@link StringBuilder} to append to
     * @param fractionalPart the fractional digits as a string
     */
    private static void appendFractionalPartWords(StringBuilder result, String fractionalPart) {
        result.append(POINT);
        for (char digit : fractionalPart.toCharArray()) {
            int digitValue = Character.getNumericValue(digit);
            result.append(" ").append(digitValue == 0 ? ZERO : UNITS[digitValue]);
        }
    }

    /**
     * Converts the whole (integer) part of a number to words.
     *
     * <p>The number is processed in chunks of three digits (thousands, millions, etc.).</p>
     *
     * @param number the non-negative whole number part
     * @return the English word representation of the whole number
     */
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

    /**
     * Converts a number from 0 to 999 into words.
     *
     * @param number the number to convert (0 <= number <= 999)
     * @return the English word representation of the number
     */
    private static String convertChunk(int number) {
        if (number < 20) {
            return UNITS[number];
        }

        if (number < 100) {
            return convertTens(number);
        }

        return convertHundreds(number);
    }

    /**
     * Converts a number from 20 to 99 into words.
     *
     * @param number the number to convert (20 <= number <= 99)
     * @return the English word representation of the number
     */
    private static String convertTens(int number) {
        int tensPart = number / 10;
        int unitsPart = number % 10;

        if (unitsPart == 0) {
            return TENS[tensPart];
        }

        return TENS[tensPart] + " " + UNITS[unitsPart];
    }

    /**
     * Converts a number from 100 to 999 into words.
     *
     * @param number the number to convert (100 <= number <= 999)
     * @return the English word representation of the number
     */
    private static String convertHundreds(int number) {
        int hundredsPart = number / 100;
        int remainder = number % 100;

        if (remainder == 0) {
            return UNITS[hundredsPart] + " Hundred";
        }

        return UNITS[hundredsPart] + " Hundred " + convertChunk(remainder);
    }
}