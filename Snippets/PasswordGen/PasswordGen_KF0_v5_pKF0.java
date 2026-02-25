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
        // Prevent instantiation
    }

    /**
     * Generates a random password with a length between {@code minLength} and {@code maxLength} (inclusive).
     *
     * @param minLength the minimum length of the password (must be > 0)
     * @param maxLength the maximum length of the password (must be >= minLength)
     * @return a randomly generated password
     * @throws IllegalArgumentException if {@code minLength} is greater than {@code maxLength}
     *                                  or if either is non-positive
     */
    public static String generatePassword(int minLength, int maxLength) {
        validateLengthBounds(minLength, maxLength);

        int passwordLength = getRandomLength(minLength, maxLength);
        StringBuilder passwordBuilder = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            passwordBuilder.append(getRandomCharacter());
        }

        return passwordBuilder.toString();
    }

    private static void validateLengthBounds(int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException("minLength and maxLength must be positive.");
        }
        if (minLength > maxLength) {
            throw new IllegalArgumentException("minLength must be less than or equal to maxLength.");
        }
    }

    private static int getRandomLength(int minLength, int maxLength) {
        int lengthRange = maxLength - minLength + 1;
        return minLength + RANDOM.nextInt(lengthRange);
    }

    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }
}