package com.thealgorithms.maths;

import java.math.BigInteger;

public final class AutomorphicNumber {

    private AutomorphicNumber() {
        // Utility class; prevent instantiation
    }

    public static boolean isAutomorphic(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        int digitCount = String.valueOf(number).length();
        long modulus = (long) Math.pow(10, digitCount);

        return square % modulus == number;
    }

    public static boolean isAutomorphicUsingString(long number) {
        if (number < 0) {
            return false;
        }

        String numberString = String.valueOf(number);
        String squareString = String.valueOf(number * number);

        return squareString.endsWith(numberString);
    }

    public static boolean isAutomorphic(String numberString) {
        BigInteger number = new BigInteger(numberString);
        if (number.signum() < 0) {
            return false;
        }

        String originalString = number.toString();
        String squareString = number.multiply(number).toString();

        return squareString.endsWith(originalString);
    }
}