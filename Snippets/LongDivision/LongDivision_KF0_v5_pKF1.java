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
        String dividendString = String.valueOf(absDividend);

        int currentIndex = 0;
        String remainderString = "";

        for (int i = 0; i < dividendString.length(); i++) {
            String currentPortionString =
                    remainderString + dividendString.substring(currentIndex, i + 1);
            long currentPortion = Long.parseLong(currentPortionString);

            if (currentPortion >= absDivisor) {
                int quotientDigit = 0;
                while (currentPortion >= absDivisor) {
                    currentPortion -= absDivisor;
                    quotientDigit++;
                }
                quotientBuilder.append(quotientDigit);
            } else {
                quotientBuilder.append(0);
            }

            remainderString =
                    (currentPortion == 0) ? "" : String.valueOf(currentPortion);
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