package com.thealgorithms.strings;

public final class StringToIntegerParser {

    private StringToIntegerParser() {
    }

    public static int parseStringToInt(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String trimmedInput = input.trim();
        int inputLength = trimmedInput.length();
        if (inputLength == 0) {
            return 0;
        }

        int currentIndex = 0;
        boolean isNegative = false;

        char firstChar = trimmedInput.charAt(currentIndex);
        if (firstChar == '-' || firstChar == '+') {
            isNegative = (firstChar == '-');
            currentIndex++;
        }

        int result = 0;
        while (currentIndex < inputLength) {
            char currentChar = trimmedInput.charAt(currentIndex);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digitValue = currentChar - '0';

            if (result > (Integer.MAX_VALUE - digitValue) / 10) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digitValue;
            currentIndex++;
        }

        return isNegative ? -result : result;
    }
}