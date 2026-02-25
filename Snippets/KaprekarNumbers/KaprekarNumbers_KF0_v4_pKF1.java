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
        String numberAsString = Long.toString(number);
        int numberOfDigits = numberAsString.length();

        BigInteger numberBigInt = BigInteger.valueOf(number);
        BigInteger squaredNumber = numberBigInt.multiply(numberBigInt);
        String squaredAsString = squaredNumber.toString();
        int squaredNumberLength = squaredAsString.length();

        if (numberOfDigits == squaredNumberLength) {
            return numberAsString.equals(squaredAsString);
        }

        int splitIndexFromRight = squaredNumberLength - numberOfDigits;

        BigInteger leftPartBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squaredAsString.indexOf('0');
        if (firstZeroIndex > 0) {
            leftPartBeforeFirstZero = new BigInteger(squaredAsString.substring(0, firstZeroIndex));
        }

        BigInteger leftPartByLength = new BigInteger(squaredAsString.substring(0, splitIndexFromRight));
        BigInteger rightPart = new BigInteger(squaredAsString.substring(splitIndexFromRight));

        String sumUsingZeroSplit = leftPartBeforeFirstZero.add(rightPart).toString();
        String sumUsingLengthSplit = leftPartByLength.add(rightPart).toString();

        return numberAsString.equals(sumUsingZeroSplit) || numberAsString.equals(sumUsingLengthSplit);
    }
}