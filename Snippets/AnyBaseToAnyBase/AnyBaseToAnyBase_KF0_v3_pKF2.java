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
                    System.out.print("Enter number: ");
                    number = in.next().toUpperCase();

                    System.out.print(
                        "Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): "
                    );
                    sourceBase = in.nextInt();
                    if (!isBaseInRange(sourceBase)) {
                        System.out.println("Invalid base!");
                        continue;
                    }

                    if (!validForBase(number, sourceBase)) {
                        System.out.println("The number is invalid for this base!");
                        continue;
                    }

                    System.out.print(
                        "Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): "
                    );
                    targetBase = in.nextInt();
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

            System.out.println(base2base(number, sourceBase, targetBase));
        }
    }

    private static boolean isBaseInRange(int base) {
        return base >= MINIMUM_BASE && base <= MAXIMUM_BASE;
    }

    /**
     * Checks if a number (as a String) is valid for a given base.
     *
     * @param n    the number to validate (uppercase letters expected for digits >= 10)
     * @param base the base to validate against
     * @return {@code true} if all digits in {@code n} are valid for {@code base},
     *         {@code false} otherwise
     */
    public static boolean validForBase(String n, int base) {
        if (!isBaseInRange(base)) {
            return false;
        }

        for (char c : n.toCharArray()) {
            int digitValue = DIGITS.indexOf(c);
            if (digitValue == -1 || digitValue >= base) {
                return false;
            }
        }

        return true;
    }

    /**
     * Converts a number from base {@code b1} to base {@code b2}.
     *
     * @param n  the number to be converted (uppercase letters expected for digits >= 10)
     * @param b1 the source base
     * @param b2 the target base
     * @return {@code n} represented in base {@code b2}
     */
    public static String base2base(String n, int b1, int b2) {
        int decimalValue = 0;

        for (int i = 0; i < n.length(); i++) {
            char currentChar = n.charAt(i);
            int digitValue = DIGITS.indexOf(currentChar);
            decimalValue = decimalValue * b1 + digitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder output = new StringBuilder();

        while (decimalValue != 0) {
            int remainder = decimalValue % b2;
            output.insert(0, DIGITS.charAt(remainder));
            decimalValue /= b2;
        }

        return output.toString();
    }
}