package com.thealgorithms.others;

import java.util.Objects;

/**
 * Implementation of the Verhoeff check digit algorithm.
 *
 * <p>The Verhoeff algorithm is a checksum formula for error detection
 * primarily used to validate identification numbers. It detects all
 * single-digit errors and most transposition errors.
 */
public final class Verhoeff {

    /** Multiplication table (d-table) for the Verhoeff algorithm. */
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

    /** Inverse table (inv-table) for the Verhoeff algorithm. */
    private static final byte[] INV_TABLE = {
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

    /** Permutation table (p-table) for the Verhoeff algorithm. */
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

    private Verhoeff() {
        // Utility class; prevent instantiation.
    }

    /**
     * Validates a numeric string using the Verhoeff algorithm.
     *
     * @param value numeric string including its Verhoeff check digit
     * @return {@code true} if the value is Verhoeff-valid, {@code false} otherwise
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} contains non-digit characters
     */
    public static boolean isValid(String value) {
        validateNumeric(value);
        int[] digits = toIntArray(value);
        int checksum = computeChecksum(digits);
        return checksum == 0;
    }

    /**
     * Generates and appends a Verhoeff check digit to the given numeric string.
     *
     * @param value numeric string without a check digit
     * @return the input value with its Verhoeff check digit appended
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} contains non-digit characters
     */
    public static String addCheckDigit(String value) {
        validateNumeric(value);

        String valueWithZero = value + "0";
        int[] digits = toIntArray(valueWithZero);

        int checksum = computeChecksum(digits);
        int checkDigit = INV_TABLE[checksum];

        return value + checkDigit;
    }

    /**
     * Example usage of the Verhoeff algorithm.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validExample = "2363";
        String invalidExample = "2364";
        printValidationResult(validExample);
        printValidationResult(invalidExample);

        System.out.println("\nCheck digit generation example:");
        String baseValue = "236";
        printCheckDigitGeneration(baseValue);
    }

    private static void printValidationResult(String value) {
        String result = isValid(value) ? "valid" : "not valid";
        System.out.println("Input '" + value + "' is " + result);
    }

    private static void printCheckDigitGeneration(String value) {
        String withCheckDigit = addCheckDigit(value);
        System.out.println(
            "Generate and add checksum to initial value '" + value + "'. Result: '" + withCheckDigit + "'"
        );
    }

    private static void validateNumeric(String value) {
        Objects.requireNonNull(value, "Input must not be null");
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + value + "' contains non-digit characters");
        }
    }

    private static int[] toIntArray(String value) {
        return value.chars()
            .map(ch -> Character.digit(ch, 10))
            .toArray();
    }

    private static int computeChecksum(int[] digits) {
        int checksum = 0;
        for (int i = 0; i < digits.length; i++) {
            int reversedIndex = digits.length - 1 - i;
            int digit = digits[reversedIndex];
            byte permuted = P_TABLE[i % 8][digit];
            checksum = D_TABLE[checksum][permuted];
        }
        return checksum;
    }
}