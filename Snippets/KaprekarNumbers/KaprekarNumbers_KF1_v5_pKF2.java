package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Kaprekar number operations.
 *
 * A Kaprekar number is a non-negative integer whose square can be split into
 * two parts that add up to the original number.
 */
public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns a list of all Kaprekar numbers in the inclusive range [start, end].
     *
     * @param start the lower bound of the range (inclusive)
     * @param end   the upper bound of the range (inclusive)
     * @return list of Kaprekar numbers within the given range
     * @throws IllegalArgumentException if start is greater than end
     */
    public static List<Long> findKaprekarNumbers(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long n = start; n <= end; n++) {
            if (isKaprekar(n)) {
                kaprekarNumbers.add(n);
            }
        }
        return kaprekarNumbers;
    }

    /**
     * Checks whether a given number is a Kaprekar number.
     *
     * @param n the number to check
     * @return true if n is a Kaprekar number, false otherwise
     */
    public static boolean isKaprekar(long n) {
        if (n < 0) {
            return false;
        }

        String nStr = Long.toString(n);
        int nDigits = nStr.length();

        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger square = nBig.multiply(nBig);
        String squareStr = square.toString();
        int squareDigits = squareStr.length();

        // If the square has the same number of digits as n, they must be equal
        if (squareDigits == nDigits) {
            return nStr.equals(squareStr);
        }

        // Split the square into left and right parts, where rightPart has nDigits digits
        int splitIndex = squareDigits - nDigits;

        BigInteger leftPart = new BigInteger(squareStr.substring(0, splitIndex));
        BigInteger rightPart = new BigInteger(squareStr.substring(splitIndex));

        // Variant 1: use prefix before the first zero in the square representation
        BigInteger prefixBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squareStr.indexOf('0');
        if (firstZeroIndex > 0) {
            prefixBeforeFirstZero = new BigInteger(squareStr.substring(0, firstZeroIndex));
        }

        String sumWithPrefix = prefixBeforeFirstZero.add(rightPart).toString();
        String sumWithLeft = leftPart.add(rightPart).toString();

        return nStr.equals(sumWithPrefix) || nStr.equals(sumWithLeft);
    }
}