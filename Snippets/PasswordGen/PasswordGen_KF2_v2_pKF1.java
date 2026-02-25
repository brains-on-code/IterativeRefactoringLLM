package com.thealgorithms.others;

import java.security.SecureRandom;

final class PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*(){}?";
    private static final String ALLOWED_CHARACTERS =
            UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_SYMBOLS;

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private PasswordGenerator() {
        // Utility class; prevent instantiation
    }

    public static String generatePassword(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException(
                    "Invalid length parameters: minLength must be <= maxLength and both must be > 0");
        }

        int passwordLength = SECURE_RANDOM.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder(passwordLength);

        for (int index = 0; index < passwordLength; index++) {
            int randomCharIndex = SECURE_RANDOM.nextInt(ALLOWED_CHARACTERS.length());
            password.append(ALLOWED_CHARACTERS.charAt(randomCharIndex));
        }

        return password.toString();
    }
}