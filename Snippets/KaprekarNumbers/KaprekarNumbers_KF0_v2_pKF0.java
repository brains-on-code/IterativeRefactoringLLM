package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns a list of Kaprekar numbers in the inclusive range [start, end].
     *
     * @throws IllegalArgumentException if start > end
     */
    public static List<Long> kaprekarNumberInRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long number = start; number <= end; number++) {
            if (isKaprekarNumber(number)) {
                kaprekarNumbers.add(number);
            }
        }
        return kaprekarNumbers;
    }

    /**
     * Checks whether a given number is a Kaprekar number.
     *
     * A Kaprekar number is an n-digit number such that its square can be split into
     * two parts where the right part has n digits and the sum of these parts is
     * equal to the original number.
     */
    public static boolean isKaprekarNumber(long number) {
        if (number < 1) {
            return false;
        }

        int numDigits = Long.toString(number).length();

        BigInteger original = BigInteger.valueOf(number);
        BigInteger squared = original.multiply(original);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        if (squaredLength == numDigits) {
            return squared.equals(original);
        }

        int splitIndex = squaredLength - numDigits;
        String leftPartStr = squaredStr.substring(0, splitIndex);
        String rightPartStr = squaredStr.substring(splitIndex);

        BigInteger leftPart = leftPartStr.isEmpty()
            ? BigInteger.ZERO
            : new BigInteger(leftPartStr);
        BigInteger rightPart = new BigInteger(rightPartStr);

        return leftPart.add(rightPart).equals(original);
    }
}