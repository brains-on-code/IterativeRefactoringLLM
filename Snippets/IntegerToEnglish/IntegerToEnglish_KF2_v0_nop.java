package com.thealgorithms.conversions;

import java.util.Map;


public final class IntegerToEnglish {

    private static final Map<Integer, String> BASE_NUMBERS_MAP = Map.ofEntries(Map.entry(0, ""), Map.entry(1, "One"), Map.entry(2, "Two"), Map.entry(3, "Three"), Map.entry(4, "Four"), Map.entry(5, "Five"), Map.entry(6, "Six"), Map.entry(7, "Seven"), Map.entry(8, "Eight"), Map.entry(9, "Nine"),
        Map.entry(10, "Ten"), Map.entry(11, "Eleven"), Map.entry(12, "Twelve"), Map.entry(13, "Thirteen"), Map.entry(14, "Fourteen"), Map.entry(15, "Fifteen"), Map.entry(16, "Sixteen"), Map.entry(17, "Seventeen"), Map.entry(18, "Eighteen"), Map.entry(19, "Nineteen"), Map.entry(20, "Twenty"),
        Map.entry(30, "Thirty"), Map.entry(40, "Forty"), Map.entry(50, "Fifty"), Map.entry(60, "Sixty"), Map.entry(70, "Seventy"), Map.entry(80, "Eighty"), Map.entry(90, "Ninety"), Map.entry(100, "Hundred"));

    private static final Map<Integer, String> THOUSAND_POWER_MAP = Map.ofEntries(Map.entry(1, "Thousand"), Map.entry(2, "Million"), Map.entry(3, "Billion"));

    private IntegerToEnglish() {
    }


    private static String convertToWords(int number) {
        int remainder = number % 100;
        StringBuilder result = new StringBuilder();

        if (remainder <= 20) {
            result.append(BASE_NUMBERS_MAP.get(remainder));
        } else if (BASE_NUMBERS_MAP.containsKey(remainder)) {
            result.append(BASE_NUMBERS_MAP.get(remainder));
        } else {
            int tensDigit = remainder / 10;
            int onesDigit = remainder % 10;
            String tens = BASE_NUMBERS_MAP.getOrDefault(tensDigit * 10, "");
            String ones = BASE_NUMBERS_MAP.getOrDefault(onesDigit, "");
            result.append(tens);
            if (ones != null && !ones.isEmpty()) {
                result.append(" ").append(ones);
            }
        }

        int hundredsDigit = number / 100;
        if (hundredsDigit > 0) {
            if (result.length() > 0) {
                result.insert(0, " ");
            }
            result.insert(0, String.format("%s Hundred", BASE_NUMBERS_MAP.get(hundredsDigit)));
        }

        return result.toString().trim();
    }


    public static String integerToEnglishWords(int number) {
        if (number == 0) {
            return "Zero";
        }

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (number > 0) {
            int remainder = number % 1000;
            number /= 1000;

            if (remainder > 0) {
                String subResult = convertToWords(remainder);
                if (!subResult.isEmpty()) {
                    if (index > 0) {
                        subResult += " " + THOUSAND_POWER_MAP.get(index);
                    }
                    if (result.length() > 0) {
                        result.insert(0, " ");
                    }
                    result.insert(0, subResult);
                }
            }

            index++;
        }

        return result.toString().trim();
    }
}
