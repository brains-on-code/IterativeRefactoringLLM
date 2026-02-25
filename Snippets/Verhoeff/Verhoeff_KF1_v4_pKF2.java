package com.thealgorithms.others;

import java.util.Objects;

/**
 * Utility class implementing the Verhoeff checksum algorithm.
 *
 * <p>The Verhoeff algorithm is a checksum formula for error detection on
 * identification numbers. It detects all single-digit errors and most
 * transposition errors.
 */
public final class Verhoeff {

    private Verhoeff() {
        // Prevent instantiation
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
     * @return {@code true} if the value is Verhoeff-valid, {@code false} otherwise
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} contains non-digit characters
     */
    public static boolean isValid(String value) {
        validateNumericString(value);
        int[] digits = toDigitArray(value);
        int checksum = computeChecksum(digits);
        return checksum == 0;
    }

    /**
     * Generates and appends a Verhoeff check digit to the given numeric string.
     *
     * @param value numeric string for which to generate a check digit
     * @return the original value with the Verhoeff check digit appended
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} contains non-digit characters
     */
    public static String appendCheckDigit(String value) {
        validateNumericString(value);

        String valueWithZero = value + "0";
        int[] digits = toDigitArray(valueWithZero);

        int checksum = computeChecksum(digits);
        checksum = INV_TABLE[checksum];

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
        printCheckDigitGeneration(baseValue);
    }

    private static void printValidationResult(String value) {
        String result = Verhoeff.isValid(value) ? "valid" : "not valid";
        System.out.println("Input '" + value + "' is " + result);
    }

    private static void printCheckDigitGeneration(String value) {
        String withChecksum = appendCheckDigit(value);
        System.out.println(
            "Generate and add checksum to initial value '" + value + "'. Result: '" + withChecksum + "'"
        );
    }

    /**
     * Ensures the input is a non-null string of digits.
     *
     * @param value input string
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} contains non-digit characters
     */
    private static void validateNumericString(String value) {
        Objects.requireNonNull(value, "Input must not be null");
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + value + "' contains non-digit characters");
        }
    }

    /**
     * Converts a numeric string to an array of its digit values.
     *
     * @param value numeric string
     * @return array of digits
     */
    private static int[] toDigitArray(String value) {
        return value.chars().map(ch -> Character.digit(ch, 10)).toArray();
    }

    /**
     * Computes the Verhoeff checksum for the given array of digits.
     *
     * @param digits array of digits
     * @return checksum value
     */
    private static int computeChecksum(int[] digits) {
        int checksum = 0;
        for (int i = 0; i < digits.length; i++) {
            int reversedIndex = digits.length - i - 1;
            byte permuted = P_TABLE[i % 8][digits[reversedIndex]];
            checksum = D_TABLE[checksum][permuted];
        }
        return checksum;
    }
}