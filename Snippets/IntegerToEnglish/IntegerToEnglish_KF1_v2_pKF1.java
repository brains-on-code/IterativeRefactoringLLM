package com.thealgorithms.conversions;

import java.util.Map;

/**
 * Utility class for converting integers to their English word representation.
 */
public final class NumberToWordsConverter {

    private static final Map<Integer, String> BASE_NUMBER_WORDS =
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

    private static final Map<Integer, String> SCALE_NUMBER_WORDS =
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
    private static String convertThreeDigitNumberToWords(int number) {
        int lastTwoDigits = number % 100;
        StringBuilder wordsBuilder = new StringBuilder();

        if (lastTwoDigits <= 20) {
            wordsBuilder.append(BASE_NUMBER_WORDS.get(lastTwoDigits));
        } else if (BASE_NUMBER_WORDS.containsKey(lastTwoDigits)) {
            wordsBuilder.append(BASE_NUMBER_WORDS.get(lastTwoDigits));
        } else {
            int tensDigit = lastTwoDigits / 10;
            int onesDigit = lastTwoDigits % 10;
            String tensWord = BASE_NUMBER_WORDS.getOrDefault(tensDigit * 10, "");
            String onesWord = BASE_NUMBER_WORDS.getOrDefault(onesDigit, "");
            wordsBuilder.append(tensWord);
            if (!onesWord.isEmpty()) {
                wordsBuilder.append(" ").append(onesWord);
            }
        }

        int hundredsDigit = number / 100;
        if (hundredsDigit > 0) {
            if (wordsBuilder.length() > 0) {
                wordsBuilder.insert(0, " ");
            }
            wordsBuilder.insert(0, String.format("%s Hundred", BASE_NUMBER_WORDS.get(hundredsDigit)));
        }

        return wordsBuilder.toString().trim();
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

        StringBuilder resultBuilder = new StringBuilder();
        int scaleGroupIndex = 0;

        while (number > 0) {
            int threeDigitGroup = number % 1000;
            number /= 1000;

            if (threeDigitGroup > 0) {
                String groupWords = convertThreeDigitNumberToWords(threeDigitGroup);
                if (!groupWords.isEmpty()) {
                    if (scaleGroupIndex > 0) {
                        groupWords += " " + SCALE_NUMBER_WORDS.get(scaleGroupIndex);
                    }
                    if (resultBuilder.length() > 0) {
                        resultBuilder.insert(0, " ");
                    }
                    resultBuilder.insert(0, groupWords);
                }
            }

            scaleGroupIndex++;
        }

        return resultBuilder.toString().trim();
    }
}