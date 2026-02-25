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

        boolean movingDown = true;

        char[][] railGrid = new char[railCount][plainText.length()];

        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            Arrays.fill(railGrid[railIndex], '\n');
        }

        int currentRailIndex = 0;
        int currentColumnIndex = 0;
        int plainTextIndex = 0;

        while (currentColumnIndex < plainText.length()) {
            if (currentRailIndex == 0) {
                movingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                movingDown = false;
            }

            railGrid[currentRailIndex][currentColumnIndex] = plainText.charAt(plainTextIndex);
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
            plainTextIndex++;
        }

        StringBuilder encryptedTextBuilder = new StringBuilder();
        for (char[] railRow : railGrid) {
            for (char character : railRow) {
                if (character != '\n') {
                    encryptedTextBuilder.append(character);
                }
            }
        }
        return encryptedTextBuilder.toString();
    }

    // Decrypts the input string using the rail fence cipher method with the given number of rails.
    public String decrypt(String cipherText, int railCount) {

        // Base case of single rail or rails are more than the number of characters in the string
        if (railCount == 1 || railCount >= cipherText.length()) {
            return cipherText;
        }

        boolean movingDown = true;

        char[][] railGrid = new char[railCount][cipherText.length()];

        int currentRailIndex = 0;
        int currentColumnIndex = 0;

        // Mark the pattern on the rail grid using '*'.
        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                movingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                movingDown = false;
            }

            railGrid[currentRailIndex][currentColumnIndex] = '*';
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
        }

        int cipherTextIndex = 0;

        // Fill the rail grid with characters from the input string based on the marked pattern.
        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railGrid[railIndex][columnIndex] == '*') {
                    railGrid[railIndex][columnIndex] = cipherText.charAt(cipherTextIndex++);
                }
            }
        }

        StringBuilder decryptedTextBuilder = new StringBuilder();
        currentRailIndex = 0;
        currentColumnIndex = 0;

        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                movingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                movingDown = false;
            }

            decryptedTextBuilder.append(railGrid[currentRailIndex][currentColumnIndex]);
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
        }

        return decryptedTextBuilder.toString();
    }
}