package com.thealgorithms.conversions;

public final class AnyToAny {

    private AnyToAny() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts a number from one base to another.
     *
     * @param sourceNumber the number to convert, expressed in {@code sourceBase}
     * @param sourceBase   the base of {@code sourceNumber} (between 2 and 10)
     * @param destBase     the target base (between 2 and 10)
     * @return the converted number, expressed in {@code destBase}
     * @throws IllegalArgumentException if either base is outside the range [2, 10]
     */
    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        validateBase(sourceBase);
        validateBase(destBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }

    /**
     * Validates that a base is within the supported range.
     *
     * @param base the base to validate
     * @throws IllegalArgumentException if the base is outside the range [2, 10]
     */
    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

    /**
     * Converts a number from a given base to decimal (base 10).
     *
     * @param number the number to convert, expressed in {@code base}
     * @param base   the base of {@code number}
     * @return the decimal representation of {@code number}
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
     * Converts a decimal (base 10) number to a given base.
     *
     * @param decimal the decimal number to convert
     * @param base    the target base
     * @return the representation of {@code decimal} in {@code base}
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
}