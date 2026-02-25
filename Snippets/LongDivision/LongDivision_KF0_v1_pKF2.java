package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Utility class; prevent instantiation
    }

    /**
     * Divides two integers without using multiplication, division, or modulo.
     * Uses long division and truncates toward zero.
     */
    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0; // Division by zero is undefined; return 0 as a safe default
        }

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        // If dividend is 0 or its absolute value is smaller than divisor, result is 0
        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendStr = Long.toString(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();

        String remainderStr = "";
        int lastIndex = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            // Build the current part of the dividend: previous remainder + next digit
            String currentPartStr = remainderStr + dividendStr.substring(lastIndex, i + 1);
            long currentPart = Long.parseLong(currentPartStr);

            int currentQuotientDigit = 0;
            if (currentPart >= absDivisor) {
                // Subtract divisor repeatedly to find the current quotient digit
                while (currentPart >= absDivisor) {
                    currentPart -= absDivisor;
                    currentQuotientDigit++;
                }
            }

            quotientBuilder.append(currentQuotientDigit);

            // Update remainder for the next iteration
            remainderStr = (currentPart == 0) ? "" : Long.toString(currentPart);

            lastIndex++;
        }

        boolean negativeResult = (dividend < 0) ^ (divisor < 0);

        try {
            int result = Integer.parseInt(quotientBuilder.toString());
            return negativeResult ? -result : result;
        } catch (NumberFormatException e) {
            // Overflow handling: clamp to integer bounds
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}