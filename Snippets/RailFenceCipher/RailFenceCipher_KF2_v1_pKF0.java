package com.thealgorithms.ciphers;

import java.util.Arrays;

public class RailFenceCipher {

    public String encrypt(String text, int rails) {
        if (rails <= 1 || rails >= text.length()) {
            return text;
        }

        char[][] railMatrix = new char[rails][text.length()];
        for (char[] row : railMatrix) {
            Arrays.fill(row, '\n');
        }

        boolean movingDown = false;
        int row = 0;

        for (int col = 0; col < text.length(); col++) {
            if (row == 0 || row == rails - 1) {
                movingDown = !movingDown;
            }

            railMatrix[row][col] = text.charAt(col);
            row += movingDown ? 1 : -1;
        }

        StringBuilder encrypted = new StringBuilder();
        for (char[] railRow : railMatrix) {
            for (char ch : railRow) {
                if (ch != '\n') {
                    encrypted.append(ch);
                }
            }
        }

        return encrypted.toString();
    }

    public String decrypt(String cipher, int rails) {
        if (rails <= 1 || rails >= cipher.length()) {
            return cipher;
        }

        char[][] railMatrix = new char[rails][cipher.length()];

        boolean movingDown = false;
        int row = 0;

        for (int col = 0; col < cipher.length(); col++) {
            if (row == 0 || row == rails - 1) {
                movingDown = !movingDown;
            }

            railMatrix[row][col] = '*';
            row += movingDown ? 1 : -1;
        }

        int index = 0;
        for (int i = 0; i < rails; i++) {
            for (int j = 0; j < cipher.length(); j++) {
                if (railMatrix[i][j] == '*' && index < cipher.length()) {
                    railMatrix[i][j] = cipher.charAt(index++);
                }
            }
        }

        StringBuilder decrypted = new StringBuilder();
        movingDown = false;
        row = 0;

        for (int col = 0; col < cipher.length(); col++) {
            if (row == 0 || row == rails - 1) {
                movingDown = !movingDown;
            }

            decrypted.append(railMatrix[row][col]);
            row += movingDown ? 1 : -1;
        }

        return decrypted.toString();
    }
}