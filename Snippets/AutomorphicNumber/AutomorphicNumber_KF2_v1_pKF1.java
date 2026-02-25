package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {

    private AutomorphicNumber() {
    }

    public static boolean isAutomorphic(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        long tempNumber = number;
        long digitCount = 0;

        while (tempNumber > 0) {
            digitCount++;
            tempNumber /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long trailingDigits = square % modulus;

        return number == trailingDigits;
    }

    public static boolean isAutomorphicUsingString(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        return String.valueOf(square).endsWith(String.valueOf(number));
    }

    public static boolean isAutomorphicBigInteger(String numberString) {
        BigInteger number = new BigInteger(numberString);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger square = number.multiply(number);
        return square.toString().endsWith(number.toString());
    }
}