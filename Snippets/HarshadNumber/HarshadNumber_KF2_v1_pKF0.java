package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long sumOfDigits = sumDigits(number);
        return number % sumOfDigits == 0;
    }

    public static boolean isHarshad(String value) {
        long number = Long.parseLong(value);
        if (number <= 0) {
            return false;
        }

        int sumOfDigits = sumDigits(value);
        return number % sumOfDigits == 0;
    }

    private static long sumDigits(long number) {
        long sum = 0;
        long temp = number;

        while (temp > 0) {
            sum += temp % 10;
            temp /= 10;
        }

        return sum;
    }

    private static int sumDigits(String value) {
        int sum = 0;

        for (char ch : value.toCharArray()) {
            sum += ch - '0';
        }

        return sum;
    }
}