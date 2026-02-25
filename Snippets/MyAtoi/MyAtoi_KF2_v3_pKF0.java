package com.thealgorithms.strings;

public final class MyAtoi {

    private MyAtoi() {
        // Utility class; prevent instantiation
    }

    public static int myAtoi(String s) {
        if (s == null) {
            return 0;
        }

        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            return 0;
        }

        int index = 0;
        int length = trimmed.length();
        boolean isNegative = false;

        char firstChar = trimmed.charAt(index);
        if (isSign(firstChar)) {
            isNegative = (firstChar == '-');
            index++;
        }

        int result = 0;
        while (index < length) {
            char currentChar = trimmed.charAt(index);
            if (!Character.isDigit(currentChar)) {
                break;
            }

            int digit = currentChar - '0';

            if (willOverflow(result, digit, isNegative)) {
                return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            result = result * 10 + digit;
            index++;
        }

        return isNegative ? -result : result;
    }

    private static boolean isSign(char c) {
        return c == '-' || c == '+';
    }

    private static boolean willOverflow(int currentValue, int nextDigit, boolean isNegative) {
        int limit = isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int threshold = limit / 10;
        int remainder = limit % 10;

        if (isNegative) {
            return currentValue < threshold || (currentValue == threshold && -nextDigit < remainder);
        } else {
            return currentValue > threshold || (currentValue == threshold && nextDigit > remainder);
        }
    }
}