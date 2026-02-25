package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Utility class for generating random strings.
 */
final class RandomStringGenerator {

    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_CHARACTERS =
        UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGIT_CHARACTERS + SPECIAL_CHARACTERS;

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
        validateLengthRange(minLength, maxLength);

        int length = getRandomLength(minLength, maxLength);
        StringBuilder result = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            result.append(getRandomCharacter());
        }

        return result.toString();
    }

    private static void validateLengthRange(int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException("Length parameters must be greater than 0.");
        }
        if (minLength > maxLength) {
            throw new IllegalArgumentException("minLength must be less than or equal to maxLength.");
        }
    }

    private static int getRandomLength(int minLength, int maxLength) {
        int range = maxLength - minLength + 1;
        return RANDOM.nextInt(range) + minLength;
    }

    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }
}