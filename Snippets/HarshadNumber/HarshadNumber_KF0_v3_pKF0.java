package com.thealgorithms.maths;

// Wikipedia for Harshad Number : https://en.wikipedia.org/wiki/Harshad_number
public final class HarshadNumber {

    private HarshadNumber() {
        // Utility class; prevent instantiation
    }

    /**
     * Checks if a number is a Harshad number.
     *
     * @param n the number to be checked
     * @return {@code true} if {@code n} is a Harshad number, otherwise {@code false}
     */
    public static boolean isHarshad(long n) {
        return n > 0 && isHarshadInternal(n);
    }

    /**
     * Checks if a number (given as a String) is a Harshad number.
     *
     * @param s the number in String form to be checked
     * @return {@code true} if the parsed number is a Harshad number, otherwise {@code false}
     * @throws NumberFormatException if the string does not contain a parsable long
     */
    public static boolean isHarshad(String s) {
        long n = Long.parseLong(s);
        return n > 0 && isHarshadInternal(n);
    }

    private static boolean isHarshadInternal(long n) {
        long sumOfDigits = sumDigits(n);
        return sumOfDigits != 0 && n % sumOfDigits == 0;
    }

    private static long sumDigits(long n) {
        long sum = 0;
        long value = Math.abs(n);

        while (value > 0) {
            sum += value % 10;
            value /= 10;
        }

        return sum;
    }
}