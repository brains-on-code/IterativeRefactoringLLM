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
        try (Scanner in = new Scanner(System.in)) {
            String number;
            int sourceBase;
            int targetBase;

            while (true) {
                try {
                    System.out.print("Enter number: ");
                    number = in.next().toUpperCase();

                    System.out.print(
                        "Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): "
                    );
                    sourceBase = in.nextInt();
                    if (!isBaseInRange(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!isValidForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    System.out.print(
                        "Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): "
                    );
                    targetBase = in.nextInt();
                    if (!isBaseInRange(targetBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next(); // clear invalid token
                }
            }

            System.out.println(convertBase(number, sourceBase, targetBase));
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
        c = Character.toUpperCase(c);
        int index = VALID_DIGITS.indexOf(c);
        return index >= 0 ? index : -1;
    }

    private static char digitToChar(int digit) {
        return VALID_DIGITS.charAt(digit);
    }
}