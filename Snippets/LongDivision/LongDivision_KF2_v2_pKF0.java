package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        boolean isNegative = (dividend < 0) ^ (divisor < 0);

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendStr = Long.toString(absDividend);
        StringBuilder quotient = new StringBuilder();
        long remainder = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            int digit = dividendStr.charAt(i) - '0';
            long current = remainder * 10 + digit;

            int quotientDigit = 0;
            while (current >= absDivisor) {
                current -= absDivisor;
                quotientDigit++;
            }

            quotient.append(quotientDigit);
            remainder = current;
        }

        String quotientStr = stripLeadingZeros(quotient.toString());

        int result;
        try {
            result = Integer.parseInt(quotientStr);
        } catch (NumberFormatException e) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        return isNegative ? -result : result;
    }

    private static String stripLeadingZeros(String value) {
        int index = 0;
        int length = value.length();

        while (index < length - 1 && value.charAt(index) == '0') {
            index++;
        }

        return value.substring(index);
    }
}