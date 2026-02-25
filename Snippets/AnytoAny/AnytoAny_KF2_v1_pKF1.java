package com.thealgorithms.conversions;

public final class BaseConverter {

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    public static int convertBase(int sourceNumber, int sourceBase, int targetBase) {
        validateBase(sourceBase);
        validateBase(targetBase);

        int decimalValue = convertToDecimal(sourceNumber, sourceBase);
        return convertFromDecimal(decimalValue, targetBase);
    }

    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

    private static int convertToDecimal(int numberInSourceBase, int sourceBase) {
        int decimalValue = 0;
        int placeValue = 1;

        while (numberInSourceBase != 0) {
            int digit = numberInSourceBase % 10;
            decimalValue += digit * placeValue;
            placeValue *= sourceBase;
            numberInSourceBase /= 10;
        }
        return decimalValue;
    }

    private static int convertFromDecimal(int decimalNumber, int targetBase) {
        int numberInTargetBase = 0;
        int placeValue = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % targetBase;
            numberInTargetBase += digit * placeValue;
            placeValue *= 10;
            decimalNumber /= targetBase;
        }
        return numberInTargetBase;
    }
}