package com.thealgorithms.others;

import java.util.Objects;

/**
 * The Verhoeff algorithm is a checksum formula for error detection developed by
 * the Dutch mathematician Jacobus Verhoeff and was first published in 1969. It
 * was the first decimal check digit algorithm which detects all single-digit
 * errors, and all transposition errors involving two adjacent digits.
 *
 * <p>
 * The strengths of the algorithm are that it detects all transliteration and
 * transposition errors, and additionally most twin, twin jump, jump
 * transposition and phonetic errors. The main weakness of the Verhoeff
 * algorithm is its complexity. The calculations required cannot easily be
 * expressed as a formula. For easy calculation three tables are required:
 * </p>
 * <ol>
 * <li>multiplication table</li>
 * <li>inverse table</li>
 * <li>permutation table</li>
 * </ol>
 *
 * @see <a href="https://en.wikipedia.org/wiki/Verhoeff_algorithm">Wiki.
 * Verhoeff algorithm</a>
 */
public final class Verhoeff {

    private static final int RADIX = 10;
    private static final int PERMUTATION_PERIOD = 8;

    /**
     * Table {@code d}. Based on multiplication in the dihedral group D5 and is
     * simply the Cayley table of the group. Note that this group is not
     * commutative, that is, for some values of {@code j} and {@code k},
     * {@code d(j,k) ≠ d(k, j)}.
     *
     * @see <a href="https://en.wikipedia.org/wiki/Dihedral_group">Wiki.
     * Dihedral group</a>
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
     * The inverse table {@code inv}. Represents the multiplicative inverse of a
     * digit, that is, the value that satisfies {@code d(j, inv(j)) = 0}.
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
     * The permutation table {@code p}. Applies a permutation to each digit
     * based on its position in the number. This is actually a single
     * permutation {@code (1 5 8 9 4 2 7 0)(3 6)} applied iteratively; i.e.
     * {@code p(i+j,n) = p(i, p(j,n))}.
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

    private Verhoeff() {
        // Utility class
    }

    /**
     * Check input digits by Verhoeff algorithm.
     *
     * @param digits input to check
     * @return true if check was successful, false otherwise
     * @throws IllegalArgumentException if input parameter contains not only
     * digits
     * @throws NullPointerException if input is null
     */
    public static boolean verhoeffCheck(String digits) {
        validateInput(digits);
        int[] numbers = toIntArray(digits);
        return calculateChecksum(numbers) == 0;
    }

    /**
     * Calculate check digit for initial digits and add it to the last
     * position.
     *
     * @param initialDigits initial value
     * @return digits with the checksum in the last position
     * @throws IllegalArgumentException if input parameter contains not only
     * digits
     * @throws NullPointerException if input is null
     */
    public static String addVerhoeffChecksum(String initialDigits) {
        validateInput(initialDigits);

        String modifiedDigits = initialDigits + "0";
        int[] numbers = toIntArray(modifiedDigits);

        int checksum = calculateChecksum(numbers);
        int checkDigit = MULTIPLICATIVE_INVERSE[checksum];

        return initialDigits + checkDigit;
    }

    public static void main(String[] args) {
        System.out.println("Verhoeff algorithm usage examples:");
        String validInput = "2363";
        String invalidInput = "2364";
        printCheckResult(validInput);
        printCheckResult(invalidInput);

        System.out.println("\nCheck digit generation example:");
        String input = "236";
        printGeneratedChecksum(input);
    }

    private static void printCheckResult(String input) {
        String validationResult = verhoeffCheck(input) ? "valid" : "not valid";
        System.out.println("Input '" + input + "' is " + validationResult);
    }

    private static void printGeneratedChecksum(String input) {
        String result = addVerhoeffChecksum(input);
        System.out.println(
            "Generate and add checksum to initial value '" + input + "'. Result: '" + result + "'"
        );
    }

    private static void validateInput(String input) {
        Objects.requireNonNull(input, "Input must not be null");
        if (!input.matches("\\d+")) {
            throw new IllegalArgumentException("Input '" + input + "' contains not only digits");
        }
    }

    private static int[] toIntArray(String string) {
        return string.chars()
            .map(ch -> Character.digit(ch, RADIX))
            .toArray();
    }

    private static int calculateChecksum(int[] numbers) {
        int checksum = 0;
        for (int position = 0; position < numbers.length; position++) {
            int indexFromRight = numbers.length - 1 - position;
            int digit = numbers[indexFromRight];
            byte permutedDigit = PERMUTATION_TABLE[position % PERMUTATION_PERIOD][digit];
            checksum = MULTIPLICATION_TABLE[checksum][permutedDigit];
        }
        return checksum;
    }
}