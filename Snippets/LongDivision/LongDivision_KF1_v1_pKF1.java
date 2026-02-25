package com.thealgorithms.maths;

public final class IntegerDivision {

    private IntegerDivision() {}

    public static int divide(int dividend, int divisor) {
        long absDividend = dividend;
        long absDivisor = divisor;

        if (divisor == 0) {
            return 0;
        }
        if (dividend < 0) {
            absDividend *= -1;
        }
        if (divisor < 0) {
            absDivisor *= -1;
        }

        if (dividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        StringBuilder quotientBuilder = new StringBuilder();

        String dividendStr = String.valueOf(absDividend);
        int startIndex = 0;

        String remainderStr = "";

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentValueStr = remainderStr + dividendStr.substring(startIndex, i + 1);
            long currentValue = Long.parseLong(currentValueStr);

            if (currentValue >= absDivisor) {
                int digitQuotient = 0;
                while (currentValue >= absDivisor) {
                    currentValue -= absDivisor;
                    digitQuotient++;
                }
                quotientBuilder.append(digitQuotient);
            } else if (currentValue == 0) {
                quotientBuilder.append(0);
            } else if (currentValue < absDivisor) {
                quotientBuilder.append(0);
            }

            if (currentValue != 0) {
                remainderStr = String.valueOf(currentValue);
            } else {
                remainderStr = "";
            }

            startIndex++;
        }

        boolean negativeResult = (dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0);

        try {
            int result = Integer.parseInt(quotientBuilder.toString());
            return negativeResult ? -result : result;
        } catch (NumberFormatException e) {
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}