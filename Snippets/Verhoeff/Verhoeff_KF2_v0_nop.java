package com.thealgorithms.others;

import java.util.Objects;


public final class Verhoeff {
    private Verhoeff() {
    }


    private static final byte[][] MULTIPLICATION_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 2, 3, 4, 0, 6, 7, 8, 9, 5},
        {2, 3, 4, 0, 1, 7, 8, 9, 5, 6},
        {3, 4, 0, 1, 2, 8, 9, 5, 6, 7},
        {4, 0, 1, 2, 3, 9, 5, 6, 7, 8},
        {5, 9, 8, 7, 6, 0, 4, 3, 2, 1},
        {6, 5, 9, 8, 7, 1, 0, 4, 3, 2},
        {7, 6, 5, 9, 8, 2, 1, 0, 4, 3},
        {8, 7, 6, 5, 9, 3, 2, 1, 0, 4},
        {9, 8, 7, 6, 5, 4, 3, 2, 1, 0},
    };


    private static final byte[] MULTIPLICATIVE_INVERSE = {
        0,
        4,
        3,
        2,
        1,
        5,
        6,
        7,
        8,
        9,
    };


    private static final byte[][] PERMUTATION_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8},
    };


    public static boolean verhoeffCheck(String digits) {
        checkInput(digits);
        int[] numbers = toIntArray(digits);

        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int index = numbers.length - i - 1;
            byte b = PERMUTATION_TABLE[i % 8][numbers[index]];
            checksum = MULTIPLICATION_TABLE[checksum][b];
        }

        return checksum == 0;
    }


    public static String addVerhoeffChecksum(String initialDigits) {
        checkInput(initialDigits);

        var modifiedDigits = initialDigits + "0";

        int[] numbers = toIntArray(modifiedDigits);

        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int index = numbers.length - i - 1;
            byte b = PERMUTATION_TABLE[i % 8][numbers[index]];
            checksum = MULTIPLICATION_TABLE[checksum][b];
        }
        checksum = MULTIPLICATIVE_INVERSE[checksum];

        return initialDigits + checksum;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        var validInput = "2363";
        var invalidInput = "2364";
        checkAndPrint(validInput);
        checkAndPrint(invalidInput);

        System.out.println("\nCheck digit generation example:");
        var input = "236";
        generateAndPrint(input);
    }

    private static void checkAndPrint(String input) {
        String validationResult = Verhoeff.verhoeffCheck(input) ? "valid" : "not valid";
        System.out.println("Input '" + input + "' is " + validationResult);
    }

    private static void generateAndPrint(String input) {
        String result = addVerhoeffChecksum(input);
        System.out.println("Generate and add checksum to initial value '" + input + "'. Result: '" + result + "'");
    }

    private static void checkInput(String input) {
        Objects.requireNonNull(input);
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + input + "' contains not only digits");
        }
    }

    private static int[] toIntArray(String string) {
        return string.chars().map(i -> Character.digit(i, 10)).toArray();
    }
}
