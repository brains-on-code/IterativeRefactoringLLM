package com.thealgorithms.conversions;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 */
public final class BaseConverter {

    /** Minimum supported base. */
    static final int MIN_BASE = 2;

    /** Maximum supported base. */
    static final int MAX_BASE = 36;

    /** Digit characters used for bases up to 36. */
    private static final char[] DIGITS = {
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

    private BaseConverter() {
        // Utility class; prevent instantiation.
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

                    if (!isValidNumberForBase(number, fromBase)) {
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
                    scanner.next(); // Clear invalid token
                }
            }

            System.out.println(convertBase(number, fromBase, toBase));
        }
    }

    /**
     * Checks whether a base is within the supported range.
     *
     * @param base the base to check
     * @return {@code true} if the base is between {@link #MIN_BASE} and {@link #MAX_BASE} (inclusive),
     *         {@code false} otherwise
     */
    private static boolean isBaseInRange(int base) {
        return base >= MIN_BASE && base <= MAX_BASE;
    }

    /**
     * Validates that the given number string is valid for the specified base.
     *
     * @param number the number as a string (using digits 0-9 and uppercase A-Z)
     * @param base   the base to validate against
     * @return {@code true} if the number is valid for the base, {@code false} otherwise
     */
    public static boolean isValidNumberForBase(String number, int base) {
        HashSet<Character> allowedDigits = new HashSet<>();
        for (int i = 0; i < base; i++) {
            allowedDigits.add(DIGITS[i]);
        }

        for (char c : number.toCharArray()) {
            if (!allowedDigits.contains(c)) {
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
    public static String convertBase(String number, int fromBase, int toBase) {
        int decimalValue = 0;

        for (int i = 0; i < number.length(); i++) {
            char digitChar = number.charAt(i);
            int digitValue =
                (digitChar >= 'A' && digitChar <= 'Z')
                    ? 10 + (digitChar - 'A')
                    : digitChar - '0';
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