package com.thealgorithms.conversions;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class AnyBaseToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final String VALID_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private AnyBaseToAnyBase() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            String number = promptForNumber(scanner);
            int sourceBase = promptForBase(scanner, "Enter beginning base");

            while (!isValidForBase(number, sourceBase)) {
                System.out.println("The number is invalid for this base!");
                number = promptForNumber(scanner);
                sourceBase = promptForBase(scanner, "Enter beginning base");
            }

            int targetBase = promptForBase(scanner, "Enter end base");
            String convertedNumber = convertBase(number, sourceBase, targetBase);

            System.out.println(convertedNumber);
        }
    }

    private static String promptForNumber(Scanner scanner) {
        System.out.print("Enter number: ");
        return scanner.next().toUpperCase();
    }

    private static int promptForBase(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.printf("%s (between %d and %d): ", prompt, MINIMUM_BASE, MAXIMUM_BASE);
                int base = scanner.nextInt();

                if (isBaseInRange(base)) {
                    return base;
                }

                System.out.println("Invalid base!");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // clear invalid token
            }
        }
    }

    private static boolean isBaseInRange(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    public static boolean isValidForBase(String number, int base) {
        for (char c : number.toCharArray()) {
            int digitValue = charToDigit(c);
            if (digitValue < 0 || digitValue >= base) {
                return false;
            }
        }
        return true;
    }

    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = toDecimal(number, sourceBase);
        return fromDecimal(decimalValue, targetBase);
    }

    private static int toDecimal(String number, int base) {
        int decimalValue = 0;
        for (char c : number.toCharArray()) {
            int digitValue = charToDigit(c);
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
            result.insert(0, digitToChar(remainder));
            value /= base;
        }

        return result.toString();
    }

    private static int charToDigit(char c) {
        char upperChar = Character.toUpperCase(c);
        int index = VALID_DIGITS.indexOf(upperChar);
        return index >= 0 ? index : -1;
    }

    private static char digitToChar(int digit) {
        return VALID_DIGITS.charAt(digit);
    }
}