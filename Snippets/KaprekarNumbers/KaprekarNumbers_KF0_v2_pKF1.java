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

        BigInteger originalNumber = BigInteger.valueOf(number);
        BigInteger squaredNumber = originalNumber.multiply(originalNumber);
        String squaredNumberAsString = squaredNumber.toString();
        int squaredNumberLength = squaredNumberAsString.length();

        if (numberOfDigits == squaredNumberLength) {
            return numberAsString.equals(squaredNumberAsString);
        }

        int splitIndexByLength = squaredNumberLength - numberOfDigits;

        BigInteger leftPartBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squaredNumberAsString.indexOf('0');
        if (firstZeroIndex > 0) {
            leftPartBeforeFirstZero = new BigInteger(squaredNumberAsString.substring(0, firstZeroIndex));
        }

        BigInteger leftPartByLength = new BigInteger(squaredNumberAsString.substring(0, splitIndexByLength));
        BigInteger rightPart = new BigInteger(squaredNumberAsString.substring(splitIndexByLength));

        String sumUsingZeroSplit = leftPartBeforeFirstZero.add(rightPart).toString();
        String sumUsingLengthSplit = leftPartByLength.add(rightPart).toString();

        return numberAsString.equals(sumUsingZeroSplit) || numberAsString.equals(sumUsingLengthSplit);
    }
}