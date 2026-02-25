package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {

    private AutomorphicNumber() {
    }

    public static boolean isAutomorphic(long number) {
        if (number < 0) {
            return false;
        }

        long squaredNumber = number * number;
        long remainingNumber = number;
        long digitCount = 0;

        while (remainingNumber > 0) {
            digitCount++;
            remainingNumber /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long squaredNumberSuffix = squaredNumber % modulus;

        return number == squaredNumberSuffix;
    }

    public static boolean isAutomorphicUsingString(long number) {
        if (number < 0) {
            return false;
        }

        long squaredNumber = number * number;
        String squaredNumberString = String.valueOf(squaredNumber);
        String numberString = String.valueOf(number);

        return squaredNumberString.endsWith(numberString);
    }

    public static boolean isAutomorphicBigInteger(String numberString) {
        BigInteger number = new BigInteger(numberString);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger squaredNumber = number.multiply(number);
        String squaredNumberString = squaredNumber.toString();
        String originalNumberString = number.toString();

        return squaredNumberString.endsWith(originalNumberString);
    }
}