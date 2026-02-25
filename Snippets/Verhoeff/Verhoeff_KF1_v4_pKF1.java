package com.thealgorithms.others;

import java.util.Objects;

/**
 * Verhoeff checksum algorithm implementation.
 */
public final class VerhoeffAlgorithm {

    private VerhoeffAlgorithm() {}

    /** Multiplication table (d-table). */
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

    /** Inverse table (inv-table). */
    private static final byte[] INVERSE_DIGIT_TABLE = {
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

    /**
     * Validates a numeric string using the Verhoeff checksum algorithm.
     *
     * @param numericString numeric string to validate
     * @return true if the value is Verhoeff-valid, false otherwise
     */
    public static boolean isValid(String numericString) {
        validateNumericString(numericString);
        int[] digits = toDigitArray(numericString);

        int checksum = 0;
        for (int position = 0; position < digits.length; position++) {
            int reversedIndex = digits.length - position - 1;
            byte permutedDigit = PERMUTATION_TABLE[position % 8][digits[reversedIndex]];
            checksum = MULTIPLICATION_TABLE[checksum][permutedDigit];
        }

        return checksum == 0;
    }

    /**
     * Generates and appends a Verhoeff check digit to the given numeric string.
     *
     * @param numericString numeric string without check digit
     * @return value with appended Verhoeff check digit
     */
    public static String generateCheckDigit(String numericString) {
        validateNumericString(numericString);

        String numericStringWithPlaceholderCheckDigit = numericString + "0";
        int[] digits = toDigitArray(numericStringWithPlaceholderCheckDigit);

        int checksum = 0;
        for (int position = 0; position < digits.length; position++) {
            int reversedIndex = digits.length - position - 1;
            byte permutedDigit = PERMUTATION_TABLE[position % 8][digits[reversedIndex]];
            checksum = MULTIPLICATION_TABLE[checksum][permutedDigit];
        }
        checksum = INVERSE_DIGIT_TABLE[checksum];

        return numericString + checksum;
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

    private static void printValidationResult(String numericString) {
        String validationResult = VerhoeffAlgorithm.isValid(numericString) ? "valid" : "not valid";
        System.out.println("Input '" + numericString + "' is " + validationResult);
    }

    private static void printGeneratedChecksum(String numericString) {
        String valueWithChecksum = generateCheckDigit(numericString);
        System.out.println(
            "Generate and add checksum to initial value '" + numericString + "'. Result: '" + valueWithChecksum + "'"
        );
    }

    private static void validateNumericString(String numericString) {
        Objects.requireNonNull(numericString, "Input value must not be null");
        if (!numericString.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + numericString + "' contains non-digit characters");
        }
    }

    private static int[] toDigitArray(String numericString) {
        return numericString.chars().map(character -> Character.digit(character, 10)).toArray();
    }
}