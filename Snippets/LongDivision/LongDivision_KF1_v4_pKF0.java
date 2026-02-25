package com.thealgorithms.maths;

public final class Class1 {

    private Class1() {
        // Utility class
    }

    public static int method1(int dividend, int divisor) {
        if (divisor == 0 || dividend == 0) {
            return 0;
        }

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend < absDivisor) {
            return 0;
        }

        String dividendStr = Long.toString(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();
        long remainder = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            int currentDigit = dividendStr.charAt(i) - '0';
            long currentValue = remainder * 10 + currentDigit;

            int digitQuotient = 0;
            while (currentValue >= absDivisor) {
                currentValue -= absDivisor;
                digitQuotient++;
            }

            quotientBuilder.append(digitQuotient);
            remainder = currentValue;
        }

        int sign = (dividend < 0) ^ (divisor < 0) ? -1 : 1;
        String quotientStr = quotientBuilder.toString();

        try {
            return sign * Integer.parseInt(quotientStr);
        } catch (NumberFormatException e) {
            return sign < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}