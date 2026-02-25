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
        if (start > end) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long number = start; number <= end; number++) {
            if (isKaprekar(number)) {
                kaprekarNumbers.add(number);
            }
        }
        return kaprekarNumbers;
    }

    /**
     * Checks whether a number satisfies a Kaprekar-like property:
     * based on its square and partitions of the square's decimal representation.
     *
     * @param n number to check
     * @return true if the number satisfies the property, false otherwise
     */
    public static boolean isKaprekar(long n) {
        String numberStr = Long.toString(n);
        BigInteger number = BigInteger.valueOf(n);
        BigInteger square = number.multiply(number);
        String squareStr = square.toString();

        int numberLength = numberStr.length();
        int squareLength = squareStr.length();

        if (numberLength == squareLength) {
            return numberStr.equals(squareStr);
        }

        String leftPartStr = squareStr.substring(0, squareLength - numberLength);
        String rightPartStr = squareStr.substring(squareLength - numberLength);

        BigInteger leftPart = new BigInteger(leftPartStr);
        BigInteger rightPart = new BigInteger(rightPartStr);

        BigInteger prefixBeforeZero = extractPrefixBeforeFirstZero(squareStr);

        String sumWithPrefix = prefixBeforeZero.add(rightPart).toString();
        String sumWithLeft = leftPart.add(rightPart).toString();

        return numberStr.equals(sumWithPrefix) || numberStr.equals(sumWithLeft);
    }

    private static BigInteger extractPrefixBeforeFirstZero(String value) {
        int zeroIndex = value.indexOf('0');
        if (zeroIndex > 0) {
            return new BigInteger(value.substring(0, zeroIndex));
        }
        return BigInteger.ZERO;
    }
}