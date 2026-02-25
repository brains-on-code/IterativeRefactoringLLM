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
                    number = readNumber(scanner);
                    sourceBase = readBase(scanner, "Enter beginning base");

                    if (!isValidNumberForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    targetBase = readBase(scanner, "Enter end base");
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    scanner.next(); // clear invalid token
                }
            }

            String result = convertBase(number, sourceBase, targetBase);
            System.out.println(result);
        }
    }

    private static String readNumber(Scanner scanner) {
        System.out.print("Enter number: ");
        return scanner.next().toUpperCase();
    }

    private static int readBase(Scanner scanner, String prompt) {
        while (true) {
            System.out.printf("%s (between %d and %d): ", prompt, MINIMUM_BASE, MAXIMUM_BASE);
            int base = scanner.nextInt();

            if (isValidBase(base)) {
                return base;
            }

            System.out.println("Invalid base!");
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
        return DIGITS.indexOf(c);
    }

    private static char valueToChar(int value) {
        return DIGITS.charAt(value);
    }
}