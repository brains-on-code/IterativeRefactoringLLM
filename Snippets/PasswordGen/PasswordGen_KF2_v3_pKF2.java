package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random passwords.
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
     * Generates a random password whose length is between {@code minLength} and {@code maxLength},
     * inclusive.
     *
     * @param minLength the minimum length of the password; must be > 0 and <= {@code maxLength}
     * @param maxLength the maximum length of the password; must be > 0 and >= {@code minLength}
     * @return a randomly generated password
     * @throws IllegalArgumentException if {@code minLength} or {@code maxLength} are non-positive,
     *                                  or if {@code minLength} is greater than {@code maxLength}
     */
    public static String generatePassword(int minLength, int maxLength) {
        validateLengthRange(minLength, maxLength);

        int passwordLength = RANDOM.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }

    /**
     * Validates that the provided minimum and maximum lengths define a valid range.
     *
     * @param minLength the minimum allowed length; must be > 0
     * @param maxLength the maximum allowed length; must be > 0 and >= {@code minLength}
     * @throws IllegalArgumentException if the range is invalid
     */
    private static void validateLengthRange(int minLength, int maxLength) {
        boolean hasNonPositiveBound = minLength <= 0 || maxLength <= 0;
        boolean isInvertedRange = minLength > maxLength;

        if (hasNonPositiveBound || isInvertedRange) {
            throw new IllegalArgumentException(
                    "Invalid length range: minLength must be <= maxLength and both must be > 0");
        }
    }
}