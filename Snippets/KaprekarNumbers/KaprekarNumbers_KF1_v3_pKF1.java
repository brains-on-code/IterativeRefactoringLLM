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
        String candidateString = Long.toString(candidate);
        BigInteger candidateBigInt = BigInteger.valueOf(candidate);
        BigInteger squaredCandidate = candidateBigInt.multiply(candidateBigInt);
        String squaredString = squaredCandidate.toString();

        if (candidateString.length() == squaredString.length()) {
            return candidateString.equals(squaredString);
        } else {
            BigInteger prefixBeforeFirstZero = BigInteger.ZERO;
            if (squaredString.contains("0")) {
                prefixBeforeFirstZero =
                    new BigInteger(squaredString.substring(0, squaredString.indexOf("0")));
            }

            int rightPartLength = candidateString.length();
            int splitIndex = squaredString.length() - rightPartLength;

            BigInteger leftPart = new BigInteger(squaredString.substring(0, splitIndex));
            BigInteger rightPart = new BigInteger(squaredString.substring(splitIndex));

            String sumUsingPrefix = prefixBeforeFirstZero.add(rightPart).toString();
            String sumUsingLeftPart = leftPart.add(rightPart).toString();

            return candidateString.equals(sumUsingPrefix) || candidateString.equals(sumUsingLeftPart);
        }
    }
}