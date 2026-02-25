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
            int digit = dividendString.charAt(i) - '0';
            long currentValue = remainder * 10 + digit;

            int currentQuotientDigit = 0;
            while (currentValue >= absDivisor) {
                currentValue -= absDivisor;
                currentQuotientDigit++;
            }

            quotientBuilder.append(currentQuotientDigit);
            remainder = currentValue;
        }

        String quotientString = removeLeadingZeros(quotientBuilder.toString());

        int result;
        try {
            result = Integer.parseInt(quotientString);
        } catch (NumberFormatException e) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        return isNegativeResult ? -result : result;
    }

    private static String removeLeadingZeros(String value) {
        return value.replaceFirst("^0+(?!$)", "");
    }
}