package com.thealgorithms.conversions;

public final class AnyToAny {

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
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

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