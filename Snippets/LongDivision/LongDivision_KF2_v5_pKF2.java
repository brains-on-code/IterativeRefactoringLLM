package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class; prevent instantiation
    }

    public static int divide(int dividend, int divisor) {
        // Handle division by zero
        if (divisor == 0) {
            return 0;
        }

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        // If dividend is 0 or smaller than divisor, result is 0
        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendString = Long.toString(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();

        String remainder = "";
        int lastIndex = 0;

        // Perform long division digit by digit
        for (int i = 0; i < dividendString.length(); i++) {
            String currentPartString = remainder + dividendString.substring(lastIndex, i + 1);
            long currentPart = Long.parseLong(currentPartString);

            int currentQuotientDigit = 0;
            while (currentPart >= absDivisor) {
                currentPart -= absDivisor;
                currentQuotientDigit++;
            }

            quotientBuilder.append(currentQuotientDigit);
            remainder = (currentPart == 0) ? "" : Long.toString(currentPart);

            lastIndex++;
        }

        String quotientString = quotientBuilder.toString();

        boolean negativeResult =
                (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int result = Integer.parseInt(quotientString);
            return negativeResult ? -result : result;
        } catch (NumberFormatException e) {
            // Overflow: clamp to int bounds with correct sign
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}