package com.thealgorithms.conversions;

import java.util.Map;

/**
 * Utility class to convert integers to their English word representation.
 *
 * <p>Supports conversion of numbers from 0 to 2,147,483,647
 * (the maximum value of a 32-bit signed integer). The number is split
 * into groups of three digits (thousands, millions, billions, etc.),
 * and each group is translated into words.</p>
 *
 * <h2>Example Usage</h2>
 * <pre>
 *   IntegerToEnglish.integerToEnglishWords(12345);
 *   // "Twelve Thousand Three Hundred Forty Five"
 * </pre>
 */
public final class IntegerToEnglish {

    /**
     * Base number words:
     * - 0–20
     * - multiples of 10 up to 90
     * - 100 ("Hundred")
     */
    private static final Map<Integer, String> BASE_NUMBERS_MAP = Map.ofEntries(
        Map.entry(0, ""),
        Map.entry(1, "One"),
        Map.entry(2, "Two"),
        Map.entry(3, "Three"),
        Map.entry(4, "Four"),
        Map.entry(5, "Five"),
        Map.entry(6, "Six"),
        Map.entry(7, "Seven"),
        Map.entry(8, "Eight"),
        Map.entry(9, "Nine"),
        Map.entry(10, "Ten"),
        Map.entry(11, "Eleven"),
        Map.entry(12, "Twelve"),
        Map.entry(13, "Thirteen"),
        Map.entry(14, "Fourteen"),
        Map.entry(15, "Fifteen"),
        Map.entry(16, "Sixteen"),
        Map.entry(17, "Seventeen"),
        Map.entry(18, "Eighteen"),
        Map.entry(19, "Nineteen"),
        Map.entry(20, "Twenty"),
        Map.entry(30, "Thirty"),
        Map.entry(40, "Forty"),
        Map.entry(50, "Fifty"),
        Map.entry(60, "Sixty"),
        Map.entry(70, "Seventy"),
        Map.entry(80, "Eighty"),
        Map.entry(90, "Ninety"),
        Map.entry(100, "Hundred")
    );

    /**
     * Scale words for groups of three digits (powers of 1000).
     * Index is the group position:
     * 1 -> Thousand, 2 -> Million, 3 -> Billion.
     */
    private static final Map<Integer, String> THOUSAND_POWER_MAP = Map.ofEntries(
        Map.entry(1, "Thousand"),
        Map.entry(2, "Million"),
        Map.entry(3, "Billion")
    );

    private IntegerToEnglish() {
        // Prevent instantiation.
    }

    /**
     * Converts a non-negative integer to its English word representation.
     *
     * @param number the integer to convert (0-2,147,483,647)
     * @return the English word representation of the input number
     */
    public static String integerToEnglishWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();
        int thousandGroupIndex = 0;

        while (number > 0) {
            int groupValue = number % 1000;
            number /= 1000;

            if (groupValue > 0) {
                String groupWords = convertToWords(groupValue);
                if (!groupWords.isEmpty()) {
                    if (thousandGroupIndex > 0) {
                        groupWords += " " + THOUSAND_POWER_MAP.get(thousandGroupIndex);
                    }
                    if (result.length() > 0) {
                        result.insert(0, " ");
                    }
                    result.insert(0, groupWords);
                }
            }

            thousandGroupIndex++;
        }

        return result.toString().trim();
    }

    /**
     * Converts a number in the range [0, 999] into English words.
     *
     * @param number the integer value (0-999) to convert
     * @return the English word representation of the input number
     */
    private static String convertToWords(int number) {
        StringBuilder result = new StringBuilder();

        int remainder = number % 100;
        appendTensAndOnes(result, remainder);

        int hundredsDigit = number / 100;
        if (hundredsDigit > 0) {
            String hundredsPart =
                BASE_NUMBERS_MAP.get(hundredsDigit) + " " + BASE_NUMBERS_MAP.get(100);
            if (result.length() > 0) {
                result.insert(0, " ");
            }
            result.insert(0, hundredsPart);
        }

        return result.toString().trim();
    }

    /**
     * Appends the English words for the tens and ones place of a number (0-99).
     *
     * @param builder the StringBuilder to append to
     * @param number  the integer value (0-99)
     */
    private static void appendTensAndOnes(StringBuilder builder, int number) {
        if (number == 0) {
            return;
        }

        if (number <= 20 || BASE_NUMBERS_MAP.containsKey(number)) {
            builder.append(BASE_NUMBERS_MAP.get(number));
            return;
        }

        int tensDigit = number / 10;
        int onesDigit = number % 10;

        String tens = BASE_NUMBERS_MAP.getOrDefault(tensDigit * 10, "");
        String ones = BASE_NUMBERS_MAP.getOrDefault(onesDigit, "");

        builder.append(tens);
        if (!ones.isEmpty()) {
            builder.append(" ").append(ones);
        }
    }
}