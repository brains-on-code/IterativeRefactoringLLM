package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random passwords.
 */
final class Class1 {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_ALLOWED_CHARACTERS =
        UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom RANDOM = new SecureRandom();

    private Class1() {
        // Utility class; prevent instantiation
    }

    /**
     * Generates a random string (e.g., password) with a length between the given
     * minimum and maximum values (inclusive).
     *
     * @param minLength the minimum length of the generated string (must be > 0)
     * @param maxLength the maximum length of the generated string (must be >= minLength)
     * @return a randomly generated string
     * @throws IllegalArgumentException if the length parameters are invalid
     */
    public static String method1(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException(
                "Incorrect length parameters: minLength must be <= maxLength and both must be > 0"
            );
        }

        int length = RANDOM.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALL_ALLOWED_CHARACTERS.length());
            password.append(ALL_ALLOWED_CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}