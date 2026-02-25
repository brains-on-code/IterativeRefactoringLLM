package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for working with Kaprekar numbers.
 *
 * <p>A Kaprekar number is an n-digit number such that its square can be split into
 * two parts where the right part has n digits and the sum of these parts is equal
 * to the original number.</p>
 */
public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Prevent instantiation
    }

    /**
     * Returns a list of Kaprekar numbers in the inclusive range [start, end].
     *
     * @param start the start of the range (inclusive)
     * @param end   the end of the range (inclusive)
     * @return list of Kaprekar numbers in the given range
     * @throws IllegalArgumentException if start is greater than end
     */
    public static List<Long> kaprekarNumberInRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            if (isKaprekarNumber(i)) {
                kaprekarNumbers.add(i);
            }
        }
        return kaprekarNumbers;
    }

    /**
     * Checks whether a given number is a Kaprekar number.
     *
     * @param num the number to check
     * @return {@code true} if {@code num} is a Kaprekar number, {@code false} otherwise
     */
    public static boolean isKaprekarNumber(long num) {
        if (num < 1) {
            return false;
        }

        String numberStr = Long.toString(num);
        int numDigits = numberStr.length();

        BigInteger original = BigInteger.valueOf(num);
        BigInteger squared = original.multiply(original);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        // If the square has the same number of digits as the original number,
        // the only possible split is 0 + square.
        if (squaredLength == numDigits) {
            return numberStr.equals(squaredStr);
        }

        // Split the square into left and right parts where
        // the right part has the same number of digits as the original number.
        int splitIndex = squaredLength - numDigits;

        BigInteger leftPart = new BigInteger(squaredStr.substring(0, splitIndex));
        BigInteger rightPart = new BigInteger(squaredStr.substring(splitIndex));

        // Alternative left part: from the start of the square up to the first '0'
        BigInteger altLeftPart = BigInteger.ZERO;
        int firstZeroIndex = squaredStr.indexOf('0');
        if (firstZeroIndex > 0) {
            altLeftPart = new BigInteger(squaredStr.substring(0, firstZeroIndex));
        }

        String sum1 = leftPart.add(rightPart).toString();
        String sum2 = altLeftPart.add(rightPart).toString();

        return numberStr.equals(sum1) || numberStr.equals(sum2);
    }
}