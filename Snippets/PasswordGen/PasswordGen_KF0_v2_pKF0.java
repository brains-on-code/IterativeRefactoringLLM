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
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            password.append(getRandomCharacter());
        }

        return password.toString();
    }

    private static void validateLengthBounds(int minLength, int maxLength) {
        boolean invalidMin = minLength <= 0;
        boolean invalidMax = maxLength <= 0;
        boolean invertedRange = minLength > maxLength;

        if (invalidMin || invalidMax || invertedRange) {
            throw new IllegalArgumentException(
                    "Invalid length parameters: minLength must be > 0, " +
                    "maxLength must be > 0, and minLength must be <= maxLength."
            );
        }
    }

    private static int getRandomLength(int minLength, int maxLength) {
        int range = maxLength - minLength + 1;
        return minLength + RANDOM.nextInt(range);
    }

    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }
}