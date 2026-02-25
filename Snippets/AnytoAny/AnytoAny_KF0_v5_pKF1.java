package com.thealgorithms.conversions;

/**
 * A utility class for converting numbers from any base to any other base.
 *
 * This class provides a method to convert a source number from a given base
 * to a destination number in another base. Valid bases range from 2 to 10.
 */
public final class BaseConverter {

    private BaseConverter() {
    }

    /**
     * Converts a number from a source base to a destination base.
     *
     * @param sourceNumber The number in the source base (as an integer).
     * @param sourceBase The base of the source number (between 2 and 10).
     * @param targetBase The base to which the number should be converted (between 2 and 10).
     * @throws IllegalArgumentException if the bases are not between 2 and 10.
     * @return The converted number in the destination base (as an integer).
     */
    public static int convertBase(int sourceNumber, int sourceBase, int targetBase) {
        if (!isBaseInValidRange(sourceBase) || !isBaseInValidRange(targetBase)) {
            throw new IllegalArgumentException("Bases must be between 2 and 10.");
        }

        int decimalValue = convertToDecimal(sourceNumber, sourceBase);
        return convertFromDecimal(decimalValue, targetBase);
    }

    private static boolean isBaseInValidRange(int base) {
        return base >= 2 && base <= 10;
    }

    /**
     * Converts a number from a given base to its decimal representation (base 10).
     *
     * @param sourceNumber The number in the original base.
     * @param sourceBase The base of the given number.
     * @return The decimal representation of the number.
     */
    private static int convertToDecimal(int sourceNumber, int sourceBase) {
        int decimalValue = 0;
        int placeValue = 1;

        int remainingNumber = sourceNumber;
        while (remainingNumber != 0) {
            int digit = remainingNumber % 10;
            decimalValue += digit * placeValue;
            placeValue *= sourceBase;
            remainingNumber /= 10;
        }
        return decimalValue;
    }

    /**
     * Converts a decimal (base 10) number to a specified base.
     *
     * @param decimalValue The decimal number to convert.
     * @param targetBase The destination base for conversion.
     * @return The number in the specified base.
     */
    private static int convertFromDecimal(int decimalValue, int targetBase) {
        int convertedValue = 0;
        int placeValue = 1;

        int remainingDecimal = decimalValue;
        while (remainingDecimal != 0) {
            int digit = remainingDecimal % targetBase;
            convertedValue += digit * placeValue;
            placeValue *= 10;
            remainingDecimal /= targetBase;
        }
        return convertedValue;
    }
}