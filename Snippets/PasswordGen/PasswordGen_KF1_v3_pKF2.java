package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random passwords.
 */
final class PasswordGenerator {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_ALLOWED_CHARACTERS =
        UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGenerator() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates a random password whose length is between {@code minLength}
     * and {@code maxLength}, inclusive.
     *
     * @param minLength the minimum password length; must be greater than 0
     * @param maxLength the maximum password length; must be greater than 0 and
     *                  greater than or equal to {@code minLength}
     * @return a randomly generated password
     * @throws IllegalArgumentException if the length parameters are invalid
     */
    public static String generate(int minLength, int maxLength) {
        validateLengthRange(minLength, maxLength);

        int length = RANDOM.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALL_ALLOWED_CHARACTERS.length());
            password.append(ALL_ALLOWED_CHARACTERS.charAt(index));
        }

        return password.toString();
    }

    /**
     * Validates that the provided minimum and maximum lengths define a valid range.
     *
     * @param minLength the minimum length to validate
     * @param maxLength the maximum length to validate
     * @throws IllegalArgumentException if the range is invalid
     */
    private static void validateLengthRange(int minLength, int maxLength) {
        boolean nonPositiveLengths = minLength <= 0 || maxLength <= 0;
        boolean invalidOrder = minLength > maxLength;

        if (nonPositiveLengths || invalidOrder) {
            throw new IllegalArgumentException(
                "Invalid length range: minLength must be > 0, maxLength must be > 0, and minLength must be <= maxLength."
            );
        }
    }
}