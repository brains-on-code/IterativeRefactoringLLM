package com.thealgorithms.others;

import java.util.Random;

/**
 * Utility class for generating random strings.
 */
final class RandomStringGenerator {
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMERIC_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String CHARACTER_POOL =
        UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + NUMERIC_CHARACTERS + SPECIAL_CHARACTERS;

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

        Random randomGenerator = new Random();
        int generatedLength = randomGenerator.nextInt(maxLength - minLength + 1) + minLength;

        StringBuilder generatedStringBuilder = new StringBuilder(generatedLength);
        int characterPoolSize = CHARACTER_POOL.length();

        for (int index = 0; index < generatedLength; index++) {
            int randomCharacterIndex = randomGenerator.nextInt(characterPoolSize);
            generatedStringBuilder.append(CHARACTER_POOL.charAt(randomCharacterIndex));
        }

        return generatedStringBuilder.toString();
    }
}