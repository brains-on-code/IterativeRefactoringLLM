package com.thealgorithms.maths;

public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Performs integer division of {@code dividend} by {@code divisor} without using
     * the division operator.
     *
     * <p>Behavior:
     * <ul>
     *   <li>If {@code divisor == 0}, returns 0.</li>
     *   <li>If {@code |dividend| < |divisor|}, returns 0.</li>
     *   <li>If the mathematical result overflows the {@code int} range, returns
     *       {@link Integer#MIN_VALUE} or {@link Integer#MAX_VALUE} depending on
     *       the sign of the result.</li>
     * </ul>
     *
     * @param dividend the number to be divided
     * @param divisor  the number by which to divide
     * @return the quotient of the division, or a clamped value on overflow
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

        int nextDigitIndex = 0;
        String remainderStr = "";

        for (int i = 0; i < dividendStr.length(); i++) {
            String currentChunk = remainderStr + dividendStr.substring(nextDigitIndex, i + 1);
            long currentValue = Long.parseLong(currentChunk);

            int digitQuotient = 0;
            while (currentValue >= absDivisor) {
                currentValue -= absDivisor;
                digitQuotient++;
            }

            quotientBuilder.append(digitQuotient);
            remainderStr = currentValue != 0 ? String.valueOf(currentValue) : "";
            nextDigitIndex++;
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