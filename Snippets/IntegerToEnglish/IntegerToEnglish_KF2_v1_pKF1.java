package com.thealgorithms.conversions;

import java.util.Map;

public final class IntegerToEnglish {

    private static final Map<Integer, String> NUMBER_WORDS = Map.ofEntries(
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

    private static final Map<Integer, String> THOUSAND_SCALE_WORDS = Map.ofEntries(
        Map.entry(1, "Thousand"),
        Map.entry(2, "Million"),
        Map.entry(3, "Billion")
    );

    private IntegerToEnglish() {
    }

    private static String convertThreeDigitGroupToWords(int threeDigitGroup) {
        int lastTwoDigits = threeDigitGroup % 100;
        StringBuilder wordsBuilder = new StringBuilder();

        if (lastTwoDigits <= 20) {
            wordsBuilder.append(NUMBER_WORDS.get(lastTwoDigits));
        } else if (NUMBER_WORDS.containsKey(lastTwoDigits)) {
            wordsBuilder.append(NUMBER_WORDS.get(lastTwoDigits));
        } else {
            int tensValue = lastTwoDigits / 10;
            int onesValue = lastTwoDigits % 10;

            String tensWord = NUMBER_WORDS.getOrDefault(tensValue * 10, "");
            String onesWord = NUMBER_WORDS.getOrDefault(onesValue, "");

            wordsBuilder.append(tensWord);
            if (onesWord != null && !onesWord.isEmpty()) {
                wordsBuilder.append(" ").append(onesWord);
            }
        }

        int hundredsValue = threeDigitGroup / 100;
        if (hundredsValue > 0) {
            if (wordsBuilder.length() > 0) {
                wordsBuilder.insert(0, " ");
            }
            wordsBuilder.insert(0, String.format("%s Hundred", NUMBER_WORDS.get(hundredsValue)));
        }

        return wordsBuilder.toString().trim();
    }

    public static String integerToEnglishWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder fullWordsBuilder = new StringBuilder();
        int thousandGroupIndex = 0;

        while (number > 0) {
            int currentThreeDigitGroup = number % 1000;
            number /= 1000;

            if (currentThreeDigitGroup > 0) {
                String groupWords = convertThreeDigitGroupToWords(currentThreeDigitGroup);
                if (!groupWords.isEmpty()) {
                    if (thousandGroupIndex > 0) {
                        groupWords += " " + THOUSAND_SCALE_WORDS.get(thousandGroupIndex);
                    }
                    if (fullWordsBuilder.length() > 0) {
                        fullWordsBuilder.insert(0, " ");
                    }
                    fullWordsBuilder.insert(0, groupWords);
                }
            }

            thousandGroupIndex++;
        }

        return fullWordsBuilder.toString().trim();
    }
}