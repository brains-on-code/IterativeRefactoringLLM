package com.thealgorithms.others;

import java.security.SecureRandom;

final class PasswordGenerator {
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_ALLOWED_CHARACTERS =
            UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + DIGIT_CHARACTERS + SPECIAL_CHARACTERS;

    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordGenerator() {
        // Utility class; prevent instantiation
    }

    public static String generatePassword(int minimumLength, int maximumLength) {
        if (minimumLength > maximumLength || minimumLength <= 0 || maximumLength <= 0) {
            throw new IllegalArgumentException(
                    "Invalid length parameters: minimumLength must be <= maximumLength and both must be > 0");
        }

        int passwordLength = RANDOM.nextInt(maximumLength - minimumLength + 1) + minimumLength;
        StringBuilder passwordBuilder = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = RANDOM.nextInt(ALL_ALLOWED_CHARACTERS.length());
            passwordBuilder.append(ALL_ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }
}