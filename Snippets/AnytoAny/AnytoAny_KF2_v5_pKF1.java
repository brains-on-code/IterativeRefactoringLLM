package com.thealgorithms.conversions;

public final class BaseConverter {

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    public static int convertBase(int value, int fromBase, int toBase) {
        validateBase(fromBase);
        validateBase(toBase);

        int decimalValue = toDecimal(value, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

    private static int toDecimal(int valueInSourceBase, int sourceBase) {
        int decimalValue = 0;
        int placeValue = 1;

        while (valueInSourceBase != 0) {
            int digit = valueInSourceBase % 10;
            decimalValue += digit * placeValue;
            placeValue *= sourceBase;
            valueInSourceBase /= 10;
        }
        return decimalValue;
    }

    private static int fromDecimal(int decimalValue, int targetBase) {
        int valueInTargetBase = 0;
        int placeValue = 1;

        while (decimalValue != 0) {
            int digit = decimalValue % targetBase;
            valueInTargetBase += digit * placeValue;
            placeValue *= 10;
            decimalValue /= targetBase;
        }
        return valueInTargetBase;
    }
}