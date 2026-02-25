package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public final class AnyBaseToAnyBase {

    private static final int MINIMUM_BASE = 2;
    private static final int MAXIMUM_BASE = 36;
    private static final char[] VALID_DIGITS = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
        'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
        'U', 'V', 'W', 'X', 'Y', 'Z'
    };

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

    public static boolean validForBase(String number, int base) {
        char[] digitsForBase = Arrays.copyOfRange(VALID_DIGITS, 0, base);
        HashSet<Character> validDigitSet = new HashSet<>();

        for (char digit : digitsForBase) {
            validDigitSet.add(digit);
        }

        for (char c : number.toCharArray()) {
            if (!validDigitSet.contains(c)) {
                return false;
            }
        }

        return true;
    }

    public static String base2base(String number, int sourceBase, int targetBase) {
        int decimalValue = 0;

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

        StringBuilder output = new StringBuilder();
        while (decimalValue != 0) {
            int remainder = decimalValue % targetBase;
            if (remainder < 10) {
                output.insert(0, remainder);
            } else {
                output.insert(0, (char) (remainder - 10 + 'A'));
            }
            decimalValue /= targetBase;
        }

        return output.toString();
    }
}