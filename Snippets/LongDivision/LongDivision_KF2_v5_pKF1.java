package com.thealgorithms.maths;

public final class LongDivision {

    private LongDivision() {
    }

    public static int divide(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        long absDividend = dividend;
        long absDivisor = divisor;

        if (dividend < 0) {
            absDividend = -absDividend;
        }
        if (divisor < 0) {
            absDivisor = -absDivisor;
        }

        if (dividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        StringBuilder quotientBuilder = new StringBuilder();
        String dividendStr = String.valueOf(absDividend);

        int currentIndex = 0;
        String remainderStr = "";

        for (int i = 0; i < dividendStr.length(); i++) {
            String partialDividendStr = remainderStr + dividendStr.substring(currentIndex, i + 1);
            long partialDividend = Long.parseLong(partialDividendStr);

            if (partialDividend >= absDivisor) {
                int quotientDigit = 0;
                while (partialDividend >= absDivisor) {
                    partialDividend -= absDivisor;
                    quotientDigit++;
                }
                quotientBuilder.append(quotientDigit);
            } else {
                quotientBuilder.append(0);
            }

            remainderStr = (partialDividend == 0) ? "" : String.valueOf(partialDividend);
            currentIndex++;
        }

        boolean isNegativeResult =
                (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int quotient = Integer.parseInt(quotientBuilder.toString());
            return isNegativeResult ? -quotient : quotient;
        } catch (NumberFormatException exception) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}