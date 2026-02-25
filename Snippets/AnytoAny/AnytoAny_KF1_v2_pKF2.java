package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers between bases 2 through 10.
 */
public final class BaseConverter {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private BaseConverter() {
        // Prevent instantiation
    }

    /**
     * Converts a number from one base to another (both between 2 and 10).
     *
     * @param number   the number to convert, expressed in the source base
     * @param fromBase the base the input number is currently in (2–10)
     * @param toBase   the base to convert the number to (2–10)
     * @return the converted number, expressed in the target base
     * @throws IllegalArgumentException if either base is outside the range [2, 10]
     */
    public static int convert(int number, int fromBase, int toBase) {
        validateBase(fromBase);
        validateBase(toBase);

        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    /**
     * Converts a number from a given base (2–10) to its decimal (base-10) representation.
     *
     * @param number the number to convert, expressed in the given base
     * @param base   the base of the input number (2–10)
     * @return the decimal (base-10) representation of the input number
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
     * Converts a decimal (base-10) number to a given base (2–10).
     *
     * @param decimal the decimal (base-10) number to convert
     * @param base    the target base (2–10)
     * @return the number expressed in the target base
     */
    private static int fromDecimal(int decimal, int base) {
        int result = 0;
        int placeValue = 1;

        while (decimal != 0) {
            int digit = decimal % base;
            result += digit * placeValue;
            placeValue *= 10;
            decimal /= base;
        }
        return result;
    }

    /**
     * Validates that a base is within the allowed range.
     *
     * @param base the base to validate
     * @throws IllegalArgumentException if the base is outside the range [2, 10]
     */
    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }
}