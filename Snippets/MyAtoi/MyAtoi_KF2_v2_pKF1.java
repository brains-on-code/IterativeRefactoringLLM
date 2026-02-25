package com.thealgorithms.strings;

public final class MyAtoi {

    private MyAtoi() {
    }

    public static int myAtoi(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        String trimmed = input.trim();
        int length = trimmed.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean negative = false;

        char signChar = trimmed.charAt(index);
        if (signChar == '-' || signChar == '+') {
            negative = (signChar == '-');
            index++;
        }

        int value = 0;
        while (index < length) {
            char ch = trimmed.charAt(index);
            if (!Character.isDigit(ch)) {
                break;
            }

            int digit = ch - '0';

            if (value > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            value = value * 10 + digit;
            index++;
        }

        return negative ? -value : value;
    }
}