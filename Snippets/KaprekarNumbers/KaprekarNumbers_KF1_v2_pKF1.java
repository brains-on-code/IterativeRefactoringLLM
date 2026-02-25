package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
    }

    public static List<Long> findKaprekarNumbersInRange(long start, long end) throws Exception {
        long range = end - start;
        if (range < 0) {
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
        BigInteger bigNumber = BigInteger.valueOf(number);
        BigInteger squaredNumber = bigNumber.multiply(bigNumber);
        String squaredString = squaredNumber.toString();

        if (numberString.length() == squaredString.length()) {
            return numberString.equals(squaredString);
        } else {
            BigInteger prefixBeforeFirstZero = BigInteger.ZERO;
            if (squaredString.contains("0")) {
                prefixBeforeFirstZero =
                    new BigInteger(squaredString.substring(0, squaredString.indexOf("0")));
            }

            int rightPartLength = numberString.length();
            int splitIndex = squaredString.length() - rightPartLength;

            BigInteger leftPart = new BigInteger(squaredString.substring(0, splitIndex));
            BigInteger rightPart = new BigInteger(squaredString.substring(splitIndex));

            String sumWithPrefix = prefixBeforeFirstZero.add(rightPart).toString();
            String sumWithLeftPart = leftPart.add(rightPart).toString();

            return numberString.equals(sumWithPrefix) || numberString.equals(sumWithLeftPart);
        }
    }
}