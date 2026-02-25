package com.thealgorithms.maths;

public final class Class1 {

    private Class1() {
        // Prevent instantiation of utility class
    }

    /**
     * Performs integer division of {@code dividend} by {@code divisor} without using
     * the division operator. If the result overflows the {@code int} range,
     * it returns {@link Integer#MIN_VALUE} or {@link Integer#MAX_VALUE} depending
     * on the sign of the result.
     *
     * @param dividend the number to be divided
     * @param divisor  the number by which to divide
     * @return the quotient of the division, or a clamped value on overflow;
     *         returns 0 if {@code divisor} is 0 or if {@code |dividend| < |divisor|}
     */
    public static int method1(int dividend, int divisor) {
        if (divisor == 0) {
            return 0;
        }

        long absDividend = Math.abs((long) dividend);
        long absDivisor = Math.abs((long) divisor);

        if (absDividend == 0 || absDividend < absDivisor) {
            return 0;
        }

        String dividendStr = String.valueOf(absDividend);
        StringBuilder quotientBuilder = new StringBuilder();

        int startIndex = 0;
        String remainderStr = "";

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentChunk = remainderStr + dividendStr.substring(startIndex, i + 1);
            long currentValue = Long.parseLong(currentChunk);

            int digitQuotient = 0;
            while (currentValue >= absDivisor) {
                currentValue -= absDivisor;
                digitQuotient++;
            }

            quotientBuilder.append(digitQuotient);
            remainderStr = (currentValue != 0) ? String.valueOf(currentValue) : "";
            startIndex++;
        }

        boolean negativeResult = (dividend < 0) ^ (divisor < 0);

        try {
            int result = Integer.parseInt(quotientBuilder.toString());
            return negativeResult ? -result : result;
        } catch (NumberFormatException e) {
            return negativeResult ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
    }
}