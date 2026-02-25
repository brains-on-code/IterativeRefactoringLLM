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
     * @param start the lower bound of the range (inclusive)
     * @param end   the upper bound of the range (inclusive)
     * @return list of Kaprekar numbers within the specified range
     * @throws IllegalArgumentException if start is greater than end
     */
    public static List<Long> kaprekarNumberInRange(long start, long end) {
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start must not be greater than end.");
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
     * A Kaprekar number is a non-negative integer whose square can be split
     * into two parts that add up to the original number.
     *
     * @param num the number to check
     * @return true if num is a Kaprekar number, false otherwise
     */
    public static boolean isKaprekarNumber(long num) {
        if (num < 0) {
            return false;
        }

        String originalStr = Long.toString(num);
        int originalLength = originalStr.length();

        BigInteger original = BigInteger.valueOf(num);
        BigInteger squared = original.multiply(original);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        // If the square has the same number of digits as the original,
        // the only possible split is the entire number itself.
        if (originalLength == squaredLength) {
            return originalStr.equals(squaredStr);
        }

        // Split the square into:
        // - rightPart: last 'originalLength' digits
        // - leftPartFull: all digits before rightPart
        String rightPartStr = squaredStr.substring(squaredLength - originalLength);
        BigInteger rightPart = new BigInteger(rightPartStr);

        String leftPartFullStr = squaredStr.substring(0, squaredLength - originalLength);
        BigInteger leftPartFull = new BigInteger(leftPartFullStr);

        // Alternative left part: digits before the first '0' (if any)
        BigInteger leftPartUntilZero = BigInteger.ZERO;
        int zeroIndex = squaredStr.indexOf('0');
        if (zeroIndex > 0) {
            String leftUntilZeroStr = squaredStr.substring(0, zeroIndex);
            leftPartUntilZero = new BigInteger(leftUntilZeroStr);
        }

        // Two possible sums:
        // 1) leftPartUntilZero + rightPart
        // 2) leftPartFull + rightPart
        String sumWithZeroSplit = leftPartUntilZero.add(rightPart).toString();
        String sumWithFullLeft = leftPartFull.add(rightPart).toString();

        return originalStr.equals(sumWithZeroSplit) || originalStr.equals(sumWithFullLeft);
    }
}