package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random strings.
 */
final class RandomStringGenerator {

    private static final String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_CHARACTERS = UPPERCASE + LOWERCASE + DIGITS + SPECIAL_CHARACTERS;

    private static final SecureRandom RANDOM = new SecureRandom();

    private RandomStringGenerator() {
        // Prevent instantiation
    }

    /**
     * Generates a random string with a length between {@code minLength} and {@code maxLength}, inclusive.
     *
     * @param minLength the minimum length of the generated string (must be > 0 and <= maxLength)
     * @param maxLength the maximum length of the generated string (must be >= minLength)
     * @return a randomly generated string
     * @throws IllegalArgumentException if the length parameters are invalid
     */
    public static String generate(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException(
                "Incorrect length parameters: minLength must be <= maxLength and both must be > 0"
            );
        }

        int length = RANDOM.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
            result.append(ALL_CHARACTERS.charAt(randomIndex));
        }

        return result.toString();
    }
}