package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
        // Prevent instantiation
    }

    /**
     * Divides two integers without using multiplication, division, or modulo.
     * Uses long division and truncates toward zero.
     */
    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendStr = Long.toString(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();

        String remainderStr = "";
        int lastIndex = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentPartStr = remainderStr + dividendStr.substring(lastIndex, i + 1);
            long currentPart = Long.parseLong(currentPartStr);

            int currentQuotientDigit = 0;
            while (currentPart >= absDivisor) {
                currentPart -= absDivisor;
                currentQuotientDigit++;
            }

            quotientBuilder.append(currentQuotientDigit);
            remainderStr = (currentPart == 0) ? "" : Long.toString(currentPart);
            lastIndex++;
        }

        boolean negativeResult = (dividend < 0) ^ (divisor < 0);

        try {
            int result = Integer.parseInt(quotientBuilder.toString());
            return negativeResult ? -result : result;
        } catch (NumberFormatException e) {
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}