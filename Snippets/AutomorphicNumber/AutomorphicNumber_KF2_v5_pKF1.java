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
        long temp = number;
        long digitCount = 0;

        while (temp > 0) {
            digitCount++;
            temp /= 10;
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
        String squareString = String.valueOf(square);
        String numberString = String.valueOf(number);

        return squareString.endsWith(numberString);
    }

    public static boolean isAutomorphicBigInteger(String numberString) {
        BigInteger number = new BigInteger(numberString);

        if (number.signum() < 0) {
            return false;
        }

        BigInteger square = number.multiply(number);
        String squareString = square.toString();
        String originalNumberString = number.toString();

        return squareString.endsWith(originalNumberString);
    }
}