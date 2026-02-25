package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * The rail fence cipher (also called a zigzag cipher) is a classical type of transposition cipher.
 * It derives its name from the manner in which encryption is performed, in analogy to a fence built with horizontal rails.
 * https://en.wikipedia.org/wiki/Rail_fence_cipher
 * @author https://github.com/Krounosity
 */
public class RailFenceCipher {

    // Encrypts the input string using the rail fence cipher method with the given number of rails.
    public String encrypt(String plainText, int railCount) {

        // Base case of single rail or rails are more than the number of characters in the string
        if (railCount == 1 || railCount >= plainText.length()) {
            return plainText;
        }

        boolean isMovingDown = true;

        char[][] railMatrix = new char[railCount][plainText.length()];

        for (int rail = 0; rail < railCount; rail++) {
            Arrays.fill(railMatrix[rail], '\n');
        }

        int currentRail = 0;
        int currentColumn = 0;
        int plainTextIndex = 0;

        while (currentColumn < plainText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == railCount - 1) {
                isMovingDown = false;
            }

            railMatrix[currentRail][currentColumn] = plainText.charAt(plainTextIndex);
            currentColumn++;

            currentRail += isMovingDown ? 1 : -1;
            plainTextIndex++;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (char[] railRow : railMatrix) {
            for (char character : railRow) {
                if (character != '\n') {
                    encryptedText.append(character);
                }
            }
        }
        return encryptedText.toString();
    }

    // Decrypts the input string using the rail fence cipher method with the given number of rails.
    public String decrypt(String cipherText, int railCount) {

        // Base case of single rail or rails are more than the number of characters in the string
        if (railCount == 1 || railCount >= cipherText.length()) {
            return cipherText;
        }

        boolean isMovingDown = true;

        char[][] railMatrix = new char[railCount][cipherText.length()];

        int currentRail = 0;
        int currentColumn = 0;

        // Mark the pattern on the rail matrix using '*'.
        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == railCount - 1) {
                isMovingDown = false;
            }

            railMatrix[currentRail][currentColumn] = '*';
            currentColumn++;

            currentRail += isMovingDown ? 1 : -1;
        }

        int cipherTextIndex = 0;

        // Fill the rail matrix with characters from the input string based on the marked pattern.
        for (int rail = 0; rail < railCount; rail++) {
            for (int column = 0; column < cipherText.length(); column++) {
                if (railMatrix[rail][column] == '*') {
                    railMatrix[rail][column] = cipherText.charAt(cipherTextIndex++);
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        currentRail = 0;
        currentColumn = 0;

        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == railCount - 1) {
                isMovingDown = false;
            }

            decryptedText.append(railMatrix[currentRail][currentColumn]);
            currentColumn++;

            currentRail += isMovingDown ? 1 : -1;
        }

        return decryptedText.toString();
    }
}