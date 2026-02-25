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
        int currentIndex = 0;

        for (int i = 0; i < dividendString.length(); i++) {
            String currentDividendPortionString =
                    remainderString + dividendString.substring(currentIndex, i + 1);
            long currentDividendPortion = Long.parseLong(currentDividendPortionString);

            if (currentDividendPortion >= absoluteDivisor) {
                int currentQuotientDigit = 0;
                while (currentDividendPortion >= absoluteDivisor) {
                    currentDividendPortion -= absoluteDivisor;
                    currentQuotientDigit++;
                }
                quotientBuilder.append(currentQuotientDigit);
            } else if (currentDividendPortion == 0) {
                quotientBuilder.append(0);
            } else if (currentDividendPortion < absoluteDivisor) {
                quotientBuilder.append(0);
            }

            if (currentDividendPortion != 0) {
                remainderString = String.valueOf(currentDividendPortion);
            } else {
                remainderString = "";
            }

            currentIndex++;
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