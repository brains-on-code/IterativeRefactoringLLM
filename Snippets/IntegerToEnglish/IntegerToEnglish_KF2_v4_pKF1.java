package com.thealgorithms.conversions;

import java.util.Map;

public final class IntegerToEnglish {

    private static final Map<Integer, String> NUMBER_TO_WORD = Map.ofEntries(
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

    private static final Map<Integer, String> SCALE_WORDS = Map.ofEntries(
        Map.entry(1, "Thousand"),
        Map.entry(2, "Million"),
        Map.entry(3, "Billion")
    );

    private IntegerToEnglish() {
    }

    private static String convertThreeDigitNumberToWords(int threeDigitNumber) {
        int lastTwoDigits = threeDigitNumber % 100;
        StringBuilder wordsBuilder = new StringBuilder();

        if (lastTwoDigits <= 20 || NUMBER_TO_WORD.containsKey(lastTwoDigits)) {
            wordsBuilder.append(NUMBER_TO_WORD.get(lastTwoDigits));
        } else {
            int tens = lastTwoDigits / 10;
            int ones = lastTwoDigits % 10;

            String tensWord = NUMBER_TO_WORD.getOrDefault(tens * 10, "");
            String onesWord = NUMBER_TO_WORD.getOrDefault(ones, "");

            wordsBuilder.append(tensWord);
            if (!onesWord.isEmpty()) {
                wordsBuilder.append(" ").append(onesWord);
            }
        }

        int hundreds = threeDigitNumber / 100;
        if (hundreds > 0) {
            if (wordsBuilder.length() > 0) {
                wordsBuilder.insert(0, " ");
            }
            wordsBuilder.insert(0, String.format("%s Hundred", NUMBER_TO_WORD.get(hundreds)));
        }

        return wordsBuilder.toString().trim();
    }

    public static String integerToEnglishWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder resultBuilder = new StringBuilder();
        int scaleIndex = 0;

        while (number > 0) {
            int currentThreeDigitNumber = number % 1000;
            number /= 1000;

            if (currentThreeDigitNumber > 0) {
                String groupWords = convertThreeDigitNumberToWords(currentThreeDigitNumber);
                if (!groupWords.isEmpty()) {
                    if (scaleIndex > 0) {
                        groupWords += " " + SCALE_WORDS.get(scaleIndex);
                    }
                    if (resultBuilder.length() > 0) {
                        resultBuilder.insert(0, " ");
                    }
                    resultBuilder.insert(0, groupWords);
                }
            }

            scaleIndex++;
        }

        return resultBuilder.toString().trim();
    }
}