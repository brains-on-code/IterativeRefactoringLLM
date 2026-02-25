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
        String number;
        int sourceBase;
        int targetBase;

        while (true) {
            try {
                System.out.print("Enter number: ");
                number = scanner.next();

                System.out.print("Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                sourceBase = scanner.nextInt();
                if (sourceBase > MAXIMUM_BASE || sourceBase < MINIMUM_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }

                if (!isValidForBase(number, sourceBase)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }

                System.out.print("Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                targetBase = scanner.nextInt();
                if (targetBase > MAXIMUM_BASE || targetBase < MINIMUM_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                scanner.next();
            }
        }

        System.out.println(convertBase(number, sourceBase, targetBase));
        scanner.close();
    }

    public static boolean isValidForBase(String number, int base) {
        char[] validDigits = {
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

        char[] digitsForBase = Arrays.copyOfRange(validDigits, 0, base);

        HashSet<Character> validDigitSet = new HashSet<>();
        for (char digit : digitsForBase) {
            validDigitSet.add(digit);
        }

        for (char character : number.toCharArray()) {
            if (!validDigitSet.contains(character)) {
                return false;
            }
        }

        return true;
    }

    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < number.length(); i++) {
            char currentChar = number.charAt(i);
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
                output.insert(0, remainder);
            } else {
                output.insert(0, (char) (remainder + 55));
            }
            decimalValue /= targetBase;
        }

        return output.toString();
    }
}