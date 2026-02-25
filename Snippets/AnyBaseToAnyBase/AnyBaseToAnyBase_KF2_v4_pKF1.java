package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class AnyBaseToAnyBase {

    private AnyBaseToAnyBase() {}

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputNumber;
        int sourceBase;
        int targetBase;

        while (true) {
            try {
                System.out.print("Enter number: ");
                inputNumber = scanner.next();

                System.out.print("Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                sourceBase = scanner.nextInt();
                if (!isBaseInRange(sourceBase)) {
                    System.out.println("Invalid base!");
                    continue;
                }

                if (!isValidForBase(inputNumber, sourceBase)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }

                System.out.print("Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                targetBase = scanner.nextInt();
                if (!isBaseInRange(targetBase)) {
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

    private static boolean isBaseInRange(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

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

        for (char digit : number.toCharArray()) {
            if (!allowedDigitSet.contains(digit)) {
                return false;
            }
        }

        return true;
    }

    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;
        StringBuilder convertedNumberBuilder = new StringBuilder();

        for (int index = 0; index < number.length(); index++) {
            char currentDigitChar = number.charAt(index);
            int currentDigitValue;

            if (currentDigitChar >= 'A' && currentDigitChar <= 'Z') {
                currentDigitValue = 10 + (currentDigitChar - 'A');
            } else {
                currentDigitValue = currentDigitChar - '0';
            }

            decimalValue = decimalValue * sourceBase + currentDigitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        while (decimalValue != 0) {
            int remainder = decimalValue % targetBase;
            if (remainder < 10) {
                convertedNumberBuilder.insert(0, remainder);
            } else {
                convertedNumberBuilder.insert(0, (char) (remainder + 55));
            }
            decimalValue /= targetBase;
        }

        return convertedNumberBuilder.toString();
    }
}