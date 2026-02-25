package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Utility class; prevent instantiation
    }

    public static List<Long> kaprekarNumberInRange(long start, long end) {
        if (end < start) {
            throw new IllegalArgumentException("Invalid range: end must be >= start");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long number = start; number <= end; number++) {
            if (isKaprekarNumber(number)) {
                kaprekarNumbers.add(number);
            }
        }
        return kaprekarNumbers;
    }

    public static boolean isKaprekarNumber(long number) {
        if (number < 1) {
            return false;
        }

        String numberStr = Long.toString(number);
        int numberLength = numberStr.length();

        BigInteger squared = BigInteger.valueOf(number).pow(2);
        String squaredStr = squared.toString();
        int squaredLength = squaredStr.length();

        if (numberLength == squaredLength) {
            return numberStr.equals(squaredStr);
        }

        String rightPartStr = squaredStr.substring(squaredLength - numberLength);
        String leftPartStr = squaredStr.substring(0, squaredLength - numberLength);

        BigInteger rightPart = new BigInteger(rightPartStr);
        BigInteger leftPart = new BigInteger(leftPartStr);
        BigInteger leftPartBeforeZero = extractLeftPartBeforeFirstZero(squaredStr);

        String sumWithLeftPart = leftPart.add(rightPart).toString();
        String sumWithLeftPartBeforeZero = leftPartBeforeZero.add(rightPart).toString();

        return numberStr.equals(sumWithLeftPart) || numberStr.equals(sumWithLeftPartBeforeZero);
    }

    private static BigInteger extractLeftPartBeforeFirstZero(String squaredStr) {
        int zeroIndex = squaredStr.indexOf('0');
        if (zeroIndex > 0) {
            return new BigInteger(squaredStr.substring(0, zeroIndex));
        }
        return BigInteger.ZERO;
    }
}