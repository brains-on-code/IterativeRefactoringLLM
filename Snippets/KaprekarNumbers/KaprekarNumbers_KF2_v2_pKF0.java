package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Utility class; prevent instantiation
    }

    public static List<Long> kaprekarNumberInRange(long start, long end) throws IllegalArgumentException {
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
        String numberStr = Long.toString(number);
        int numberLength = numberStr.length();

        BigInteger bigNumber = BigInteger.valueOf(number);
        String squaredStr = bigNumber.multiply(bigNumber).toString();
        int squaredLength = squaredStr.length();

        if (numberLength == squaredLength) {
            return numberStr.equals(squaredStr);
        }

        String rightPartStr = squaredStr.substring(squaredLength - numberLength);
        BigInteger rightPart = new BigInteger(rightPartStr);

        String leftPartStr = squaredStr.substring(0, squaredLength - numberLength);
        BigInteger leftPart = new BigInteger(leftPartStr);

        BigInteger leftPartBeforeZero = BigInteger.ZERO;
        int zeroIndex = squaredStr.indexOf('0');
        if (zeroIndex > 0) {
            leftPartBeforeZero = new BigInteger(squaredStr.substring(0, zeroIndex));
        }

        String sumWithLeftPart = leftPart.add(rightPart).toString();
        String sumWithLeftPartBeforeZero = leftPartBeforeZero.add(rightPart).toString();

        return numberStr.equals(sumWithLeftPart) || numberStr.equals(sumWithLeftPartBeforeZero);
    }
}