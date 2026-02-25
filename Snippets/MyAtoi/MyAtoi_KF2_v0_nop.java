package com.thealgorithms.strings;


public final class MyAtoi {
    private MyAtoi() {
    }


    public static int myAtoi(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        s = s.trim();
        int length = s.length();
        if (length == 0) {
            return 0;
        }

        int index = 0;
        boolean negative = false;

        if (s.charAt(index) == '-' || s.charAt(index) == '+') {
            negative = s.charAt(index) == '-';
            index++;
        }

        int number = 0;
        while (index < length) {
            char ch = s.charAt(index);
            if (!Character.isDigit(ch)) {
                break;
            }

            int digit = ch - '0';

            if (number > (Integer.MAX_VALUE - digit) / 10) {
                return negative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            number = number * 10 + digit;
            index++;
        }

        return negative ? -number : number;
    }
}
