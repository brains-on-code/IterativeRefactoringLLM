package com.thealgorithms.conversions;

public final class AnyToAny {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;
    private static final int DECIMAL_RADIX = 10;

    private AnyToAny() {
        // Utility class; prevent instantiation
    }

    public static int convertBase(int sourceNumber, int sourceBase, int destinationBase) {
        validateBase(sourceBase);
        validateBase(destinationBase);

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destinationBase);
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    private static int toDecimal(int number, int base) {
        int decimalValue = 0;
        int placeValue = 1;

        while (number != 0) {
            int digit = number % DECIMAL_RADIX;
            decimalValue += digit * placeValue;
            placeValue *= base;
            number /= DECIMAL_RADIX;
        }

        return decimalValue;
    }

    private static int fromDecimal(int decimalNumber, int base) {
        int result = 0;
        int placeValue = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % base;
            result += digit * placeValue;
            placeValue *= DECIMAL_RADIX;
            decimalNumber /= base;
        }

        return result;
    }
}