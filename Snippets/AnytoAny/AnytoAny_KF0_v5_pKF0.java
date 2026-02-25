package com.thealgorithms.conversions;

/**
 * A utility class for converting numbers from any base to any other base.
 *
 * Valid bases range from 2 to 10.
 */
public final class AnytoAny {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private AnytoAny() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a number from a source base to a destination base.
     *
     * @param sourceNumber the number in the source base (as an integer)
     * @param sourceBase   the base of the source number (between 2 and 10)
     * @param destBase     the base to which the number should be converted (between 2 and 10)
     * @return the converted number in the destination base (as an integer)
     * @throws IllegalArgumentException if the bases are not between 2 and 10
     */
    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        validateBase(sourceBase);
        validateBase(destBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }

    /**
     * Validates that a base is within the allowed range.
     *
     * @param base the base to validate
     * @throws IllegalArgumentException if the base is not between MIN_BASE and MAX_BASE
     */
    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    /**
     * Converts a number from a given base to its decimal representation (base 10).
     *
     * @param number the number in the original base
     * @param base   the base of the given number
     * @return the decimal representation of the number
     */
    private static int toDecimal(int number, int base) {
        int decimalValue = 0;
        int basePower = 1;

        while (number != 0) {
            int digit = number % 10;
            decimalValue += digit * basePower;
            basePower *= base;
            number /= 10;
        }

        return decimalValue;
    }

    /**
     * Converts a decimal (base 10) number to a specified base.
     *
     * @param decimal the decimal number to convert
     * @param base    the destination base for conversion
     * @return the number in the specified base
     */
    private static int fromDecimal(int decimal, int base) {
        int result = 0;
        int decimalPlace = 1;

        while (decimal != 0) {
            int digit = decimal % base;
            result += digit * decimalPlace;
            decimalPlace *= 10;
            decimal /= base;
        }

        return result;
    }
}