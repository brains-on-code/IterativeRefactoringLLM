package com.thealgorithms.others;

import java.util.Random;

/**
 * Creates a random password from ASCII letters given password length bounds.
 *
 * @author AKS1996
 * @date 2017.10.25
 */
final class PasswordGenerator {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_SYMBOLS = "!@#$%^&*(){}?";
    private static final String ALLOWED_CHARACTERS =
            UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_SYMBOLS;

    private PasswordGenerator() {
    }

    /**
     * Generates a random password with a length between minLength and maxLength.
     *
     * @param minLength The minimum length of the password.
     * @param maxLength The maximum length of the password.
     * @return A randomly generated password.
     * @throws IllegalArgumentException if minLength is greater than maxLength or if either is non-positive.
     */
    public static String generatePassword(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException(
                "Incorrect length parameters: minLength must be <= maxLength and both must be > 0"
            );
        }

        Random randomGenerator = new Random();
        int passwordLength = randomGenerator.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder password = new StringBuilder(passwordLength);

        for (int characterIndex = 0; characterIndex < passwordLength; characterIndex++) {
            int randomCharacterIndex = randomGenerator.nextInt(ALLOWED_CHARACTERS.length());
            password.append(ALLOWED_CHARACTERS.charAt(randomCharacterIndex));
        }

        return password.toString();
    }
}