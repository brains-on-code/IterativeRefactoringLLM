package com.thealgorithms.conversions;

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

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final String DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private AnyBaseToAnyBase() {
        // Utility class
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

                    System.out.print("Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                    sourceBase = in.nextInt();
                    if (!isValidBase(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!validForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    System.out.print("Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                    targetBase = in.nextInt();
                    if (!isValidBase(targetBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    in.next(); // clear invalid token
                }
            }

            System.out.println(base2base(number, sourceBase, targetBase));
        }
    }

    private static boolean isValidBase(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    /**
     * Checks if a number (as a String) is valid for a given base.
     */
    public static boolean validForBase(String n, int base) {
        if (n == null || n.isEmpty()) {
            return false;
        }

        for (char c : n.toUpperCase().toCharArray()) {
            int value = charToValue(c);
            if (value < 0 || value >= base) {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to convert any integer from base b1 to base b2. Works by
     * converting from b1 to decimal, then decimal to b2.
     *
     * @param n  The integer to be converted.
     * @param b1 Beginning base.
     * @param b2 End base.
     * @return n in base b2.
     */
    public static String base2base(String n, int b1, int b2) {
        int decimalValue = toDecimal(n.toUpperCase(), b1);
        return fromDecimal(decimalValue, b2);
    }

    private static int toDecimal(String n, int base) {
        int decimalValue = 0;
        for (char c : n.toCharArray()) {
            int digitValue = charToValue(c);
            decimalValue = decimalValue * base + digitValue;
        }
        return decimalValue;
    }

    private static String fromDecimal(int decimalValue, int base) {
        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder result = new StringBuilder();
        while (decimalValue > 0) {
            int remainder = decimalValue % base;
            result.insert(0, valueToChar(remainder));
            decimalValue /= base;
        }
        return result.toString();
    }

    private static int charToValue(char c) {
        int index = DIGITS.indexOf(c);
        return index >= 0 ? index : -1;
    }

    private static char valueToChar(int value) {
        return DIGITS.charAt(value);
    }
}