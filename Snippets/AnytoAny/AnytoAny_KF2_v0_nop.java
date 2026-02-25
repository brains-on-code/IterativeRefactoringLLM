package com.thealgorithms.conversions;


public final class AnytoAny {
    private AnytoAny() {
    }


    public static int convertBase(int sourceNumber, int sourceBase, int destBase) {
        if (sourceBase < 2 || sourceBase > 10 || destBase < 2 || destBase > 10) {
            throw new IllegalArgumentException("Bases must be between 2 and 10.");
        }

        int decimalValue = toDecimal(sourceNumber, sourceBase);
        return fromDecimal(decimalValue, destBase);
    }


    private static int toDecimal(int number, int base) {
        int decimalValue = 0;
        int multiplier = 1;

        while (number != 0) {
            decimalValue += (number % 10) * multiplier;
            multiplier *= base;
            number /= 10;
        }
        return decimalValue;
    }


    private static int fromDecimal(int decimal, int base) {
        int result = 0;
        int multiplier = 1;

        while (decimal != 0) {
            result += (decimal % base) * multiplier;
            multiplier *= 10;
            decimal /= base;
        }
        return result;
    }
}
