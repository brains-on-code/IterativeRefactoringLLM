package com.thealgorithms.maths;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class Class1 {

    private Class1() {
    }

    public static List<Long> method1(long start, long end) throws Exception {
        long range = end - start;
        if (range < 0) {
            throw new Exception("Invalid range");
        }

        List<Long> kaprekarNumbers = new ArrayList<>();

        for (long number = start; number <= end; number++) {
            if (method2(number)) {
                kaprekarNumbers.add(number);
            }
        }

        return kaprekarNumbers;
    }

    public static boolean method2(long number) {
        String numberStr = Long.toString(number);
        BigInteger bigNumber = BigInteger.valueOf(number);
        BigInteger squaredNumber = bigNumber.multiply(bigNumber);
        String squaredStr = squaredNumber.toString();

        if (numberStr.length() == squaredStr.length()) {
            return numberStr.equals(squaredStr);
        } else {
            BigInteger prefixBeforeZero = BigInteger.ZERO;
            if (squaredStr.contains("0")) {
                prefixBeforeZero = new BigInteger(squaredStr.substring(0, squaredStr.indexOf("0")));
            }

            int splitIndex = squaredStr.length() - numberStr.length();
            BigInteger leftPart = new BigInteger(squaredStr.substring(0, splitIndex));
            BigInteger rightPart = new BigInteger(squaredStr.substring(splitIndex));

            String sumWithPrefix = prefixBeforeZero.add(rightPart).toString();
            String sumWithLeftPart = leftPart.add(rightPart).toString();

            return numberStr.equals(sumWithPrefix) || numberStr.equals(sumWithLeftPart);
        }
    }
}