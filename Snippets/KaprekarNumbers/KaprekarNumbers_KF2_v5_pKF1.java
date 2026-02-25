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

        BigInteger numberBigInt = BigInteger.valueOf(number);
        BigInteger squaredNumber = numberBigInt.multiply(numberBigInt);
        String squaredStr = squaredNumber.toString();
        int squaredLength = squaredStr.length();

        if (numberLength == squaredLength) {
            return numberStr.equals(squaredStr);
        }

        BigInteger leftSegmentBeforeFirstZero = BigInteger.ZERO;
        if (squaredStr.contains("0")) {
            int firstZeroIndex = squaredStr.indexOf('0');
            if (firstZeroIndex > 0) {
                leftSegmentBeforeFirstZero = new BigInteger(squaredStr.substring(0, firstZeroIndex));
            }
        }

        int splitIndex = squaredLength - numberLength;
        BigInteger leftSegment = new BigInteger(squaredStr.substring(0, splitIndex));
        BigInteger rightSegment = new BigInteger(squaredStr.substring(splitIndex));

        String sumWithZeroSegment = leftSegmentBeforeFirstZero.add(rightSegment).toString();
        String sumWithLeftSegment = leftSegment.add(rightSegment).toString();

        return numberStr.equals(sumWithZeroSegment) || numberStr.equals(sumWithLeftSegment);
    }
}