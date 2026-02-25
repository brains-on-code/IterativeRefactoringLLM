package com.thealgorithms.conversions;

import java.util.Map;

/**
 * Utility class for converting integers to their English word representation.
 */
public final class NumberToWordsConverter {

    private static final Map<Integer, String> BASIC_NUMBER_WORDS =
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

    private static final Map<Integer, String> LARGE_NUMBER_SCALES =
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
     * @param threeDigitNumber a number between 0 and 999 (inclusive)
     * @return the English word representation of the number
     */
    private static String convertThreeDigitNumberToWords(int threeDigitNumber) {
        int lastTwoDigits = threeDigitNumber % 100;
        StringBuilder wordBuilder = new StringBuilder();

        if (lastTwoDigits <= 20) {
            wordBuilder.append(BASIC_NUMBER_WORDS.get(lastTwoDigits));
        } else if (BASIC_NUMBER_WORDS.containsKey(lastTwoDigits)) {
            wordBuilder.append(BASIC_NUMBER_WORDS.get(lastTwoDigits));
        } else {
            int tensDigit = lastTwoDigits / 10;
            int onesDigit = lastTwoDigits % 10;
            String tensWord = BASIC_NUMBER_WORDS.getOrDefault(tensDigit * 10, "");
            String onesWord = BASIC_NUMBER_WORDS.getOrDefault(onesDigit, "");
            wordBuilder.append(tensWord);
            if (!onesWord.isEmpty()) {
                wordBuilder.append(" ").append(onesWord);
            }
        }

        int hundredsDigit = threeDigitNumber / 100;
        if (hundredsDigit > 0) {
            if (wordBuilder.length() > 0) {
                wordBuilder.insert(0, " ");
            }
            wordBuilder.insert(0, String.format("%s Hundred", BASIC_NUMBER_WORDS.get(hundredsDigit)));
        }

        return wordBuilder.toString().trim();
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

        StringBuilder fullNumberInWords = new StringBuilder();
        int scaleGroupIndex = 0;

        while (number > 0) {
            int currentThreeDigitGroup = number % 1000;
            number /= 1000;

            if (currentThreeDigitGroup > 0) {
                String groupInWords = convertThreeDigitNumberToWords(currentThreeDigitGroup);
                if (!groupInWords.isEmpty()) {
                    if (scaleGroupIndex > 0) {
                        groupInWords += " " + LARGE_NUMBER_SCALES.get(scaleGroupIndex);
                    }
                    if (fullNumberInWords.length() > 0) {
                        fullNumberInWords.insert(0, " ");
                    }
                    fullNumberInWords.insert(0, groupInWords);
                }
            }

            scaleGroupIndex++;
        }

        return fullNumberInWords.toString().trim();
    }
}