package com.thealgorithms.conversions;

import java.util.Map;

public final class IntegerToEnglish {

    private static final Map<Integer, String> BASE_NUMBERS = Map.ofEntries(
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

    private static final Map<Integer, String> THOUSAND_POWERS = Map.ofEntries(
        Map.entry(1, "Thousand"),
        Map.entry(2, "Million"),
        Map.entry(3, "Billion")
    );

    private IntegerToEnglish() {
        // Utility class; prevent instantiation
    }

    public static String integerToEnglishWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();
        int groupIndex = 0;

        while (number > 0) {
            int groupValue = number % 1000;
            number /= 1000;

            if (groupValue > 0) {
                String groupWords = convertThreeDigitNumber(groupValue);
                groupWords = appendThousandPower(groupWords, groupIndex);

                if (!groupWords.isEmpty()) {
                    if (result.length() > 0) {
                        result.insert(0, " ");
                    }
                    result.insert(0, groupWords);
                }
            }

            groupIndex++;
        }

        return result.toString();
    }

    private static String convertThreeDigitNumber(int number) {
        StringBuilder result = new StringBuilder();

        int hundreds = number / 100;
        int remainder = number % 100;

        if (hundreds > 0) {
            result.append(BASE_NUMBERS.get(hundreds)).append(" Hundred");
            if (remainder > 0) {
                result.append(" ");
            }
        }

        appendTensAndOnes(result, remainder);
        return result.toString();
    }

    private static void appendTensAndOnes(StringBuilder result, int number) {
        if (number == 0) {
            return;
        }

        if (number <= 20 || BASE_NUMBERS.containsKey(number)) {
            result.append(BASE_NUMBERS.get(number));
            return;
        }

        int tens = (number / 10) * 10;
        int ones = number % 10;

        result.append(BASE_NUMBERS.getOrDefault(tens, ""));
        if (ones > 0) {
            result.append(" ").append(BASE_NUMBERS.getOrDefault(ones, ""));
        }
    }

    private static String appendThousandPower(String groupWords, int groupIndex) {
        if (groupIndex == 0 || groupWords.isEmpty()) {
            return groupWords;
        }

        String power = THOUSAND_POWERS.get(groupIndex);
        if (power == null || power.isEmpty()) {
            return groupWords;
        }

        return groupWords + " " + power;
    }
}