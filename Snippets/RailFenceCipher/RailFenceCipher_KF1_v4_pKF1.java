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

        boolean movingDown = true;
        char[][] railMatrix = new char[railCount][plainText.length()];

        for (int rail = 0; rail < railCount; rail++) {
            Arrays.fill(railMatrix[rail], '\n');
        }

        int currentRail = 0;
        int currentColumn = 0;
        int textIndex = 0;

        while (currentColumn < plainText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == railCount - 1) {
                movingDown = false;
            }

            railMatrix[currentRail][currentColumn] = plainText.charAt(textIndex);
            currentColumn++;

            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
            textIndex++;
        }

        StringBuilder encodedText = new StringBuilder();
        for (char[] railRow : railMatrix) {
            for (char character : railRow) {
                if (character != '\n') {
                    encodedText.append(character);
                }
            }
        }
        return encodedText.toString();
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

        boolean movingDown = true;
        char[][] railMatrix = new char[railCount][cipherText.length()];

        int currentRail = 0;
        int currentColumn = 0;

        // Mark the positions that will be filled with characters
        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == railCount - 1) {
                movingDown = false;
            }

            railMatrix[currentRail][currentColumn] = '*';
            currentColumn++;
            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
        }

        // Fill the marked positions with the cipher text characters
        int cipherIndex = 0;
        for (int rail = 0; rail < railCount; rail++) {
            for (int column = 0; column < cipherText.length(); column++) {
                if (railMatrix[rail][column] == '*') {
                    railMatrix[rail][column] = cipherText.charAt(cipherIndex++);
                }
            }
        }

        // Read the matrix in zigzag order to reconstruct the plain text
        StringBuilder decodedText = new StringBuilder();
        currentRail = 0;
        currentColumn = 0;
        movingDown = true;

        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == railCount - 1) {
                movingDown = false;
            }

            decodedText.append(railMatrix[currentRail][currentColumn]);
            currentColumn++;
            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
        }

        return decodedText.toString();
    }
}