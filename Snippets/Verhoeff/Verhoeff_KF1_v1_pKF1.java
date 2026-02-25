package com.thealgorithms.others;

import java.util.Objects;

/**
 * Verhoeff checksum algorithm implementation.
 */
public final class VerhoeffAlgorithm {

    private VerhoeffAlgorithm() {
    }

    /** Multiplication table (d-table). */
    private static final byte[][] D_TABLE = {
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

    /** Inverse table (inv-table). */
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

    /** Permutation table (p-table). */
    private static final byte[][] P_TABLE = {
        {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
        {1, 5, 7, 6, 2, 8, 3, 0, 9, 4},
        {5, 8, 0, 3, 7, 9, 6, 1, 4, 2},
        {8, 9, 1, 6, 0, 4, 3, 5, 2, 7},
        {9, 4, 5, 3, 1, 2, 6, 8, 7, 0},
        {4, 2, 8, 6, 5, 7, 3, 9, 0, 1},
        {2, 7, 9, 3, 8, 0, 6, 4, 1, 5},
        {7, 0, 4, 6, 9, 1, 3, 2, 5, 8},
    };

    /**
     * Validates a numeric string using the Verhoeff checksum algorithm.
     *
     * @param value numeric string to validate
     * @return true if the value is Verhoeff-valid, false otherwise
     */
    public static boolean isValid(String value) {
        validateNumericString(value);
        int[] digits = toDigitArray(value);

        int checksum = 0;
        for (int position = 0; position < digits.length; position++) {
            int reversedIndex = digits.length - position - 1;
            byte permuted = P_TABLE[position % 8][digits[reversedIndex]];
            checksum = D_TABLE[checksum][permuted];
        }

        return checksum == 0;
    }

    /**
     * Generates and appends a Verhoeff check digit to the given numeric string.
     *
     * @param value numeric string without check digit
     * @return value with appended Verhoeff check digit
     */
    public static String generateCheckDigit(String value) {
        validateNumericString(value);

        String valueWithZero = value + "0";
        int[] digits = toDigitArray(valueWithZero);

        int checksum = 0;
        for (int position = 0; position < digits.length; position++) {
            int reversedIndex = digits.length - position - 1;
            byte permuted = P_TABLE[position % 8][digits[reversedIndex]];
            checksum = D_TABLE[checksum][permuted];
        }
        checksum = INVERSE_TABLE[checksum];

        return value + checksum;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validExample = "2363";
        String invalidExample = "2364";
        printValidationResult(validExample);
        printValidationResult(invalidExample);

        System.out.println("\nCheck digit generation example:");
        String baseValue = "236";
        printGeneratedChecksum(baseValue);
    }

    private static void printValidationResult(String value) {
        String result = VerhoeffAlgorithm.isValid(value) ? "valid" : "not valid";
        System.out.println("Input '" + value + "' is " + result);
    }

    private static void printGeneratedChecksum(String value) {
        String withChecksum = generateCheckDigit(value);
        System.out.println("Generate and add checksum to initial value '" + value + "'. Result: '" + withChecksum + "'");
    }

    private static void validateNumericString(String value) {
        Objects.requireNonNull(value);
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + value + "' contains not only digits");
        }
    }

    private static int[] toDigitArray(String value) {
        return value.chars().map(ch -> Character.digit(ch, 10)).toArray();
    }
}