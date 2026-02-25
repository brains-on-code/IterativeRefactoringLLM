package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
    }

    public static List<Long> findKaprekarNumbersInRange(long start, long end) throws Exception {
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

    public static boolean isKaprekarNumber(long number) {
        String numberString = Long.toString(number);
        int numberLength = numberString.length();

        BigInteger original = BigInteger.valueOf(number);
        BigInteger squared = original.multiply(original);
        String squaredString = squared.toString();
        int squaredLength = squaredString.length();

        if (numberLength == squaredLength) {
            return numberString.equals(squaredString);
        }

        BigInteger leftPartBeforeZero = BigInteger.ZERO;
        if (squaredString.contains("0")) {
            int zeroIndex = squaredString.indexOf('0');
            if (zeroIndex > 0) {
                leftPartBeforeZero = new BigInteger(squaredString.substring(0, zeroIndex));
            }
        }

        int splitIndex = squaredLength - numberLength;
        BigInteger leftPart = new BigInteger(squaredString.substring(0, splitIndex));
        BigInteger rightPart = new BigInteger(squaredString.substring(splitIndex));

        String sumWithBeforeZero = leftPartBeforeZero.add(rightPart).toString();
        String sumWithLeftPart = leftPart.add(rightPart).toString();

        return numberString.equals(sumWithBeforeZero) || numberString.equals(sumWithLeftPart);
    }
}