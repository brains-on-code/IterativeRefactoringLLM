package com.thealgorithms.maths;

public final class IntegerDivision {

    private IntegerDivision() {}

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

        String remainderStr = "";
        int nextDigitIndex = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            String partialDividendStr =
                    remainderStr + dividendStr.substring(nextDigitIndex, i + 1);
            long partialDividend = Long.parseLong(partialDividendStr);

            if (partialDividend >= absDivisor) {
                int quotientDigit = 0;
                while (partialDividend >= absDivisor) {
                    partialDividend -= absDivisor;
                    quotientDigit++;
                }
                quotientBuilder.append(quotientDigit);
            } else if (partialDividend == 0) {
                quotientBuilder.append(0);
            } else if (partialDividend < absDivisor) {
                quotientBuilder.append(0);
            }

            if (partialDividend != 0) {
                remainderStr = String.valueOf(partialDividend);
            } else {
                remainderStr = "";
            }

            nextDigitIndex++;
        }

        boolean isNegativeResult =
                (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int quotient = Integer.parseInt(quotientBuilder.toString());
            return isNegativeResult ? -quotient : quotient;
        } catch (NumberFormatException e) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}