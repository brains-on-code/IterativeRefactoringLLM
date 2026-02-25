package com.thealgorithms.maths;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs integer division of dividend by divisor without using
     * the division operator. Returns clamped Integer.MIN_VALUE / Integer.MAX_VALUE
     * on overflow.
     */
    public static int method1(int dividend, int divisor) {
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

        int startIndex = 0;
        String remainderStr = "";

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentChunk = remainderStr + dividendStr.substring(startIndex, i + 1);
            long currentValue = Long.parseLong(currentChunk);

            if (currentValue >= absDivisor) {
                int digitQuotient = 0;
                while (currentValue >= absDivisor) {
                    currentValue -= absDivisor;
                    digitQuotient++;
                }
                quotientBuilder.append(digitQuotient);
            } else if (currentValue == 0) {
                quotientBuilder.append(0);
            } else { // currentValue < absDivisor
                quotientBuilder.append(0);
            }

            remainderStr = (currentValue != 0) ? String.valueOf(currentValue) : "";
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