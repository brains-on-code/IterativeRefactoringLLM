package com.thealgorithms.conversions;

public final class BaseConverter {

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    public static int convertBase(int number, int sourceBase, int targetBase) {
        validateBase(sourceBase);
        validateBase(targetBase);

        int decimalNumber = toDecimal(number, sourceBase);
        return fromDecimal(decimalNumber, targetBase);
    }

    private static void validateBase(int base) {
        if (base < 2 || base > 10) {
            throw new IllegalArgumentException("Base must be between 2 and 10.");
        }
    }

    private static int toDecimal(int numberInSourceBase, int sourceBase) {
        int decimalNumber = 0;
        int positionalMultiplier = 1;

        while (numberInSourceBase != 0) {
            int digit = numberInSourceBase % 10;
            decimalNumber += digit * positionalMultiplier;
            positionalMultiplier *= sourceBase;
            numberInSourceBase /= 10;
        }
        return decimalNumber;
    }

    private static int fromDecimal(int decimalNumber, int targetBase) {
        int numberInTargetBase = 0;
        int positionalMultiplier = 1;

        while (decimalNumber != 0) {
            int digit = decimalNumber % targetBase;
            numberInTargetBase += digit * positionalMultiplier;
            positionalMultiplier *= 10;
            decimalNumber /= targetBase;
        }
        return numberInTargetBase;
    }
}