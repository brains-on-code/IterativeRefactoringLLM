package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class Class1 {

    private static final int MIN_BASE = 2;
    private static final int MAX_BASE = 36;
    private static final char[] DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private Class1() {
    }

    public static void method1(String[] args) {
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

                    if (!method2(number, fromBase)) {
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
                    scanner.next();
                }
            }

            System.out.println(method3(number, fromBase, toBase));
        }
    }

    private static boolean isBaseInRange(int base) {
        return base >= MIN_BASE && base <= MAX_BASE;
    }

    public static boolean method2(String number, int base) {
        char[] allowedDigits = Arrays.copyOfRange(DIGITS, 0, base);
        HashSet<Character> allowedSet = new HashSet<>();
        for (char c : allowedDigits) {
            allowedSet.add(c);
        }

        for (char c : number.toUpperCase().toCharArray()) {
            if (!allowedSet.contains(c)) {
                return false;
            }
        }

        return true;
    }

    public static String method3(String number, int fromBase, int toBase) {
        int decimalValue = 0;

        for (int i = 0; i < number.length(); i++) {
            char currentChar = number.charAt(i);
            int digitValue;

            if (currentChar >= 'A' && currentChar <= 'Z') {
                digitValue = 10 + (currentChar - 'A');
            } else {
                digitValue = currentChar - '0';
            }

            decimalValue = decimalValue * fromBase + digitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        while (decimalValue != 0) {
            int remainder = decimalValue % toBase;
            if (remainder < 10) {
                result.insert(0, remainder);
            } else {
                result.insert(0, (char) (remainder - 10 + 'A'));
            }
            decimalValue /= toBase;
        }

        return result.toString();
    }
}