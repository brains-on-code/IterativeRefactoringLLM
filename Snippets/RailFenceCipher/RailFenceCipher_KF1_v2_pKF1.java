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
     * @param numRails  the number of rails to use
     * @return the encoded cipher text
     */
    public String encode(String plainText, int numRails) {
        if (numRails == 1 || numRails >= plainText.length()) {
            return plainText;
        }

        boolean movingDown = true;
        char[][] railMatrix = new char[numRails][plainText.length()];

        for (int rail = 0; rail < numRails; rail++) {
            Arrays.fill(railMatrix[rail], '\n');
        }

        int currentRail = 0;
        int currentColumn = 0;
        int textIndex = 0;

        while (currentColumn < plainText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == numRails - 1) {
                movingDown = false;
            }

            railMatrix[currentRail][currentColumn] = plainText.charAt(textIndex);
            currentColumn++;

            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
            textIndex++;
        }

        StringBuilder cipherText = new StringBuilder();
        for (char[] rail : railMatrix) {
            for (char ch : rail) {
                if (ch != '\n') {
                    cipherText.append(ch);
                }
            }
        }
        return cipherText.toString();
    }

    /**
     * Decodes the given Rail Fence Cipher text using the specified number of rails.
     *
     * @param cipherText the text to decode
     * @param numRails   the number of rails used during encoding
     * @return the decoded plain text
     */
    public String decode(String cipherText, int numRails) {
        if (numRails == 1 || numRails >= cipherText.length()) {
            return cipherText;
        }

        boolean movingDown = true;
        char[][] railMatrix = new char[numRails][cipherText.length()];

        int currentRail = 0;
        int currentColumn = 0;

        // Mark the positions that will be filled with characters
        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == numRails - 1) {
                movingDown = false;
            }

            railMatrix[currentRail][currentColumn] = '*';
            currentColumn++;
            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
        }

        // Fill the marked positions with the cipher text characters
        int cipherIndex = 0;
        for (int rail = 0; rail < numRails; rail++) {
            for (int col = 0; col < cipherText.length(); col++) {
                if (railMatrix[rail][col] == '*') {
                    railMatrix[rail][col] = cipherText.charAt(cipherIndex++);
                }
            }
        }

        // Read the matrix in zigzag order to reconstruct the plain text
        StringBuilder plainText = new StringBuilder();
        currentRail = 0;
        currentColumn = 0;
        movingDown = true;

        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                movingDown = true;
            } else if (currentRail == numRails - 1) {
                movingDown = false;
            }

            plainText.append(railMatrix[currentRail][currentColumn]);
            currentColumn++;
            currentRail = movingDown ? currentRail + 1 : currentRail - 1;
        }

        return plainText.toString();
    }
}