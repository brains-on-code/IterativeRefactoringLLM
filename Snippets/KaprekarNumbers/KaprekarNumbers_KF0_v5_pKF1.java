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
        String numberString = Long.toString(number);
        int numberDigitCount = numberString.length();

        BigInteger numberBigInt = BigInteger.valueOf(number);
        BigInteger squaredNumber = numberBigInt.multiply(numberBigInt);
        String squaredNumberString = squaredNumber.toString();
        int squaredDigitCount = squaredNumberString.length();

        if (numberDigitCount == squaredDigitCount) {
            return numberString.equals(squaredNumberString);
        }

        int splitIndexFromRight = squaredDigitCount - numberDigitCount;

        BigInteger leftPartBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squaredNumberString.indexOf('0');
        if (firstZeroIndex > 0) {
            leftPartBeforeFirstZero = new BigInteger(squaredNumberString.substring(0, firstZeroIndex));
        }

        BigInteger leftPartByLength = new BigInteger(squaredNumberString.substring(0, splitIndexFromRight));
        BigInteger rightPart = new BigInteger(squaredNumberString.substring(splitIndexFromRight));

        String sumUsingZeroSplit = leftPartBeforeFirstZero.add(rightPart).toString();
        String sumUsingLengthSplit = leftPartByLength.add(rightPart).toString();

        return numberString.equals(sumUsingZeroSplit) || numberString.equals(sumUsingLengthSplit);
    }
}