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

        BigInteger numberBigInt = BigInteger.valueOf(number);
        BigInteger squared = numberBigInt.multiply(numberBigInt);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        if (numDigits == squaredLength) {
            return numberStr.equals(squaredStr);
        }

        int splitIndexFromRight = squaredLength - numDigits;

        BigInteger leftPartBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squaredStr.indexOf('0');
        if (firstZeroIndex > 0) {
            leftPartBeforeFirstZero = new BigInteger(squaredStr.substring(0, firstZeroIndex));
        }

        BigInteger leftPartByLength = new BigInteger(squaredStr.substring(0, splitIndexFromRight));
        BigInteger rightPart = new BigInteger(squaredStr.substring(splitIndexFromRight));

        String sumUsingZeroSplit = leftPartBeforeFirstZero.add(rightPart).toString();
        String sumUsingLengthSplit = leftPartByLength.add(rightPart).toString();

        return numberStr.equals(sumUsingZeroSplit) || numberStr.equals(sumUsingLengthSplit);
    }
}