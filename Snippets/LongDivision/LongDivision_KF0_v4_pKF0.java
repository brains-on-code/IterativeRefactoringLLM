package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        boolean negativeResult = (dividend < 0) ^ (divisor < 0);

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String quotientString = performLongDivision(absDividend, absDivisor);

        int result;
        try {
            result = Integer.parseInt(quotientString);
        } catch (NumberFormatException e) {
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        return negativeResult ? -result : result;
    }

    private static String performLongDivision(long dividend, long divisor) {
        String dividendString = Long.toString(dividend);
        StringBuilder quotientBuilder = new StringBuilder();
        long remainder = 0;

        for (int i = 0; i < dividendString.length(); i++) {
            int digit = dividendString.charAt(i) - '0';
            long currentValue = remainder * 10 + digit;

            int currentQuotientDigit = 0;
            while (currentValue >= divisor) {
                currentValue -= divisor;
                currentQuotientDigit++;
            }

            quotientBuilder.append(currentQuotientDigit);
            remainder = currentValue;
        }

        return stripLeadingZeros(quotientBuilder.toString());
    }

    private static String stripLeadingZeros(String value) {
        return value.replaceFirst("^0+(?!$)", "");
    }
}