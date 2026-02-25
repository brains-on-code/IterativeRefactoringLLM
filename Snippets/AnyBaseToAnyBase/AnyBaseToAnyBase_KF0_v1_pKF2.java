package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utility class for converting numbers between bases 2 and 36.
 *
 * <p>Conversion is done by first converting from the source base to decimal,
 * then from decimal to the target base.
 */
public final class AnyBaseToAnyBase {

    private AnyBaseToAnyBase() {
        // Utility class; prevent instantiation
    }

    /** Smallest base accepted as valid input. */
    static final int MINIMUM_BASE = 2;

    /** Largest base accepted as valid input. */
    static final int MAXIMUM_BASE = 36;

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
                    in.next(); // clear invalid token
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
        char[] validDigits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
        };

        char[] digitsForBase = Arrays.copyOfRange(validDigits, 0, base);

        HashSet<Character> digitsSet = new HashSet<>();
        for (char digit : digitsForBase) {
            digitsSet.add(digit);
        }

        for (char c : n.toCharArray()) {
            if (!digitsSet.contains(c)) {
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
            int digitValue;

            if (currentChar >= 'A' && currentChar <= 'Z') {
                digitValue = 10 + (currentChar - 'A');
            } else {
                digitValue = currentChar - '0';
            }

            decimalValue = decimalValue * b1 + digitValue;
        }

        if (decimalValue == 0) {
            return "0";
        }

        StringBuilder output = new StringBuilder();

        while (decimalValue != 0) {
            int remainder = decimalValue % b2;

            if (remainder < 10) {
                output.insert(0, remainder);
            } else {
                output.insert(0, (char) (remainder - 10 + 'A'));
            }

            decimalValue /= b2;
        }

        return output.toString();
    }
}