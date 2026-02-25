package com.thealgorithms.conversions;

/**
 * Utility class for converting numbers between bases 2 and 10.
 */
public final class BaseConverter {

    private BaseConverter() {
    }

    /**
     * Converts a number from one base to another (both between 2 and 10).
     *
     * @param sourceNumber the number to convert, expressed in the source base
     * @param sourceBase   the base the number is currently in (2–10)
     * @param targetBase   the base to convert the number to (2–10)
     * @return the converted number, expressed in the target base
     * @throws IllegalArgumentException if either base is not between 2 and 10
     */
    public static int convertBase(int sourceNumber, int sourceBase, int targetBase) {
        if (!isBaseInRange(sourceBase) || !isBaseInRange(targetBase)) {
            throw new IllegalArgumentException("Bases must be between 2 and 10.");
        }

        int decimalValue = convertToDecimal(sourceNumber, sourceBase);
        return convertFromDecimal(decimalValue, targetBase);
    }

    private static boolean isBaseInRange(int base) {
        return base >= 2 && base <= 10;
    }

    /**
     * Converts a number from a given base (2–10) to decimal.
     *
     * @param valueInBase the number to convert, expressed in the given base
     * @param base        the base of the given number (2–10)
     * @return the decimal representation of the number
     */
    private static int convertToDecimal(int valueInBase, int base) {
        int decimalValue = 0;
        int positionalMultiplier = 1;

        while (valueInBase != 0) {
            int digit = valueInBase % 10;
            decimalValue += digit * positionalMultiplier;
            positionalMultiplier *= base;
            valueInBase /= 10;
        }
        return decimalValue;
    }

    /**
     * Converts a decimal number to a given base (2–10).
     *
     * @param decimalValue the decimal number to convert
     * @param targetBase   the base to convert to (2–10)
     * @return the number expressed in the target base
     */
    private static int convertFromDecimal(int decimalValue, int targetBase) {
        int valueInTargetBase = 0;
        int positionalMultiplier = 1;

        while (decimalValue != 0) {
            int digit = decimalValue % targetBase;
            valueInTargetBase += digit * positionalMultiplier;
            positionalMultiplier *= 10;
            decimalValue /= targetBase;
        }
        return valueInTargetBase;
    }
}