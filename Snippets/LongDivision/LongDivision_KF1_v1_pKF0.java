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

        String remainderStr = "";
        int startIndex = 0;

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentStr = remainderStr + dividendStr.substring(startIndex, i + 1);
            long currentValue = Long.parseLong(currentStr);

            int digitQuotient = 0;
            if (currentValue >= absDivisor) {
                while (currentValue >= absDivisor) {
                    currentValue -= absDivisor;
                    digitQuotient++;
                }
            }

            quotientBuilder.append(digitQuotient);

            remainderStr = (currentValue == 0) ? "" : Long.toString(currentValue);
            startIndex++;
        }

        int sign = ((dividend < 0) ^ (divisor < 0)) ? -1 : 1;

        try {
            return sign * Integer.parseInt(quotientBuilder.toString());
        } catch (NumberFormatException e) {
            return sign < 0 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}