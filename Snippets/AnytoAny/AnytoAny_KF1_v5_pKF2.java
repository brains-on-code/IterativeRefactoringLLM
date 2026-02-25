package com.thealgorithms.conversions;

public final class BaseConverter {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 10;

    private BaseConverter() {}

    public static int convert(int number, int fromBase, int toBase) {
        validateBase(fromBase);
        validateBase(toBase);

        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
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

    private static int fromDecimal(int decimal, int base) {
        int convertedValue = 0;
        int placeValue = 1;

        while (decimal != 0) {
            int digit = decimal % base;
            convertedValue += digit * placeValue;
            placeValue *= 10;
            decimal /= base;
        }
        return convertedValue;
    }

    private static void validateBase(int base) {
        if (base < MIN_BASE || base > MAX_BASE) {
            throw new IllegalArgumentException(
                String.format("Base must be between %d and %d.", MIN_BASE, MAX_BASE)
            );
        }
    }
}