package com.thealgorithms.others;

import java.security.SecureRandom;

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

    private static void validateLengthRange(int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0 || minLength > maxLength) {
            throw new IllegalArgumentException(
                    "Invalid length range: minLength must be <= maxLength and both must be > 0");
        }
    }
}