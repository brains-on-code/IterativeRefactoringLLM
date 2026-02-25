package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers between bases 2 and 10.
 */
public final class BaseConverter {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a number from one base to another (both between 2 and 10).
     *
     * @param number   the number to convert, expressed in the source base
     * @param fromBase the base of the input number (between 2 and 10)
     * @param toBase   the base to convert to (between 2 and 10)
     * @return the converted number, expressed in the target base
     * @throws IllegalArgumentException if either base is outside the range [2, 10]
     */
    public static int convertBase(int number, int fromBase, int toBase) {
        validateBase(fromBase);
        validateBase(toBase);

        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    /**
     * Converts a number from a given base (2–10) to decimal.
     *
     * @param number the number to convert, expressed in the given base
     * @param base   the base of the input number (between 2 and 10)
     * @return the decimal representation of the number
     */
    private static int toDecimal(int number, int base) {
        int decimalValue = 0;
        int placeValue = 1;

        while (number != 0) {
            int digit = number % 10;
            decimalValue += digit * placeValue;
            placeValue *= base;
            number /= 10;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal number to a given base (2–10).
     *
     * @param decimalNumber the decimal number to convert
     * @param base          the target base (between 2 and 10)
     * @return the number expressed in the target base
     */
    private static int fromDecimal(int decimalNumber, int base) {
        int result = 0;
        int placeValue = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % base;
            result += digit * placeValue;
            placeValue *= 10;
            decimalNumber /= base;
        }

        return result;
    }
}