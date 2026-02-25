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

        int decimalValue = convertToDecimal(sourceNumber, sourceBase);
        return convertFromDecimal(decimalValue, destinationBase);
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }

    private static int convertToDecimal(int numberInSourceBase, int sourceBase) {
        int decimalValue = 0;
        int positionalMultiplier = 1;

        while (numberInSourceBase != 0) {
            int digit = numberInSourceBase % DECIMAL_RADIX;
            decimalValue += digit * positionalMultiplier;
            positionalMultiplier *= sourceBase;
            numberInSourceBase /= DECIMAL_RADIX;
        }

        return decimalValue;
    }

    private static int convertFromDecimal(int decimalNumber, int destinationBase) {
        int numberInDestinationBase = 0;
        int positionalMultiplier = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % destinationBase;
            numberInDestinationBase += digit * positionalMultiplier;
            positionalMultiplier *= DECIMAL_RADIX;
            decimalNumber /= destinationBase;
        }

        return numberInDestinationBase;
    }
}