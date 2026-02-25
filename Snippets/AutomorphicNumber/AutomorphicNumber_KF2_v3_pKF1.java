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
        long squareSuffix = square % modulus;

        return number == squareSuffix;
    }

    public static boolean isAutomorphicUsingString(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        String squareAsString = String.valueOf(square);
        String numberAsString = String.valueOf(number);

        return squareAsString.endsWith(numberAsString);
    }

    public static boolean isAutomorphicBigInteger(String numberAsString) {
        BigInteger number = new BigInteger(numberAsString);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger square = number.multiply(number);
        String squareAsString = square.toString();
        String originalNumberAsString = number.toString();

        return squareAsString.endsWith(originalNumberAsString);
    }
}