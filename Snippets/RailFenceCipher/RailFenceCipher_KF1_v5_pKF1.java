package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * Rail Fence Cipher implementation.
 */
public class RailFenceCipher {

    /**
     * Encodes the given text using the Rail Fence Cipher with the specified number of rails.
     *
     * @param plainText the text to encode
     * @param railCount the number of rails to use
     * @return the encoded cipher text
     */
    public String encode(String plainText, int railCount) {
        if (railCount == 1 || railCount >= plainText.length()) {
            return plainText;
        }

        boolean isMovingDown = true;
        char[][] railFence = new char[railCount][plainText.length()];

        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            Arrays.fill(railFence[railIndex], '\n');
        }

        int currentRailIndex = 0;
        int currentColumnIndex = 0;
        int plainTextIndex = 0;

        while (currentColumnIndex < plainText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                isMovingDown = false;
            }

            railFence[currentRailIndex][currentColumnIndex] = plainText.charAt(plainTextIndex);
            currentColumnIndex++;

            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
            plainTextIndex++;
        }

        StringBuilder encodedTextBuilder = new StringBuilder();
        for (char[] railRow : railFence) {
            for (char character : railRow) {
                if (character != '\n') {
                    encodedTextBuilder.append(character);
                }
            }
        }
        return encodedTextBuilder.toString();
    }

    /**
     * Decodes the given Rail Fence Cipher text using the specified number of rails.
     *
     * @param cipherText the text to decode
     * @param railCount  the number of rails used during encoding
     * @return the decoded plain text
     */
    public String decode(String cipherText, int railCount) {
        if (railCount == 1 || railCount >= cipherText.length()) {
            return cipherText;
        }

        boolean isMovingDown = true;
        char[][] railFence = new char[railCount][cipherText.length()];

        int currentRailIndex = 0;
        int currentColumnIndex = 0;

        // Mark the positions that will be filled with characters
        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                isMovingDown = false;
            }

            railFence[currentRailIndex][currentColumnIndex] = '*';
            currentColumnIndex++;
            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
        }

        // Fill the marked positions with the cipher text characters
        int cipherTextIndex = 0;
        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railFence[railIndex][columnIndex] == '*') {
                    railFence[railIndex][columnIndex] = cipherText.charAt(cipherTextIndex++);
                }
            }
        }

        // Read the matrix in zigzag order to reconstruct the plain text
        StringBuilder decodedTextBuilder = new StringBuilder();
        currentRailIndex = 0;
        currentColumnIndex = 0;
        isMovingDown = true;

        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                isMovingDown = false;
            }

            decodedTextBuilder.append(railFence[currentRailIndex][currentColumnIndex]);
            currentColumnIndex++;
            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
        }

        return decodedTextBuilder.toString();
    }
}