package com.thealgorithms.conversions;

public final class AnyToAny {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

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
            int digit = numberInSourceBase % 10;
            decimalValue += digit * positionalMultiplier;
            positionalMultiplier *= sourceBase;
            numberInSourceBase /= 10;
        }

        return decimalValue;
    }

    private static int convertFromDecimal(int decimalNumber, int destinationBase) {
        int numberInDestinationBase = 0;
        int positionalMultiplier = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % destinationBase;
            numberInDestinationBase += digit * positionalMultiplier;
            positionalMultiplier *= 10;
            decimalNumber /= destinationBase;
        }

        return numberInDestinationBase;
    }
}