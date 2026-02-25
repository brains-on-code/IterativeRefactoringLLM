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
            Map.entry(90, "Ninety")
        );

    private static final Map<Integer, String> SCALE_WORDS =
        Map.ofEntries(
            Map.entry(1, "Thousand"),
            Map.entry(2, "Million"),
            Map.entry(3, "Billion")
        );

    private NumberToWordsConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a number in the range 0–999 to words.
     *
     * @param number the number to convert (0–999)
     * @return the English word representation of the number
     */
    private static String convertBelowThousand(int number) {
        if (number == 0) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        int hundreds = number / 100;
        int lastTwoDigits = number % 100;

        appendHundreds(result, hundreds);
        appendLastTwoDigits(result, lastTwoDigits);

        return result.toString().trim();
    }

    private static void appendHundreds(StringBuilder result, int hundreds) {
        if (hundreds <= 0) {
            return;
        }
        result.append(NUMBER_WORDS.get(hundreds)).append(" Hundred");
    }

    private static void appendLastTwoDigits(StringBuilder result, int lastTwoDigits) {
        if (lastTwoDigits <= 0) {
            return;
        }

        if (result.length() > 0) {
            result.append(" ");
        }

        if (lastTwoDigits <= 20 || NUMBER_WORDS.containsKey(lastTwoDigits)) {
            result.append(NUMBER_WORDS.get(lastTwoDigits));
            return;
        }

        int tens = (lastTwoDigits / 10) * 10;
        int ones = lastTwoDigits % 10;

        result.append(NUMBER_WORDS.getOrDefault(tens, ""));
        if (ones > 0) {
            result.append(" ").append(NUMBER_WORDS.getOrDefault(ones, ""));
        }
    }

    /**
     * Converts a non-negative integer to its English word representation.
     *
     * @param number the number to convert (0–2,147,483,647)
     * @return the English word representation of the number
     */
    public static String toWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();
        int scaleIndex = 0;

        while (number > 0) {
            int chunk = number % 1000;
            number /= 1000;

            if (chunk > 0) {
                prependChunkWithScale(result, chunk, scaleIndex);
            }

            scaleIndex++;
        }

        return result.toString().trim();
    }

    private static void prependChunkWithScale(StringBuilder result, int chunk, int scaleIndex) {
        String chunkWords = convertBelowThousand(chunk);
        String scaleWord = SCALE_WORDS.get(scaleIndex);

        if (scaleWord != null && !scaleWord.isEmpty()) {
            chunkWords = chunkWords + " " + scaleWord;
        }

        if (result.length() > 0) {
            result.insert(0, " ");
        }
        result.insert(0, chunkWords);
    }
}