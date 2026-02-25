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
     * @param sourceNumber   the number to convert, expressed in the source base
     * @param sourceBase     the base the number is currently in (2–10)
     * @param targetBase     the base to convert the number to (2–10)
     * @return the converted number, expressed in the target base
     * @throws IllegalArgumentException if either base is not between 2 and 10
     */
    public static int convertBase(int sourceNumber, int sourceBase, int targetBase) {
        if (sourceBase < 2 || sourceBase > 10 || targetBase < 2 || targetBase > 10) {
            throw new IllegalArgumentException("Bases must be between 2 and 10.");
        }

        int decimalValue = convertToDecimal(sourceNumber, sourceBase);
        return convertFromDecimal(decimalValue, targetBase);
    }

    /**
     * Converts a number from a given base (2–10) to decimal.
     *
     * @param numberInBase   the number to convert, expressed in the given base
     * @param base           the base of the given number (2–10)
     * @return the decimal representation of the number
     */
    private static int convertToDecimal(int numberInBase, int base) {
        int decimalValue = 0;
        int positionalMultiplier = 1;

        while (numberInBase != 0) {
            int leastSignificantDigit = numberInBase % 10;
            decimalValue += leastSignificantDigit * positionalMultiplier;
            positionalMultiplier *= base;
            numberInBase /= 10;
        }
        return decimalValue;
    }

    /**
     * Converts a decimal number to a given base (2–10).
     *
     * @param decimalValue   the decimal number to convert
     * @param targetBase     the base to convert to (2–10)
     * @return the number expressed in the target base
     */
    private static int convertFromDecimal(int decimalValue, int targetBase) {
        int numberInTargetBase = 0;
        int positionalMultiplier = 1;

        while (decimalValue != 0) {
            int leastSignificantDigit = decimalValue % targetBase;
            numberInTargetBase += leastSignificantDigit * positionalMultiplier;
            positionalMultiplier *= 10;
            decimalValue /= targetBase;
        }
        return numberInTargetBase;
    }
}