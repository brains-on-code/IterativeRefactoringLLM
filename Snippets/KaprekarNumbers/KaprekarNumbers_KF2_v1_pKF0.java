package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class KaprekarNumbers {

    private KaprekarNumbers() {
        // Utility class; prevent instantiation
    }

    public static List<Long> kaprekarNumberInRange(long start, long end) throws Exception {
        if (end < start) {
            throw new Exception("Invalid range");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();
        for (long i = start; i <= end; i++) {
            if (isKaprekarNumber(i)) {
                kaprekarNumbers.add(i);
            }
        }
        return kaprekarNumbers;
    }

    public static boolean isKaprekarNumber(long num) {
        String originalStr = Long.toString(num);
        int originalLength = originalStr.length();

        BigInteger original = BigInteger.valueOf(num);
        String squaredStr = original.multiply(original).toString();
        int squaredLength = squaredStr.length();

        if (originalLength == squaredLength) {
            return originalStr.equals(squaredStr);
        }

        String rightPartStr = squaredStr.substring(squaredLength - originalLength);
        BigInteger rightPart = new BigInteger(rightPartStr);

        String leftPartStr = squaredStr.substring(0, squaredLength - originalLength);
        BigInteger leftPart = new BigInteger(leftPartStr);

        BigInteger leftPartBeforeZero = BigInteger.ZERO;
        int zeroIndex = squaredStr.indexOf('0');
        if (zeroIndex > 0) {
            leftPartBeforeZero = new BigInteger(squaredStr.substring(0, zeroIndex));
        }

        String sumWithLeftPart = leftPart.add(rightPart).toString();
        String sumWithLeftPartBeforeZero = leftPartBeforeZero.add(rightPart).toString();

        return originalStr.equals(sumWithLeftPart) || originalStr.equals(sumWithLeftPartBeforeZero);
    }
}