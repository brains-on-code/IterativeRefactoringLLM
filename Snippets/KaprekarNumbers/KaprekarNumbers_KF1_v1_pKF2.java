package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for Kaprekar number operations.
 *
 * A Kaprekar number is a non-negative integer, the representation of whose
 * square can be split into two parts that add up to the original number.
 */
public final class Class1 {

    private Class1() {
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
    public static List<Long> method1(long start, long end) {
        long range = end - start;
        if (range < 0) {
            throw new IllegalArgumentException("Invalid range: start must be <= end");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();

        for (long n = start; n <= end; n++) {
            if (method2(n)) {
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
    public static boolean method2(long n) {
        String nStr = Long.toString(n);
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger square = nBig.multiply(nBig);
        String squareStr = square.toString();

        // If the square has the same number of digits as n, they must be equal
        if (nStr.length() == squareStr.length()) {
            return nStr.equals(squareStr);
        } else {
            BigInteger prefixBeforeFirstZero = BigInteger.ZERO;

            if (squareStr.contains("0")) {
                prefixBeforeFirstZero = new BigInteger(squareStr.substring(0, squareStr.indexOf("0")));
            }

            int splitIndex = squareStr.length() - nStr.length();
            BigInteger leftPart = new BigInteger(squareStr.substring(0, splitIndex));
            BigInteger rightPart = new BigInteger(squareStr.substring(splitIndex));

            String sumWithPrefix = prefixBeforeFirstZero.add(rightPart).toString();
            String sumWithLeft = leftPart.add(rightPart).toString();

            return nStr.equals(sumWithPrefix) || nStr.equals(sumWithLeft);
        }
    }
}