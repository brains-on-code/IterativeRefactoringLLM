package com.thealgorithms.others;

import java.util.Random;

/**
 * Creates a random password from ASCII letters given password length bounds.
 *
 * @author AKS1996
 * @date 2017.10.25
 */
final class PasswordGenerator {
    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALLOWED_CHARACTERS =
            UPPERCASE_CHARACTERS + LOWERCASE_CHARACTERS + DIGIT_CHARACTERS + SPECIAL_CHARACTERS;

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

        Random random = new Random();
        int passwordLength = random.nextInt(maxLength - minLength + 1) + minLength;
        StringBuilder passwordBuilder = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            passwordBuilder.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        return passwordBuilder.toString();
    }
}