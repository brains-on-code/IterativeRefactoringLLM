package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long value) {
        if (value <= 0) {
            return false;
        }

        long remainingValue = value;
        long digitSum = 0;

        while (remainingValue > 0) {
            digitSum += remainingValue % 10;
            remainingValue /= 10;
        }

        return value % digitSum == 0;
    }

    public static boolean isHarshad(String numericString) {
        long value = Long.parseLong(numericString);
        if (value <= 0) {
            return false;
        }

        int digitSum = 0;
        for (char digitChar : numericString.toCharArray()) {
            digitSum += digitChar - '0';
        }

        return value % digitSum == 0;
    }
}