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
            String number;
            int fromBase;
            int toBase;

            while (true) {
                try {
                    System.out.print("Enter number: ");
                    number = scanner.next().toUpperCase();

                    System.out.print("Enter beginning base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                    fromBase = scanner.nextInt();
                    if (!isBaseInRange(fromBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!isValidForBase(number, fromBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    System.out.print("Enter end base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                    toBase = scanner.nextInt();
                    if (!isBaseInRange(toBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    scanner.next(); // clear invalid token
                }
            }

            System.out.println(convertBase(number, fromBase, toBase));
        }
    }

    private static boolean isBaseInRange(int base) {
        return base >= MIN_BASE && base <= MAX_BASE;
    }

    public static boolean isValidForBase(String number, int base) {
        for (char c : number.toUpperCase().toCharArray()) {
            int digitValue = DIGITS.indexOf(c);
            if (digitValue == -1 || digitValue >= base) {
                return false;
            }
        }
        return true;
    }

    public static String convertBase(String number, int fromBase, int toBase) {
        int decimalValue = toDecimal(number, fromBase);
        return fromDecimal(decimalValue, toBase);
    }

    private static int toDecimal(String number, int base) {
        int decimalValue = 0;
        for (char c : number.toUpperCase().toCharArray()) {
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