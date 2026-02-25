package com.thealgorithms.maths;

// Wikipedia for Harshad Number : https://en.wikipedia.org/wiki/Harshad_number

public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is a Harshad number.
     *
     * @param number the number to be checked
     * @return {@code true} if {@code number} is a Harshad number, otherwise {@code false}
     */
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

    /**
     * Checks if a number (given as a String) is a Harshad number.
     *
     * @param numberAsString the number in String form to be checked
     * @return {@code true} if the parsed number is a Harshad number, otherwise {@code false}
     */
    public static boolean isHarshad(String numberAsString) {
        final long number = Long.parseLong(numberAsString);
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