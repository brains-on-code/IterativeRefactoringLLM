package com.thealgorithms.others;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


final class PasswordGen {
    private static final String UPPERCASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(){}?";
    private static final String ALL_CHARACTERS = UPPERCASE_LETTERS + LOWERCASE_LETTERS + DIGITS + SPECIAL_CHARACTERS;

    private PasswordGen() {
    }


    public static String generatePassword(int minLength, int maxLength) {
        if (minLength > maxLength || minLength <= 0 || maxLength <= 0) {
            throw new IllegalArgumentException("Incorrect length parameters: minLength must be <= maxLength and both must be > 0");
        }

        Random random = new Random();

        List<Character> letters = new ArrayList<>();
        for (char c : ALL_CHARACTERS.toCharArray()) {
            letters.add(c);
        }

        Collections.shuffle(letters);
        StringBuilder password = new StringBuilder();

        for (int i = random.nextInt(maxLength - minLength) + minLength; i > 0; --i) {
            password.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        return password.toString();
    }
}
