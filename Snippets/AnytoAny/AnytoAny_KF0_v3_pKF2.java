package com.thealgorithms.conversions;

/**
 * Utility class for converting integers between bases 2 through 10.
 *
 * <p>Conversion is performed in two steps:
 * <ol>
 *   <li>Convert the source number to decimal (base 10).</li>
 *   <li>Convert that decimal value to the destination base.</li>
 * </ol>
 */
public final class AnytoAny {

    private AnytoAny() {
        // Prevent instantiation
    }

    /**
     * Converts an integer from one base to another.
     *
     * @param sourceNumber the number expressed in {@code sourceBase}, using decimal digits
     * @param sourceBase   the base of {@code sourceNumber}, in the range [2, 10]
     * @param destBase     the target base, in the range [2, 10]
     * @return the representation of {@code sourceNumber} in {@code destBase}, using decimal digits
     * @throws IllegalArgumentException if {@code sourceBase} or {@code destBase} is outside [2, 10]
     */
    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        validateBase(sourceBase);
        validateBase(destBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }

    /**
     * Converts an integer from a given base to its decimal (base-10) value.
     *
     * <p>The input is treated as a sequence of decimal digits that represent
     * a number in the specified base.</p>
     *
     * @param number the number expressed in {@code base}, using decimal digits
     * @param base   the base of {@code number}, in the range [2, 10]
     * @return the decimal (base-10) value of {@code number}
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
     * Converts a decimal (base-10) integer to its representation in another base.
     *
     * <p>The result is returned as an integer whose decimal digits represent
     * the number in the specified base.</p>
     *
     * @param decimal the decimal (base-10) number to convert
     * @param base    the target base, in the range [2, 10]
     * @return the representation of {@code decimal} in {@code base}, using decimal digits
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

    /**
     * Ensures that a base is within the supported range [2, 10].
     *
     * @param base the base to validate
     * @throws IllegalArgumentException if {@code base} is outside [2, 10]
     */
    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10 (inclusive).");
        }
    }
}