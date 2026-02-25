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

        StringBuilder quotient = new StringBuilder();
        String dividendDigits = String.valueOf(absoluteDividend);

        int digitIndex = 0;
        String remainderDigits = "";

        for (int i = 0; i < dividendDigits.length(); i++) {
            String currentDividendPortionDigits =
                    remainderDigits + dividendDigits.substring(digitIndex, i + 1);
            long currentDividendPortion = Long.parseLong(currentDividendPortionDigits);

            if (currentDividendPortion >= absoluteDivisor) {
                int quotientDigit = 0;
                while (currentDividendPortion >= absoluteDivisor) {
                    currentDividendPortion -= absoluteDivisor;
                    quotientDigit++;
                }
                quotient.append(quotientDigit);
            } else {
                quotient.append(0);
            }

            remainderDigits =
                    (currentDividendPortion == 0) ? "" : String.valueOf(currentDividendPortion);
            digitIndex++;
        }

        boolean isNegativeResult =
                (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int result = Integer.parseInt(quotient.toString());
            return isNegativeResult ? -result : result;
        } catch (NumberFormatException e) {
            return isNegativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}