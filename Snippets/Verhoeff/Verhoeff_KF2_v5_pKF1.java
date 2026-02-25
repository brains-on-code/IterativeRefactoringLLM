package com.thealgorithms.others;

import java.util.Objects;

public final class Verhoeff {

    private Verhoeff() {
    }

    private static final byte[][] DIHEDRAL_TABLE = {
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

    private static final byte[] INVERSE_TABLE = {
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

    public static boolean isValidVerhoeff(String numericValue) {
        validateNumericInput(numericValue);
        int[] digits = toDigitArray(numericValue);
        int checksum = calculateChecksum(digits);
        return checksum == 0;
    }

    public static String appendVerhoeffChecksum(String numericValue) {
        validateNumericInput(numericValue);

        String valueWithPlaceholderChecksum = numericValue + "0";
        int[] digits = toDigitArray(valueWithPlaceholderChecksum);

        int checksum = calculateChecksum(digits);
        checksum = INVERSE_TABLE[checksum];

        return numericValue + checksum;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validInput = "2363";
        String invalidInput = "2364";
        printValidationResult(validInput);
        printValidationResult(invalidInput);

        System.out.println("\nCheck digit generation example:");
        String inputWithoutChecksum = "236";
        printGeneratedChecksum(inputWithoutChecksum);
    }

    private static void printValidationResult(String input) {
        String validationResult = isValidVerhoeff(input) ? "valid" : "not valid";
        System.out.println("Input '" + input + "' is " + validationResult);
    }

    private static void printGeneratedChecksum(String inputWithoutChecksum) {
        String resultWithChecksum = appendVerhoeffChecksum(inputWithoutChecksum);
        System.out.println(
            "Generate and add checksum to initial value '" + inputWithoutChecksum + "'. Result: '" + resultWithChecksum + "'"
        );
    }

    private static void validateNumericInput(String input) {
        Objects.requireNonNull(input, "Input must not be null");
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + input + "' contains non-digit characters");
        }
    }

    private static int[] toDigitArray(String numericValue) {
        return numericValue.chars().map(ch -> Character.digit(ch, 10)).toArray();
    }

    private static int calculateChecksum(int[] digits) {
        int checksum = 0;
        for (int position = 0; position < digits.length; position++) {
            int reversedIndex = digits.length - position - 1;
            byte permutedDigit = PERMUTATION_TABLE[position % 8][digits[reversedIndex]];
            checksum = DIHEDRAL_TABLE[checksum][permutedDigit];
        }
        return checksum;
    }
}