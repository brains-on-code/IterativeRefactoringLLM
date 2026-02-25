package com.thealgorithms.conversions;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class BaseConverter {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private BaseConverter() {
        // Utility class; prevent instantiation
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            runInteractiveConversion(scanner);
        }
    }

    private static void runInteractiveConversion(Scanner scanner) {
        while (true) {
            try {
                String number = readNumber(scanner);
                int fromBase = readBase(scanner, "Enter beginning base");
                if (!isValidForBase(number, fromBase)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }

                int toBase = readBase(scanner, "Enter end base");
                String converted = convertBase(number, fromBase, toBase);
                System.out.println(converted);
                return;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next(); // clear invalid token
            }
        }
    }

    private static String readNumber(Scanner scanner) {
        System.out.print("Enter number: ");
        return scanner.next().toUpperCase();
    }

    private static int readBase(Scanner scanner, String prompt) {
        while (true) {
            System.out.printf("%s (between %d and %d): ", prompt, MIN_BASE, MAX_BASE);
            int base = scanner.nextInt();
            if (isBaseInRange(base)) {
                return base;
            }
            System.out.println("Invalid base!");
        }
    }

    private static boolean isBaseInRange(int base) {
        return base >= MIN_BASE && base <= MAX_BASE;
    }

    public static boolean isValidForBase(String number, int base) {
        String upperNumber = number.toUpperCase();
        for (char c : upperNumber.toCharArray()) {
            int digitValue = DIGITS.indexOf(c);
            if (!isValidDigitForBase(digitValue, base)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidDigitForBase(int digitValue, int base) {
        return digitValue >= 0 && digitValue < base;
    }

    public static String convertBase(String number, int fromBase, int toBase) {
        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    private static int toDecimal(String number, int base) {
        int decimalValue = 0;
        String upperNumber = number.toUpperCase();
        for (char c : upperNumber.toCharArray()) {
            int digitValue = DIGITS.indexOf(c);
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
            result.insert(0, DIGITS.charAt(remainder));
            value /= base;
        }

        return result.toString();
    }
}