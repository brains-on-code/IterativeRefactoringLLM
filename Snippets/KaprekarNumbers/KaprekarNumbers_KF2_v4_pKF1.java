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
        String candidateNumberString = Long.toString(candidateNumber);
        int candidateNumberLength = candidateNumberString.length();

        BigInteger candidateBigInt = BigInteger.valueOf(candidateNumber);
        BigInteger squaredCandidate = candidateBigInt.multiply(candidateBigInt);
        String squaredCandidateString = squaredCandidate.toString();
        int squaredCandidateLength = squaredCandidateString.length();

        if (candidateNumberLength == squaredCandidateLength) {
            return candidateNumberString.equals(squaredCandidateString);
        }

        BigInteger leftSegmentBeforeFirstZero = BigInteger.ZERO;
        if (squaredCandidateString.contains("0")) {
            int firstZeroIndex = squaredCandidateString.indexOf('0');
            if (firstZeroIndex > 0) {
                leftSegmentBeforeFirstZero = new BigInteger(squaredCandidateString.substring(0, firstZeroIndex));
            }
        }

        int splitIndex = squaredCandidateLength - candidateNumberLength;
        BigInteger leftSegment = new BigInteger(squaredCandidateString.substring(0, splitIndex));
        BigInteger rightSegment = new BigInteger(squaredCandidateString.substring(splitIndex));

        String sumUsingZeroSegment = leftSegmentBeforeFirstZero.add(rightSegment).toString();
        String sumUsingLeftSegment = leftSegment.add(rightSegment).toString();

        return candidateNumberString.equals(sumUsingZeroSegment)
            || candidateNumberString.equals(sumUsingLeftSegment);
    }
}