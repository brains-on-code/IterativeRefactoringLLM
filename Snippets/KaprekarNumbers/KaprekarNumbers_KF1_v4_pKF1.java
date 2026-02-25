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

        for (long number = startInclusive; number <= endInclusive; number++) {
            if (isKaprekarNumber(number)) {
                kaprekarNumbers.add(number);
            }
        }

        return kaprekarNumbers;
    }

    public static boolean isKaprekarNumber(long number) {
        String numberAsString = Long.toString(number);
        BigInteger numberAsBigInt = BigInteger.valueOf(number);
        BigInteger squaredNumber = numberAsBigInt.multiply(numberAsBigInt);
        String squaredNumberAsString = squaredNumber.toString();

        if (numberAsString.length() == squaredNumberAsString.length()) {
            return numberAsString.equals(squaredNumberAsString);
        } else {
            BigInteger prefixBeforeFirstZero = BigInteger.ZERO;
            if (squaredNumberAsString.contains("0")) {
                prefixBeforeFirstZero =
                    new BigInteger(squaredNumberAsString.substring(0, squaredNumberAsString.indexOf("0")));
            }

            int rightPartLength = numberAsString.length();
            int splitIndex = squaredNumberAsString.length() - rightPartLength;

            BigInteger leftPart = new BigInteger(squaredNumberAsString.substring(0, splitIndex));
            BigInteger rightPart = new BigInteger(squaredNumberAsString.substring(splitIndex));

            String sumUsingPrefix = prefixBeforeFirstZero.add(rightPart).toString();
            String sumUsingLeftPart = leftPart.add(rightPart).toString();

            return numberAsString.equals(sumUsingPrefix) || numberAsString.equals(sumUsingLeftPart);
        }
    }
}