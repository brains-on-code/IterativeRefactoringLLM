package com.thealgorithms.conversions;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class AnyBaseToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final char[] VALID_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private AnyBaseToAnyBase() {}

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            String number;
            int sourceBase;
            int targetBase;

            while (true) {
                try {
                    number = readNumber(in);
                    sourceBase = readBase(in, "Enter beginning base");
                    if (!isValidBase(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!isValidNumberForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    targetBase = readBase(in, "Enter end base");
                    if (!isValidBase(targetBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next();
                }
            }

            System.out.println(convertBase(number, sourceBase, targetBase));
        }
    }

    private static String readNumber(Scanner in) {
        System.out.print("Enter number: ");
        return in.next().toUpperCase();
    }

    private static int readBase(Scanner in, String prompt) {
        System.out.print(prompt + " (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
        return in.nextInt();
    }

    private static boolean isValidBase(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    public static boolean isValidNumberForBase(String number, int base) {
        HashSet<Character> validDigitSet = new HashSet<>();
        for (int i = 0; i < base; i++) {
            validDigitSet.add(VALID_DIGITS[i]);
        }

        for (char c : number.toCharArray()) {
            if (!validDigitSet.contains(c)) {
                return false;
            }
        }

        return true;
    }

    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;

        for (int i = 0; i < number.length(); i++) {
            char currentChar = number.charAt(i);
            int digitValue = charToDigit(currentChar);
            decimalValue = decimalValue * sourceBase + digitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder output = new StringBuilder();
        while (decimalValue != 0) {
            int remainder = decimalValue % targetBase;
            output.insert(0, digitToChar(remainder));
            decimalValue /= targetBase;
        }

        return output.toString();
    }

    private static int charToDigit(char c) {
        if (c >= 'A' && c <= 'Z') {
            return 10 + (c - 'A');
        }
        return c - '0';
    }

    private static char digitToChar(int digit) {
        if (digit < 10) {
            return (char) ('0' + digit);
        }
        return (char) ('A' + (digit - 10));
    }
}