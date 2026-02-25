package com.thealgorithms.conversions;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 * Conversion is performed by going from the source base to decimal,
 * then from decimal to the target base.
 */
public final class AnyBaseToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private AnyBaseToAnyBase() {
        // Prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String number;
            int sourceBase;
            int targetBase;

            while (true) {
                try {
                    System.out.print("Enter number: ");
                    number = scanner.next().toUpperCase();

                    System.out.print("Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                    sourceBase = scanner.nextInt();
                    if (!isValidBase(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!isValidNumberForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    System.out.print("Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                    targetBase = scanner.nextInt();
                    if (!isValidBase(targetBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    scanner.next(); // clear invalid token
                }
            }

            System.out.println(convertBase(number, sourceBase, targetBase));
        }
    }

    private static boolean isValidBase(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    /**
     * Checks if a number (as a String) is valid for a given base.
     */
    public static boolean isValidNumberForBase(String number, int base) {
        if (number == null || number.isEmpty()) {
            return false;
        }

        String upperNumber = number.toUpperCase();
        for (char c : upperNumber.toCharArray()) {
            int value = charToValue(c);
            if (value < 0 || value >= base) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts a number from base {@code sourceBase} to base {@code targetBase}.
     *
     * @param number     The number to be converted.
     * @param sourceBase The base of the input number.
     * @param targetBase The base to convert the number to.
     * @return The converted number in base {@code targetBase}.
     */
    public static String convertBase(String number, int sourceBase, int targetBase) {
        String upperNumber = number.toUpperCase();
        int decimalValue = toDecimal(upperNumber, sourceBase);
        return fromDecimal(decimalValue, targetBase);
    }

    private static int toDecimal(String number, int base) {
        int decimalValue = 0;
        for (char c : number.toCharArray()) {
            int digitValue = charToValue(c);
            decimalValue = decimalValue * base + digitValue;
        }
        return decimalValue;
    }

    private static String fromDecimal(int decimalValue, int base) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        int value = decimalValue;

        while (value > 0) {
            int remainder = value % base;
            result.insert(0, valueToChar(remainder));
            value /= base;
        }

        return result.toString();
    }

    private static int charToValue(char c) {
        int index = DIGITS.indexOf(c);
        return index >= 0 ? index : -1;
    }

    private static char valueToChar(int value) {
        return DIGITS.charAt(value);
    }
}