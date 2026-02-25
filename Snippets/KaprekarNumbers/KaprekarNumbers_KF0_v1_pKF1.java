package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
    }

    /**
     * Returns a list of Kaprekar numbers in the inclusive range [start, end].
     */
    public static List<Long> getKaprekarNumbersInRange(long start, long end) throws Exception {
        if (end < start) {
            throw new Exception("Invalid range");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long candidate = start; candidate <= end; candidate++) {
            if (isKaprekarNumber(candidate)) {
                kaprekarNumbers.add(candidate);
            }
        }
        return kaprekarNumbers;
    }

    /**
     * Checks whether a given number is a Kaprekar number.
     */
    public static boolean isKaprekarNumber(long number) {
        String numberStr = Long.toString(number);
        int numDigits = numberStr.length();

        BigInteger original = BigInteger.valueOf(number);
        BigInteger squared = original.multiply(original);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        if (numDigits == squaredLength) {
            return numberStr.equals(squaredStr);
        }

        int splitIndex = squaredLength - numDigits;

        BigInteger leftPartFromZero = BigInteger.ZERO;
        int firstZeroIndex = squaredStr.indexOf('0');
        if (firstZeroIndex > 0) {
            leftPartFromZero = new BigInteger(squaredStr.substring(0, firstZeroIndex));
        }

        BigInteger leftPartByLength = new BigInteger(squaredStr.substring(0, splitIndex));
        BigInteger rightPart = new BigInteger(squaredStr.substring(splitIndex));

        String sumWithZeroSplit = leftPartFromZero.add(rightPart).toString();
        String sumWithLengthSplit = leftPartByLength.add(rightPart).toString();

        return numberStr.equals(sumWithZeroSplit) || numberStr.equals(sumWithLengthSplit);
    }
}