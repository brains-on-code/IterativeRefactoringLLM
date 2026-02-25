package com.thealgorithms.others;

import java.util.Objects;

/**
 * Utility class implementing the Verhoeff checksum algorithm.
 *
 * <p>The Verhoeff algorithm is a checksum formula for error detection
 * on identification numbers. It detects all single-digit errors and
 * most transposition errors.
 */
public final class Verhoeff {

    private Verhoeff() {
        // Prevent instantiation
    }

    /** Verhoeff multiplication table (d-table). */
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

    /** Verhoeff inverse table (inv-table). */
    private static final byte[] MULTIPLICATIVE_INVERSE = {
        0, 4, 3, 2, 1, 5, 6, 7, 8, 9,
    };

    /** Verhoeff permutation table (p-table). */
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
     * @param digits numeric string including its Verhoeff check digit
     * @return {@code true} if the string is Verhoeff-valid, {@code false} otherwise
     * @throws NullPointerException     if {@code digits} is {@code null}
     * @throws IllegalArgumentException if {@code digits} contains non-digit characters
     */
    public static boolean verhoeffCheck(String digits) {
        validateNumericInput(digits);
        int[] numbers = toDigitArray(digits);
        int checksum = computeChecksum(numbers);
        return checksum == 0;
    }

    /**
     * Appends a Verhoeff check digit to a numeric string.
     *
     * @param initialDigits numeric string without a Verhoeff check digit
     * @return the input string with its Verhoeff check digit appended
     * @throws NullPointerException     if {@code initialDigits} is {@code null}
     * @throws IllegalArgumentException if {@code initialDigits} contains non-digit characters
     */
    public static String addVerhoeffChecksum(String initialDigits) {
        validateNumericInput(initialDigits);

        String digitsWithPlaceholder = initialDigits + "0";
        int[] numbers = toDigitArray(digitsWithPlaceholder);

        int checksum = computeChecksum(numbers);
        checksum = MULTIPLICATIVE_INVERSE[checksum];

        return initialDigits + checksum;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validInput = "2363";
        String invalidInput = "2364";
        printValidationResult(validInput);
        printValidationResult(invalidInput);

        System.out.println("\nCheck digit generation example:");
        String input = "236";
        printGeneratedChecksum(input);
    }

    private static void printValidationResult(String input) {
        String validationResult = verhoeffCheck(input) ? "valid" : "not valid";
        System.out.println("Input '" + input + "' is " + validationResult);
    }

    private static void printGeneratedChecksum(String input) {
        String result = addVerhoeffChecksum(input);
        System.out.println(
            "Generate and add checksum to initial value '" + input + "'. Result: '" + result + "'"
        );
    }

    /**
     * Ensures the input is non-null and contains only digits.
     *
     * @param input string to validate
     * @throws NullPointerException     if {@code input} is {@code null}
     * @throws IllegalArgumentException if {@code input} contains non-digit characters
     */
    private static void validateNumericInput(String input) {
        Objects.requireNonNull(input, "Input must not be null");
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(
                "Input '" + input + "' must contain only digits"
            );
        }
    }

    /**
     * Converts a numeric string into an array of its digit values.
     *
     * @param string numeric string
     * @return array of digit values
     */
    private static int[] toDigitArray(String string) {
        return string.chars()
            .map(ch -> Character.digit(ch, 10))
            .toArray();
    }

    /**
     * Computes the Verhoeff checksum for an array of digits.
     *
     * @param numbers array of digit values
     * @return checksum value
     */
    private static int computeChecksum(int[] numbers) {
        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int indexFromRight = numbers.length - 1 - i;
            byte permuted = PERMUTATION_TABLE[i % 8][numbers[indexFromRight]];
            checksum = MULTIPLICATION_TABLE[checksum][permuted];
        }
        return checksum;
    }
}