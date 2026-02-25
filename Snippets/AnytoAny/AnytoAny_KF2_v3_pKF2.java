package com.thealgorithms.conversions;

public final class AnyToAny {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private AnyToAny() {
        // Utility class; prevent instantiation
    }

    /**
     * Convert a number from one base to another.
     *
     * @param sourceNumber number expressed in {@code sourceBase}
     * @param sourceBase   base of {@code sourceNumber} (inclusive range: 2–10)
     * @param destBase     target base (inclusive range: 2–10)
     * @return {@code sourceNumber} converted to {@code destBase}
     * @throws IllegalArgumentException if {@code sourceBase} or {@code destBase} is not in [2, 10]
     */
    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        validateBase(sourceBase);
        validateBase(destBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }

    /**
     * Validate that a base is within the supported range.
     *
     * @param base base to validate
     * @throws IllegalArgumentException if {@code base} is not in [2, 10]
     */
    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    /**
     * Convert a number from an arbitrary base to decimal (base 10).
     *
     * @param number number expressed in {@code base}
     * @param base   base of {@code number}
     * @return decimal (base 10) representation of {@code number}
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
     * Convert a decimal (base 10) number to another base.
     *
     * @param decimal decimal (base 10) number
     * @param base    target base
     * @return representation of {@code decimal} in {@code base}
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