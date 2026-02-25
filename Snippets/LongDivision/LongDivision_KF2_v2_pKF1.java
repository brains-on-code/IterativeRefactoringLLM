package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        long absoluteDividend = dividend;
        long absoluteDivisor = divisor;

        if (dividend < 0) {
            absoluteDividend = -absoluteDividend;
        }
        if (divisor < 0) {
            absoluteDivisor = -absoluteDivisor;
        }

        if (dividend == 0 || absoluteDividend < absoluteDivisor) {
            return 0;
        }

        StringBuilder quotientBuilder = new StringBuilder();
        String dividendAsString = String.valueOf(absoluteDividend);

        int digitIndex = 0;
        String remainderAsString = "";

        for (int i = 0; i < dividendAsString.length(); i++) {
            String currentChunkAsString = remainderAsString + dividendAsString.substring(digitIndex, i + 1);
            long currentChunk = Long.parseLong(currentChunkAsString);

            if (currentChunk >= absoluteDivisor) {
                int quotientDigit = 0;
                while (currentChunk >= absoluteDivisor) {
                    currentChunk -= absoluteDivisor;
                    quotientDigit++;
                }
                quotientBuilder.append(quotientDigit);
            } else {
                quotientBuilder.append(0);
            }

            remainderAsString = (currentChunk == 0) ? "" : String.valueOf(currentChunk);
            digitIndex++;
        }

        boolean isNegativeResult = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int quotient = Integer.parseInt(quotientBuilder.toString());
            return isNegativeResult ? -quotient : quotient;
        } catch (NumberFormatException e) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}