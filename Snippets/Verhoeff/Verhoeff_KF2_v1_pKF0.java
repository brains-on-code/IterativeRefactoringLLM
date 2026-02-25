package com.thealgorithms.others;

import java.util.Objects;

public final class Verhoeff {

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
        0, 4, 3, 2, 1, 5, 6, 7, 8, 9,
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

    private Verhoeff() {
        // Utility class; prevent instantiation
    }

    public static boolean verhoeffCheck(String digits) {
        validateDigits(digits);
        int[] numbers = toIntArray(digits);
        int checksum = calculateChecksum(numbers);
        return checksum == 0;
    }

    public static String addVerhoeffChecksum(String initialDigits) {
        validateDigits(initialDigits);

        String digitsWithPlaceholder = initialDigits + "0";
        int[] numbers = toIntArray(digitsWithPlaceholder);

        int checksum = calculateChecksum(numbers);
        checksum = MULTIPLICATIVE_INVERSE[checksum];

        return initialDigits + checksum;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validInput = "2363";
        String invalidInput = "2364";
        checkAndPrint(validInput);
        checkAndPrint(invalidInput);

        System.out.println("\nCheck digit generation example:");
        String input = "236";
        generateAndPrint(input);
    }

    private static int calculateChecksum(int[] numbers) {
        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int indexFromRight = numbers.length - 1 - i;
            int digit = numbers[indexFromRight];
            byte permuted = PERMUTATION_TABLE[i % 8][digit];
            checksum = MULTIPLICATION_TABLE[checksum][permuted];
        }
        return checksum;
    }

    private static void checkAndPrint(String input) {
        String validationResult = verhoeffCheck(input) ? "valid" : "not valid";
        System.out.println("Input '" + input + "' is " + validationResult);
    }

    private static void generateAndPrint(String input) {
        String result = addVerhoeffChecksum(input);
        System.out.println(
            "Generate and add checksum to initial value '" + input + "'. Result: '" + result + "'"
        );
    }

    private static void validateDigits(String input) {
        Objects.requireNonNull(input, "Input must not be null");
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + input + "' contains non-digit characters");
        }
    }

    private static int[] toIntArray(String string) {
        return string.chars()
            .map(ch -> Character.digit(ch, 10))
            .toArray();
    }
}