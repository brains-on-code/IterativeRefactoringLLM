package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long number) {
        if (!isPositive(number)) {
            return false;
        }
        long digitSum = sumDigits(number);
        return isDivisible(number, digitSum);
    }

    public static boolean isHarshad(String value) {
        Long number = parsePositiveLong(value);
        if (number == null) {
            return false;
        }
        long digitSum = sumDigits(value);
        return isDivisible(number, digitSum);
    }

    private static boolean isPositive(long number) {
        return number > 0;
    }

    private static boolean isDivisible(long number, long divisor) {
        return divisor != 0 && number % divisor == 0;
    }

    private static Long parsePositiveLong(String value) {
        try {
            long number = Long.parseLong(value);
            return isPositive(number) ? number : null;
        } catch (NumberFormatException e) {
            return null;
        }
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