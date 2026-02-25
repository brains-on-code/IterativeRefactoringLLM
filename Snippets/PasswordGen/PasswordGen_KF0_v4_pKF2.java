package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random passwords from ASCII letters, digits, and special characters.
 */
final class PasswordGen {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_CHARACTERS =
            UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGen() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates a random password whose length is between {@code minLength} and {@code maxLength}, inclusive.
     *
     * @param minLength the minimum allowed length of the password (must be > 0)
     * @param maxLength the maximum allowed length of the password (must be >= minLength)
     * @return a randomly generated password
     * @throws IllegalArgumentException if {@code minLength} is greater than {@code maxLength}
     *                                  or if either bound is non-positive
     */
    public static String generatePassword(int minLength, int maxLength) {
        validateLengthBounds(minLength, maxLength);

        int passwordLength = getRandomLength(minLength, maxLength);
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            password.append(getRandomCharacter());
        }

        return password.toString();
    }

    /**
     * Validates that the provided length bounds are positive and consistent.
     *
     * @param minLength the minimum allowed length
     * @param maxLength the maximum allowed length
     * @throws IllegalArgumentException if bounds are non-positive or inconsistent
     */
    private static void validateLengthBounds(int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0 || minLength > maxLength) {
            throw new IllegalArgumentException(
                    "Invalid length parameters: minLength must be <= maxLength and both must be > 0");
        }
    }

    /**
     * Returns a random length between {@code minLength} and {@code maxLength}, inclusive.
     *
     * @param minLength the minimum allowed length
     * @param maxLength the maximum allowed length
     * @return a random length within the specified bounds
     */
    private static int getRandomLength(int minLength, int maxLength) {
        int lengthRange = maxLength - minLength + 1;
        return minLength + RANDOM.nextInt(lengthRange);
    }

    /**
     * Returns a random character from the allowed character set.
     *
     * @return a random character
     */
    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }
}