package com.thealgorithms.others;

import java.util.Random;

/**
 * Utility class for generating random strings.
 */
final class RandomStringGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*(){}?";
    private static final String ALLOWED_CHARACTERS =
        UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_SYMBOLS;

    private RandomStringGenerator() {
    }

    /**
     * Generates a random string whose length is between {@code minLength} and {@code maxLength},
     * inclusive of {@code minLength} and exclusive of {@code maxLength + 1}.
     *
     * @param minLength the minimum length of the generated string (must be > 0 and <= maxLength)
     * @param maxLength the maximum length of the generated string (must be > 0 and >= minLength)
     * @return a randomly generated string
     * @throws IllegalArgumentException if the length constraints are invalid
     */
    public static String generateRandomString(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException(
                "Incorrect length parameters: minLength must be <= maxLength and both must be > 0"
            );
        }

        Random random = new Random();
        int targetLength = random.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder result = new StringBuilder(targetLength);
        int allowedCharactersLength = ALLOWED_CHARACTERS.length();

        for (int i = 0; i < targetLength; i++) {
            int randomIndex = random.nextInt(allowedCharactersLength);
            result.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return result.toString();
    }
}