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
        String dividendAsString = String.valueOf(absDividend);

        String remainderAsString = "";
        int digitStartIndex = 0;

        for (int i = 0; i < dividendAsString.length(); i++) {
            String partialDividendString =
                    remainderAsString + dividendAsString.substring(digitStartIndex, i + 1);
            long partialDividend = Long.parseLong(partialDividendString);

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
                remainderAsString = String.valueOf(partialDividend);
            } else {
                remainderAsString = "";
            }

            digitStartIndex++;
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