package com.thealgorithms.conversions;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;


public final class AnyBaseToAnyBase {
    private AnyBaseToAnyBase() {
    }


    static final int MINIMUM_BASE = 2;

    static final int MAXIMUM_BASE = 36;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String n;
        int b1;
        int b2;
        while (true) {
            try {
                System.out.print("Enter number: ");
                n = in.next();
                System.out.print("Enter beginning base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                b1 = in.nextInt();
                if (b1 > MAXIMUM_BASE || b1 < MINIMUM_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }
                if (!validForBase(n, b1)) {
                    System.out.println("The number is invalid for this base!");
                    continue;
                }
                System.out.print("Enter end base (between " + MINIMUM_BASE + " and " + MAXIMUM_BASE + "): ");
                b2 = in.nextInt();
                if (b2 > MAXIMUM_BASE || b2 < MINIMUM_BASE) {
                    System.out.println("Invalid base!");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                in.next();
            }
        }
        System.out.println(base2base(n, b1, b2));
        in.close();
    }


    public static boolean validForBase(String n, int base) {
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

        HashSet<Character> digitsList = new HashSet<>();
        for (int i = 0; i < digitsForBase.length; i++) {
            digitsList.add(digitsForBase[i]);
        }

        for (char c : n.toCharArray()) {
            if (!digitsList.contains(c)) {
                return false;
            }
        }

        return true;
    }


    public static String base2base(String n, int b1, int b2) {
        int decimalValue = 0;
        int charB2;
        char charB1;
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < n.length(); i++) {
            charB1 = n.charAt(i);
            if (charB1 >= 'A' && charB1 <= 'Z') {
                charB2 = 10 + (charB1 - 'A');
            }
            else {
                charB2 = charB1 - '0';
            }
            decimalValue = decimalValue * b1 + charB2;
        }

        if (0 == decimalValue) {
            return "0";
        }
        while (decimalValue != 0) {
            if (decimalValue % b2 < 10) {
                output.insert(0, decimalValue % b2);
            }
            else {
                output.insert(0, (char) ((decimalValue % b2) + 55));
            }
            decimalValue /= b2;
        }
        return output.toString();
    }
}
