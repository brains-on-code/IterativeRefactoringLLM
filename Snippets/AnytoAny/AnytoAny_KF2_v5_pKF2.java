package com.thealgorithms.conversions;

public final class AnyToAny {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private AnyToAny() {
        // Utility class; prevent instantiation
    }

    /**
     * Converts an integer representation of a number from one base to another.
     * <p>
     * Both {@code sourceBase} and {@code destBase} must be between 2 and 10 (inclusive).
     * The digits of {@code sourceNumber} are interpreted as decimal digits (0–9),
     * but their positional values are computed according to {@code sourceBase}.
     *
     * @param sourceNumber the number to convert, written using decimal digits but
     *                     interpreted in {@code sourceBase}
     * @param sourceBase   the base in which {@code sourceNumber} is expressed (2–10)
     * @param destBase     the base to convert to (2–10)
     * @return the converted number, written using decimal digits but
     *         interpreted in {@code destBase}
     * @throws IllegalArgumentException if {@code sourceBase} or {@code destBase}
     *                                  is outside the range [2, 10]
     */
    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        validateBase(sourceBase);
        validateBase(destBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }

    /**
     * Validates that a base is within the supported range [2, 10].
     *
     * @param base the base to validate
     * @throws IllegalArgumentException if {@code base} is outside the range [2, 10]
     */
    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    /**
     * Converts an integer representation of a number from a given base to decimal (base 10).
     * <p>
     * The digits of {@code number} are read as decimal digits (0–9), but their
     * positional values are computed according to {@code base}.
     *
     * @param number the number to convert, written using decimal digits but
     *               interpreted in {@code base}
     * @param base   the base in which {@code number} is expressed
     * @return the decimal (base 10) value of {@code number}
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
     * Converts a decimal (base 10) integer to its representation in another base.
     * <p>
     * The result is returned as an integer whose digits are decimal digits (0–9),
     * but whose positional values correspond to {@code base}.
     *
     * @param decimal the decimal (base 10) number to convert
     * @param base    the target base
     * @return the representation of {@code decimal} in {@code base}, written using
     *         decimal digits
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