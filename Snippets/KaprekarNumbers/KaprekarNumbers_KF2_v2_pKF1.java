package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
    }

    public static List<Long> findKaprekarNumbersInRange(long rangeStart, long rangeEnd) throws Exception {
        if (rangeEnd < rangeStart) {
            throw new Exception("Invalid range");
        }

        List<Long> kaprekarNumbersInRange = new ArrayList<>();

        for (long candidateNumber = rangeStart; candidateNumber <= rangeEnd; candidateNumber++) {
            if (isKaprekarNumber(candidateNumber)) {
                kaprekarNumbersInRange.add(candidateNumber);
            }
        }

        return kaprekarNumbersInRange;
    }

    public static boolean isKaprekarNumber(long candidateNumber) {
        String candidateString = Long.toString(candidateNumber);
        int candidateLength = candidateString.length();

        BigInteger candidateBigInt = BigInteger.valueOf(candidateNumber);
        BigInteger squaredCandidate = candidateBigInt.multiply(candidateBigInt);
        String squaredString = squaredCandidate.toString();
        int squaredLength = squaredString.length();

        if (candidateLength == squaredLength) {
            return candidateString.equals(squaredString);
        }

        BigInteger leftSegmentBeforeFirstZero = BigInteger.ZERO;
        if (squaredString.contains("0")) {
            int firstZeroIndex = squaredString.indexOf('0');
            if (firstZeroIndex > 0) {
                leftSegmentBeforeFirstZero = new BigInteger(squaredString.substring(0, firstZeroIndex));
            }
        }

        int splitPosition = squaredLength - candidateLength;
        BigInteger leftSegment = new BigInteger(squaredString.substring(0, splitPosition));
        BigInteger rightSegment = new BigInteger(squaredString.substring(splitPosition));

        String sumWithZeroSegment = leftSegmentBeforeFirstZero.add(rightSegment).toString();
        String sumWithLeftSegment = leftSegment.add(rightSegment).toString();

        return candidateString.equals(sumWithZeroSegment) || candidateString.equals(sumWithLeftSegment);
    }
}