package com.thealgorithms.maths;

public final class IntegerDivision {

    private IntegerDivision() {}

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
        String dividendString = String.valueOf(absoluteDividend);

        String remainderString = "";
        int currentDigitIndex = 0;

        for (int i = 0; i < dividendString.length(); i++) {
            String currentPartialDividendString =
                    remainderString + dividendString.substring(currentDigitIndex, i + 1);
            long currentPartialDividend = Long.parseLong(currentPartialDividendString);

            if (currentPartialDividend >= absoluteDivisor) {
                int currentQuotientDigit = 0;
                while (currentPartialDividend >= absoluteDivisor) {
                    currentPartialDividend -= absoluteDivisor;
                    currentQuotientDigit++;
                }
                quotientBuilder.append(currentQuotientDigit);
            } else if (currentPartialDividend == 0) {
                quotientBuilder.append(0);
            } else if (currentPartialDividend < absoluteDivisor) {
                quotientBuilder.append(0);
            }

            if (currentPartialDividend != 0) {
                remainderString = String.valueOf(currentPartialDividend);
            } else {
                remainderString = "";
            }

            currentDigitIndex++;
        }

        boolean isNegativeQuotient =
                (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int quotient = Integer.parseInt(quotientBuilder.toString());
            return isNegativeQuotient ? -quotient : quotient;
        } catch (NumberFormatException e) {
            return isNegativeQuotient ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}