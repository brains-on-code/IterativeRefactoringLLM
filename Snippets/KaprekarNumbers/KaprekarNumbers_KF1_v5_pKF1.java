package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
    }

    public static List<Long> findKaprekarNumbersInRange(long startInclusive, long endInclusive) throws Exception {
        if (endInclusive < startInclusive) {
            throw new Exception("Invalid range");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();

        for (long candidate = startInclusive; candidate <= endInclusive; candidate++) {
            if (isKaprekarNumber(candidate)) {
                kaprekarNumbers.add(candidate);
            }
        }

        return kaprekarNumbers;
    }

    public static boolean isKaprekarNumber(long candidate) {
        String candidateStr = Long.toString(candidate);
        int candidateLength = candidateStr.length();

        BigInteger candidateBigInt = BigInteger.valueOf(candidate);
        BigInteger squared = candidateBigInt.multiply(candidateBigInt);
        String squaredStr = squared.toString();

        if (candidateLength == squaredStr.length()) {
            return candidateStr.equals(squaredStr);
        }

        BigInteger prefixBeforeFirstZero = BigInteger.ZERO;
        int firstZeroIndex = squaredStr.indexOf('0');
        if (firstZeroIndex != -1) {
            prefixBeforeFirstZero = new BigInteger(squaredStr.substring(0, firstZeroIndex));
        }

        int splitIndex = squaredStr.length() - candidateLength;

        BigInteger leftPart = new BigInteger(squaredStr.substring(0, splitIndex));
        BigInteger rightPart = new BigInteger(squaredStr.substring(splitIndex));

        String sumWithPrefix = prefixBeforeFirstZero.add(rightPart).toString();
        String sumWithLeftPart = leftPart.add(rightPart).toString();

        return candidateStr.equals(sumWithPrefix) || candidateStr.equals(sumWithLeftPart);
    }
}