package com.thealgorithms.conversions;

public final class BaseConverter {

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    public static int convertBase(int value, int sourceBase, int targetBase) {
        validateBase(sourceBase);
        validateBase(targetBase);

        int decimalValue = convertToDecimal(value, sourceBase);
        return convertFromDecimal(decimalValue, targetBase);
    }

    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

    private static int convertToDecimal(int valueInSourceBase, int sourceBase) {
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

    private static int convertFromDecimal(int decimalValue, int targetBase) {
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