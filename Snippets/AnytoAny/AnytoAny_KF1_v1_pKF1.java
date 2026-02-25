package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers between bases 2 and 10.
 */
public final class BaseConverter {

    private BaseConverter() {
    }

    /**
     * Converts a number from one base to another (both between 2 and 10).
     *
     * @param number   the number to convert, expressed in the source base
     * @param fromBase the base the number is currently in (2–10)
     * @param toBase   the base to convert the number to (2–10)
     * @return the converted number, expressed in the target base
     * @throws IllegalArgumentException if either base is not between 2 and 10
     */
    public static int convertBase(int number, int fromBase, int toBase) {
        if (fromBase < 2 || fromBase > 10 || toBase < 2 || toBase > 10) {
            throw new IllegalArgumentException("Bases must be between 2 and 10.");
        }

        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    /**
     * Converts a number from a given base (2–10) to decimal.
     *
     * @param number   the number to convert, expressed in the given base
     * @param fromBase the base of the given number (2–10)
     * @return the decimal representation of the number
     */
    private static int toDecimal(int number, int fromBase) {
        int decimalValue = 0;
        int placeValue = 1;

        while (number != 0) {
            int digit = number % 10;
            decimalValue += digit * placeValue;
            placeValue *= fromBase;
            number /= 10;
        }
        return decimalValue;
    }

    /**
     * Converts a decimal number to a given base (2–10).
     *
     * @param decimalValue the decimal number to convert
     * @param toBase       the base to convert to (2–10)
     * @return the number expressed in the target base
     */
    private static int fromDecimal(int decimalValue, int toBase) {
        int result = 0;
        int placeValue = 1;

        while (decimalValue != 0) {
            int digit = decimalValue % toBase;
            result += digit * placeValue;
            placeValue *= 10;
            decimalValue /= toBase;
        }
        return result;
    }
}