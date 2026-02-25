package com.thealgorithms.maths;

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isHarshad(long number) {
        if (number <= 0) {
            return false;
        }

        long remainingNumber = number;
        long sumOfDigits = 0;

        while (remainingNumber > 0) {
            sumOfDigits += remainingNumber % 10;
            remainingNumber /= 10;
        }

        return number % sumOfDigits == 0;
    }

    public static boolean isHarshad(String numberAsString) {
        long number = Long.parseLong(numberAsString);
        if (number <= 0) {
            return false;
        }

        int sumOfDigits = 0;
        for (char digitCharacter : numberAsString.toCharArray()) {
            sumOfDigits += digitCharacter - '0';
        }

        return number % sumOfDigits == 0;
    }
}