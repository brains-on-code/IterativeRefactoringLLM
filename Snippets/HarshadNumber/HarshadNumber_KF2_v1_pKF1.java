package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long remaining = number;
        long digitSum = 0;

        while (remaining > 0) {
            digitSum += remaining % 10;
            remaining /= 10;
        }

        return number % digitSum == 0;
    }

    public static boolean isHarshad(String numberString) {
        long number = Long.parseLong(numberString);
        if (number <= 0) {
            return false;
        }

        int digitSum = 0;
        for (char digitChar : numberString.toCharArray()) {
            digitSum += digitChar - '0';
        }

        return number % digitSum == 0;
    }
}