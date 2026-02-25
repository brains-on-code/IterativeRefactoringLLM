package com.thealgorithms.others;

import java.util.Objects;

/**
 * Implementation of the Verhoeff checksum algorithm.
 *
 * <p>The Verhoeff algorithm is a decimal check digit algorithm that detects:
 * <ul>
 *     <li>All single-digit errors</li>
 *     <li>All adjacent transposition errors</li>
 *     <li>Most twin, twin jump, jump transposition, and phonetic errors</li>
 * </ul>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Verhoeff_algorithm">Verhoeff algorithm (Wikipedia)</a>
 */
public final class Verhoeff {

    private Verhoeff() {
        // Prevent instantiation
    }

    /**
     * Multiplication table for the dihedral group D5.
     *
     * <p>Note: This operation is not commutative.
     */
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

    /**
     * Multiplicative inverses for digits 0–9.
     *
     * <p>{@code MULTIPLICATIVE_INVERSE[j]} is the value {@code k} such that
     * {@code MULTIPLICATION_TABLE[j][k] == 0}.
     */
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

    /**
     * Position-dependent permutation table.
     */
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
     * Validates a string of digits using the Verhoeff algorithm.
     *
     * @param digits the input string to validate
     * @return {@code true} if the checksum is valid, {@code false} otherwise
     * @throws IllegalArgumentException if the input contains non-digit characters
     * @throws NullPointerException if the input is {@code null}
     */
    public static boolean verhoeffCheck(String digits) {
        checkInput(digits);
        int[] numbers = toIntArray(digits);

        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int indexFromRight = numbers.length - 1 - i;
            byte permuted = PERMUTATION_TABLE[i % 8][numbers[indexFromRight]];
            checksum = MULTIPLICATION_TABLE[checksum][permuted];
        }

        return checksum == 0;
    }

    /**
     * Appends a Verhoeff check digit to the given string of digits.
     *
     * @param initialDigits the input digits without a check digit
     * @return the input digits with the computed check digit appended
     * @throws IllegalArgumentException if the input contains non-digit characters
     * @throws NullPointerException if the input is {@code null}
     */
    public static String addVerhoeffChecksum(String initialDigits) {
        checkInput(initialDigits);

        String modifiedDigits = initialDigits + "0";
        int[] numbers = toIntArray(modifiedDigits);

        int checksum = 0;
        for (int i = 0; i < numbers.length; i++) {
            int indexFromRight = numbers.length - 1 - i;
            byte permuted = PERMUTATION_TABLE[i % 8][numbers[indexFromRight]];
            checksum = MULTIPLICATION_TABLE[checksum][permuted];
        }

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

    /**
     * Validates that the input is non-null and contains only digits.
     *
     * @param input the string to validate
     * @throws IllegalArgumentException if the input contains non-digit characters
     * @throws NullPointerException if the input is {@code null}
     */
    private static void checkInput(String input) {
        Objects.requireNonNull(input, "Input must not be null");
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException(
                "Input '" + input + "' must contain digits only"
            );
        }
    }

    /**
     * Converts a string of decimal digits to an array of integers.
     *
     * @param string the string to convert
     * @return an array of digit values
     */
    private static int[] toIntArray(String string) {
        return string.chars()
            .map(ch -> Character.digit(ch, 10))
            .toArray();
    }
}