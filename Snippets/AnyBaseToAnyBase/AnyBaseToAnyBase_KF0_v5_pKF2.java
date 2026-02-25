package com.thealgorithms.conversions;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 *
 * <p>Conversion is done by first converting from the source base to decimal,
 * then from decimal to the target base.
 */
public final class AnyBaseToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

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
                    number = readNumber(in);
                    sourceBase = readBase(in, "Enter beginning base");
                    if (!isBaseInRange(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!isValidForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    targetBase = readBase(in, "Enter end base");
                    if (!isBaseInRange(targetBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next(); // discard invalid token
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

    private static boolean isBaseInRange(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    /**
     * Checks if a number is valid for a given base.
     *
     * @param number the number to validate (uppercase letters expected for digits >= 10)
     * @param base   the base to validate against
     * @return {@code true} if all digits in {@code number} are valid for {@code base},
     *         {@code false} otherwise
     */
    public static boolean isValidForBase(String number, int base) {
        if (!isBaseInRange(base)) {
            return false;
        }

        for (char c : number.toCharArray()) {
            int digitValue = DIGITS.indexOf(c);
            if (digitValue == -1 || digitValue >= base) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a number from one base to another.
     *
     * @param number     the number to be converted (uppercase letters expected for digits >= 10)
     * @param sourceBase the source base
     * @param targetBase the target base
     * @return {@code number} represented in {@code targetBase}
     */
    public static String convertBase(String number, int sourceBase, int targetBase) {
        int decimalValue = toDecimal(number, sourceBase);

        if (decimalValue == 0) {
            return "0";
        }

        return fromDecimal(decimalValue, targetBase);
    }

    private static int toDecimal(String number, int base) {
        int decimalValue = 0;

        for (int i = 0; i < number.length(); i++) {
            char currentChar = number.charAt(i);
            int digitValue = DIGITS.indexOf(currentChar);
            decimalValue = decimalValue * base + digitValue;
        }

        return decimalValue;
    }

    private static String fromDecimal(int decimalValue, int base) {
        StringBuilder output = new StringBuilder();

        while (decimalValue != 0) {
            int remainder = decimalValue % base;
            output.insert(0, DIGITS.charAt(remainder));
            decimalValue /= base;
        }

        return output.toString();
    }
}