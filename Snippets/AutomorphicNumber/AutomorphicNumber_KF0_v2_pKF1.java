package com.thealgorithms.maths;

import java.math.BigInteger;

/**
 * <a href="https://en.wikipedia.org/wiki/Automorphic_number">Automorphic Number</a>
 * A number is said to be an Automorphic, if it is present in the last digit(s)
 * of its square. Example- Let the number be 25, its square is 625. Since,
 * 25(The input number) is present in the last two digits of its square(625), it
 * is an Automorphic Number.
 */
public final class AutomorphicNumber {

    private AutomorphicNumber() {
    }

    /**
     * Checks if a number is an Automorphic number.
     *
     * @param number the number to be checked
     * @return {@code true} if {@code number} is an Automorphic number, otherwise
     *         {@code false}
     */
    public static boolean isAutomorphic(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        long remaining = number;
        long digitCount = 0;

        while (remaining > 0) {
            digitCount++;
            remaining /= 10;
        }

        long modulus = (long) Math.pow(10, digitCount);
        long squareSuffix = square % modulus;

        return number == squareSuffix;
    }

    /**
     * Checks if a number is an Automorphic number using String operations.
     *
     * @param number the number to be checked
     * @return {@code true} if {@code number} is an Automorphic number, otherwise
     *         {@code false}
     */
    public static boolean isAutomorphicUsingString(long number) {
        if (number < 0) {
            return false;
        }

        long square = number * number;
        String squareString = String.valueOf(square);
        String numberString = String.valueOf(number);

        return squareString.endsWith(numberString);
    }

    /**
     * Checks if a number is an Automorphic number using BigInteger.
     *
     * @param numberString the number (as a String) to be checked
     * @return {@code true} if the number is an Automorphic number, otherwise
     *         {@code false}
     */
    public static boolean isAutomorphicUsingBigInteger(String numberString) {
        BigInteger number = new BigInteger(numberString);

        if (number.signum() == -1) {
            return false;
        }

        BigInteger square = number.multiply(number);
        String squareString = square.toString();
        String originalNumberString = number.toString();

        return squareString.endsWith(originalNumberString);
    }
}