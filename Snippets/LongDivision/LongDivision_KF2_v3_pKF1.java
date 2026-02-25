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
            String currentPortionStr = remainderStr + dividendStr.substring(currentIndex, i + 1);
            long currentPortion = Long.parseLong(currentPortionStr);

            if (currentPortion >= absDivisor) {
                int currentQuotientDigit = 0;
                while (currentPortion >= absDivisor) {
                    currentPortion -= absDivisor;
                    currentQuotientDigit++;
                }
                quotientBuilder.append(currentQuotientDigit);
            } else {
                quotientBuilder.append(0);
            }

            remainderStr = (currentPortion == 0) ? "" : String.valueOf(currentPortion);
            currentIndex++;
        }

        boolean isNegativeQuotient = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int quotient = Integer.parseInt(quotientBuilder.toString());
            return isNegativeQuotient ? -quotient : quotient;
        } catch (NumberFormatException e) {
            return isNegativeQuotient ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}