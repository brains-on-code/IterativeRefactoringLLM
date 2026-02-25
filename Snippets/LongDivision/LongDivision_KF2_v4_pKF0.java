package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        boolean isNegativeResult = (dividend < 0) ^ (divisor < 0);

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendString = Long.toString(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();
        long remainder = 0;

        for (int i = 0; i < dividendString.length(); i++) {
            int currentDigit = dividendString.charAt(i) - '0';
            long currentValue = remainder * 10 + currentDigit;

            int quotientDigit = 0;
            while (currentValue >= absDivisor) {
                currentValue -= absDivisor;
                quotientDigit++;
            }

            quotientBuilder.append(quotientDigit);
            remainder = currentValue;
        }

        String quotientString = stripLeadingZeros(quotientBuilder.toString());

        try {
            int result = Integer.parseInt(quotientString);
            return isNegativeResult ? -result : result;
        } catch (NumberFormatException e) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }

    private static String stripLeadingZeros(String value) {
        int index = 0;
        int lastIndex = value.length() - 1;

        while (index < lastIndex && value.charAt(index) == '0') {
            index++;
        }

        return value.substring(index);
    }
}