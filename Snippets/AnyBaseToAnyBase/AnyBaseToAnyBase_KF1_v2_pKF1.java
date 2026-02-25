package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 */
public final class BaseConverter {

    private BaseConverter() {
    }

    /** Minimum supported base. */
    static final int MIN_BASE = 2;

    /** Maximum supported base. */
    static final int MAX_BASE = 36;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputNumber;
        int sourceBase;
        int targetBase;

        while (true) {
            try {
                System.out.print("Enter number: ");
                inputNumber = scanner.next().toUpperCase();

                System.out.print("Enter beginning base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                sourceBase = scanner.nextInt();
                if (sourceBase < MIN_BASE || sourceBase > MAX_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }

                if (!isValidForBase(inputNumber, sourceBase)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }

                System.out.print("Enter end base (between " + MIN_BASE + " and " + MAX_BASE + "): ");
                targetBase = scanner.nextInt();
                if (targetBase < MIN_BASE || targetBase > MAX_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next();
            }
        }

        System.out.println(convertBase(inputNumber, sourceBase, targetBase));
        scanner.close();
    }

    /**
     * Checks whether the given number string is valid for the specified base.
     *
     * @param number the number as a string (expected uppercase for alphabetic digits)
     * @param base   the base to validate against
     * @return true if the number is valid for the base, false otherwise
     */
    public static boolean isValidForBase(String number, int base) {
        char[] digitAlphabet = {
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

        char[] allowedDigits = Arrays.copyOfRange(digitAlphabet, 0, base);

        HashSet<Character> allowedDigitSet = new HashSet<>();
        for (char digit : allowedDigits) {
            allowedDigitSet.add(digit);
        }

        for (char character : number.toCharArray()) {
            if (!allowedDigitSet.contains(character)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a number from a source base to a target base.
     *
     * @param number     the number as a string (expected uppercase for alphabetic digits)
     * @param sourceBase the base of the input number
     * @param targetBase the base to convert the number to
     * @return the converted number as a string
     */
    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;
        StringBuilder convertedNumber = new StringBuilder();

        for (int index = 0; index < number.length(); index++) {
            char currentChar = number.charAt(index);
            int digitValue;
            if (currentChar >= 'A' && currentChar <= 'Z') {
                digitValue = 10 + (currentChar - 'A');
            } else {
                digitValue = currentChar - '0';
            }
            decimalValue = decimalValue * sourceBase + digitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        while (decimalValue != 0) {
            int remainder = decimalValue % targetBase;
            if (remainder < 10) {
                convertedNumber.insert(0, remainder);
            } else {
                convertedNumber.insert(0, (char) (remainder + 55));
            }
            decimalValue /= targetBase;
        }

        return convertedNumber.toString();
    }
}