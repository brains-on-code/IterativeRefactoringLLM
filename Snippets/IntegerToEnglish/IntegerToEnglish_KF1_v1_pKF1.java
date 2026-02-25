package com.thealgorithms.conversions;

import java.util.Map;

/**
 * Utility class for converting integers to their English word representation.
 */
public final class NumberToWordsConverter {

    private static final Map<Integer, String> NUMBER_WORDS =
        Map.ofEntries(
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

    private static final Map<Integer, String> SCALE_WORDS =
        Map.ofEntries(
            Map.entry(1, "Thousand"),
            Map.entry(2, "Million"),
            Map.entry(3, "Billion")
        );

    private NumberToWordsConverter() {
    }

    /**
     * Converts a number in the range 0–999 to words.
     *
     * @param number a number between 0 and 999 (inclusive)
     * @return the English word representation of the number
     */
    private static String convertHundreds(int number) {
        int lastTwoDigits = number % 100;
        StringBuilder words = new StringBuilder();

        if (lastTwoDigits <= 20) {
            words.append(NUMBER_WORDS.get(lastTwoDigits));
        } else if (NUMBER_WORDS.containsKey(lastTwoDigits)) {
            words.append(NUMBER_WORDS.get(lastTwoDigits));
        } else {
            int tens = lastTwoDigits / 10;
            int ones = lastTwoDigits % 10;
            String tensWord = NUMBER_WORDS.getOrDefault(tens * 10, "");
            String onesWord = NUMBER_WORDS.getOrDefault(ones, "");
            words.append(tensWord);
            if (onesWord != null && !onesWord.isEmpty()) {
                words.append(" ").append(onesWord);
            }
        }

        int hundreds = number / 100;
        if (hundreds > 0) {
            if (words.length() > 0) {
                words.insert(0, " ");
            }
            words.insert(0, String.format("%s Hundred", NUMBER_WORDS.get(hundreds)));
        }

        return words.toString().trim();
    }

    /**
     * Converts an integer to its English word representation.
     *
     * @param number a number between 0 and 2,147,483,647 (inclusive)
     * @return the English word representation of the number
     */
    public static String convert(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();
        int scaleIndex = 0;

        while (number > 0) {
            int chunk = number % 1000;
            number /= 1000;

            if (chunk > 0) {
                String chunkWords = convertHundreds(chunk);
                if (!chunkWords.isEmpty()) {
                    if (scaleIndex > 0) {
                        chunkWords += " " + SCALE_WORDS.get(scaleIndex);
                    }
                    if (result.length() > 0) {
                        result.insert(0, " ");
                    }
                    result.insert(0, chunkWords);
                }
            }

            scaleIndex++;
        }

        return result.toString().trim();
    }
}