package com.thealgorithms.others;

import java.security.SecureRandom;

/**
 * Creates a random password from ASCII letters given password length bounds.
 *
 * @author AKS1996
 * @date 2017.10.25
 */
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

    /**
     * Generates a random password with a length between minLength and maxLength (inclusive).
     *
     * @param minLength the minimum length of the password (must be > 0)
     * @param maxLength the maximum length of the password (must be >= minLength)
     * @return a randomly generated password
     * @throws IllegalArgumentException if minLength is greater than maxLength or if either is non-positive
     */
    public static String generatePassword(int minLength, int maxLength) {
        validateLengthBounds(minLength, maxLength);

        int passwordLength = getRandomLength(minLength, maxLength);
        StringBuilder password = new StringBuilder(passwordLength);

        for (int i = 0; i < passwordLength; i++) {
            int randomIndex = RANDOM.nextInt(ALL_CHARACTERS.length());
            password.append(ALL_CHARACTERS.charAt(randomIndex));
        }

        return password.toString();
    }

    private static void validateLengthBounds(int minLength, int maxLength) {
        if (minLength <= 0 || maxLength <= 0 || minLength > maxLength) {
            throw new IllegalArgumentException(
                "Incorrect length parameters: minLength must be <= maxLength and both must be > 0"
            );
        }
    }

    private static int getRandomLength(int minLength, int maxLength) {
        int bound = maxLength - minLength + 1;
        return minLength + RANDOM.nextInt(bound);
    }
}