package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Generates random passwords using ASCII letters, digits, and special characters.
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
        // Prevent instantiation
    }

    /**
     * Generates a random password whose length is between {@code minLength} and {@code maxLength}, inclusive.
     *
     * @param minLength minimum password length (must be > 0)
     * @param maxLength maximum password length (must be >= minLength)
     * @return generated password
     * @throws IllegalArgumentException if {@code minLength} > {@code maxLength} or either is non-positive
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
     * Ensures that length bounds are positive and {@code minLength <= maxLength}.
     *
     * @param minLength minimum allowed length
     * @param maxLength maximum allowed length
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
     * @param minLength minimum allowed length
     * @param maxLength maximum allowed length
     * @return random length within the specified bounds
     */
    private static int getRandomLength(int minLength, int maxLength) {
        int lengthRange = maxLength - minLength + 1;
        return minLength + RANDOM.nextInt(lengthRange);
    }

    /**
     * Returns a random character from the allowed character set.
     *
     * @return random character
     */
    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }
}