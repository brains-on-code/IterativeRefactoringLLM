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

        char[][] railMatrix = new char[railCount][plainText.length()];

        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            Arrays.fill(railMatrix[railIndex], '\n');
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

            railMatrix[currentRailIndex][currentColumnIndex] = plainText.charAt(plainTextIndex);
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
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

        boolean movingDown = true;

        char[][] railMatrix = new char[railCount][cipherText.length()];

        int currentRailIndex = 0;
        int currentColumnIndex = 0;

        // Mark the pattern on the rail matrix using '*'.
        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                movingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                movingDown = false;
            }

            railMatrix[currentRailIndex][currentColumnIndex] = '*';
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
        }

        int cipherTextIndex = 0;

        // Fill the rail matrix with characters from the input string based on the marked pattern.
        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railMatrix[railIndex][columnIndex] == '*') {
                    railMatrix[railIndex][columnIndex] = cipherText.charAt(cipherTextIndex++);
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        currentRailIndex = 0;
        currentColumnIndex = 0;

        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                movingDown = true;
            } else if (currentRailIndex == railCount - 1) {
                movingDown = false;
            }

            decryptedText.append(railMatrix[currentRailIndex][currentColumnIndex]);
            currentColumnIndex++;

            currentRailIndex += movingDown ? 1 : -1;
        }

        return decryptedText.toString();
    }
}