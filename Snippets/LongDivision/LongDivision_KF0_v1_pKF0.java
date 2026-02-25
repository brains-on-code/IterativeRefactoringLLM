package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        // Determine sign of result
        boolean negativeResult = (dividend < 0) ^ (divisor < 0);

        // Work with positive long values to avoid overflow
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
            long current = remainder * 10 + digit;

            int digitQuotient = 0;
            while (current >= absDivisor) {
                current -= absDivisor;
                digitQuotient++;
            }

            quotientBuilder.append(digitQuotient);
            remainder = current;
        }

        // Remove leading zeros from quotient
        String quotientString = quotientBuilder.toString().replaceFirst("^0+(?!$)", "");

        int result;
        try {
            result = Integer.parseInt(quotientString);
        } catch (NumberFormatException e) {
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }

        return negativeResult ? -result : result;
    }
}