package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Returns all numbers in the inclusive range [start, end] that satisfy {@link #isKaprekar(long)}.
     *
     * @param start lower bound of the range (inclusive)
     * @param end   upper bound of the range (inclusive)
     * @return list of numbers in the range that satisfy the Kaprekar-like property
     * @throws IllegalArgumentException if start > end
     */
    public static List<Long> method1(long start, long end) {
        if (end < start) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> result = new ArrayList<>();
        for (long n = start; n <= end; n++) {
            if (isKaprekar(n)) {
                result.add(n);
            }
        }
        return result;
    }

    /**
     * Checks whether a number satisfies a Kaprekar-like property:
     * based on its square and partitions of the square's decimal representation.
     *
     * @param n number to check
     * @return true if the number satisfies the property, false otherwise
     */
    public static boolean isKaprekar(long n) {
        String nStr = Long.toString(n);
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger square = nBig.multiply(nBig);
        String squareStr = square.toString();

        int nLen = nStr.length();
        int squareLen = squareStr.length();

        if (nLen == squareLen) {
            return nStr.equals(squareStr);
        }

        // Left and right parts for the main split
        String leftPartStr = squareStr.substring(0, squareLen - nLen);
        String rightPartStr = squareStr.substring(squareLen - nLen);

        BigInteger rightPart = new BigInteger(rightPartStr);
        BigInteger leftPart = new BigInteger(leftPartStr);

        // Optional prefix before the first '0' in the square
        BigInteger prefixBeforeZero = BigInteger.ZERO;
        int zeroIndex = squareStr.indexOf('0');
        if (zeroIndex > 0) {
            prefixBeforeZero = new BigInteger(squareStr.substring(0, zeroIndex));
        }

        String sumWithPrefix = prefixBeforeZero.add(rightPart).toString();
        String sumWithLeft = leftPart.add(rightPart).toString();

        return nStr.equals(sumWithPrefix) || nStr.equals(sumWithLeft);
    }
}