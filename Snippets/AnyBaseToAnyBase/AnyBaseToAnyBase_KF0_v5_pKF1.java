package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for converting from "any" base to "any" other base, when "any" means
 * from 2-36. Works by going from base 1 to decimal to base 2. Includes
 * auxiliary method for determining whether a number is valid for a given base.
 *
 * @author Michael Rolland
 * @version 2017.10.10
 */
public final class AnyBaseToAnyBase {

    private AnyBaseToAnyBase() {
    }

    /**
     * Smallest and largest base you want to accept as valid input
     */
    static final int MINIMUM_BASE = 2;
    static final int MAXIMUM_BASE = 36;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputNumber;
        int sourceBase;
        int targetBase;

        while (true) {
            try {
                System.out.print("Enter number: ");
                inputNumber = scanner.next().toUpperCase();

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

    /**
     * Checks if a number (as a String) is valid for a given base.
     */
    public static boolean isValidForBase(String number, int base) {
        char[] allPossibleDigits = {
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

        char[] validDigitsForBase = Arrays.copyOfRange(allPossibleDigits, 0, base);

        HashSet<Character> validDigitSet = new HashSet<>();
        for (char digit : validDigitsForBase) {
            validDigitSet.add(digit);
        }

        for (char digit : number.toCharArray()) {
            if (!validDigitSet.contains(digit)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Method to convert any integer from sourceBase to targetBase. Works by
     * converting from sourceBase to decimal, then decimal to targetBase.
     *
     * @param number The integer to be converted.
     * @param sourceBase Beginning base.
     * @param targetBase End base.
     * @return number in targetBase.
     */
    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;
        StringBuilder convertedNumber = new StringBuilder();

        for (int index = 0; index < number.length(); index++) {
            char digitChar = number.charAt(index);
            int digitValue;
            if (digitChar >= 'A' && digitChar <= 'Z') {
                digitValue = 10 + (digitChar - 'A');
            } else {
                digitValue = digitChar - '0';
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
                convertedNumber.insert(0, (char) (remainder + 55)); // 55 = 'A' - 10
            }
            decimalValue /= targetBase;
        }

        return convertedNumber.toString();
    }
}