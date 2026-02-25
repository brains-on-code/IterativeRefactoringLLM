package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }
        long digitSum = sumDigits(number);
        return number % digitSum == 0;
    }

    public static boolean isHarshad(String value) {
        long number;
        try {
            number = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return false;
        }

        if (number <= 0) {
            return false;
        }

        long digitSum = sumDigits(value);
        return number % digitSum == 0;
    }

    private static long sumDigits(long number) {
        long sum = 0;
        long remaining = number;

        while (remaining > 0) {
            sum += remaining % 10;
            remaining /= 10;
        }

        return sum;
    }

    private static long sumDigits(String value) {
        long sum = 0;

        for (char ch : value.toCharArray()) {
            sum += Character.getNumericValue(ch);
        }

        return sum;
    }
}