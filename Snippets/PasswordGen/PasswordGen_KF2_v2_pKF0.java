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

        int passwordLength = getRandomLength(minLength, maxLength);
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            password.append(getRandomCharacter());
        }

        return password.toString();
    }

    private static int getRandomLength(int minLength, int maxLength) {
        int lengthRange = maxLength - minLength + 1;
        return RANDOM.nextInt(lengthRange) + minLength;
    }

    private static char getRandomCharacter() {
        int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
        return ALL_CHARACTERS.charAt(randomIndex);
    }

    private static void validateLengthRange(int minLength, int maxLength) {
        boolean invalidMin = minLength <= 0;
        boolean invalidMax = maxLength <= 0;
        boolean invertedRange = minLength > maxLength;

        if (invalidMin || invalidMax || invertedRange) {
            throw new IllegalArgumentException(
                "Invalid length parameters: minLength and maxLength must be > 0, and minLength must be <= maxLength."
            );
        }
    }
}