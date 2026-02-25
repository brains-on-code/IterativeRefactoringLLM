package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 */
public final class Class1 {

    private Class1() {
        // Utility class; prevent instantiation
    }

    /** Minimum supported base. */
    static final int MIN_BASE = 2;

    /** Maximum supported base. */
    static final int MAX_BASE = 36;

    public static void method1(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String number;
        int fromBase;
        int toBase;

        while (true) {
            try {
                System.out.print("Enter number: ");
                number = scanner.next();

                System.out.print("Enter beginning base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                fromBase = scanner.nextInt();
                if (fromBase > MAX_BASE || fromBase < MIN_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }

                if (!method2(number, fromBase)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }

                System.out.print("Enter end base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                toBase = scanner.nextInt();
                if (toBase > MAX_BASE || toBase < MIN_BASE) {
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
        scanner.close();
    }

    /**
     * Validates that the given number string is valid for the specified base.
     *
     * @param number the number as a string (using digits 0-9 and uppercase A-Z)
     * @param base   the base to validate against
     * @return true if the number is valid for the base, false otherwise
     */
    public static boolean method2(String number, int base) {
        char[] digits = {
            '0',
            '1',
            '2',
            '3',
            '4',
            '5',
            '6',
            '7',
            '8',
            '9',
            'A',
            'B',
            'C',
            'D',
            'E',
            'F',
            'G',
            'H',
            'I',
            'J',
            'K',
            'L',
            'M',
            'N',
            'O',
            'P',
            'Q',
            'R',
            'S',
            'T',
            'U',
            'V',
            'W',
            'X',
            'Y',
            'Z',
        };

        // Allowed characters for the given base
        char[] allowedDigits = Arrays.copyOfRange(digits, 0, base);

        HashSet<Character> allowedSet = new HashSet<>();
        for (char c : allowedDigits) {
            allowedSet.add(c);
        }

        // Check that every character in the number is allowed for this base
        for (char c : number.toCharArray()) {
            if (!allowedSet.contains(c)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a number from one base to another.
     *
     * @param number   the number as a string (using digits 0-9 and uppercase A-Z)
     * @param fromBase the base the number is currently in
     * @param toBase   the base to convert the number to
     * @return the converted number as a string
     */
    public static String method3(String number, int fromBase, int toBase) {
        int decimalValue = 0;
        int digitValue;
        char digitChar;
        StringBuilder result = new StringBuilder();

        // Convert from source base to decimal
        for (int i = 0; i < number.length(); i++) {
            digitChar = number.charAt(i);
            if (digitChar >= 'A' && digitChar <= 'Z') {
                digitValue = 10 + (digitChar - 'A');
            } else {
                digitValue = digitChar - '0';
            }
            decimalValue = decimalValue * fromBase + digitValue;
        }

        // Special case: zero
        if (decimalValue == 0) {
            return "0";
        }

        // Convert from decimal to target base
        while (decimalValue != 0) {
            int remainder = decimalValue % toBase;
            if (remainder < 10) {
                result.insert(0, remainder);
            } else {
                result.insert(0, (char) (remainder + 55)); // 10 -> 'A', 11 -> 'B', ...
            }
            decimalValue /= toBase;
        }

        return result.toString();
    }
}