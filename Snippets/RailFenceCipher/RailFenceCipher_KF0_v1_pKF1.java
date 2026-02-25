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

        // Boolean flag to determine if the movement is downward or upward in the rail matrix.
        boolean movingDown = true;

        // Create a 2D array to represent the rails (rows) and the length of the string (columns).
        char[][] railMatrix = new char[railCount][plainText.length()];

        // Initialize all positions in the rail matrix with a placeholder character ('\n').
        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            Arrays.fill(railMatrix[railIndex], '\n');
        }

        int currentRow = 0; // Start at the first row
        int currentColumn = 0; // Start at the first column
        int textIndex = 0;

        // Fill the rail matrix with characters from the string based on the rail pattern.
        while (currentColumn < plainText.length()) {
            // Change direction to down when at the first row.
            if (currentRow == 0) {
                movingDown = true;
            }
            // Change direction to up when at the last row.
            else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            // Place the character in the current position of the rail matrix.
            railMatrix[currentRow][currentColumn] = plainText.charAt(textIndex);
            currentColumn++; // Move to the next column.

            // Move to the next row based on the direction.
            currentRow += movingDown ? 1 : -1;

            textIndex++;
        }

        // Construct the encrypted string by reading characters row by row.
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

        // Boolean flag to determine if the movement is downward or upward in the rail matrix.
        boolean movingDown = true;

        // Create a 2D array to represent the rails (rows) and the length of the string (columns).
        char[][] railMatrix = new char[railCount][cipherText.length()];

        int currentRow = 0; // Start at the first row
        int currentColumn = 0; // Start at the first column

        // Mark the pattern on the rail matrix using '*'.
        while (currentColumn < cipherText.length()) {
            // Change direction to down when at the first row.
            if (currentRow == 0) {
                movingDown = true;
            }
            // Change direction to up when at the last row.
            else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            // Mark the current position in the rail matrix.
            railMatrix[currentRow][currentColumn] = '*';
            currentColumn++; // Move to the next column.

            // Move to the next row based on the direction.
            currentRow += movingDown ? 1 : -1;
        }

        int cipherIndex = 0; // Index to track characters from the input string.

        // Fill the rail matrix with characters from the input string based on the marked pattern.
        for (int railIndex = 0; railIndex < railCount; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railMatrix[railIndex][columnIndex] == '*') {
                    railMatrix[railIndex][columnIndex] = cipherText.charAt(cipherIndex++);
                }
            }
        }

        // Construct the decrypted string by following the zigzag pattern.
        StringBuilder decryptedText = new StringBuilder();
        currentRow = 0; // Reset to the first row
        currentColumn = 0; // Reset to the first column

        while (currentColumn < cipherText.length()) {
            // Change direction to down when at the first row.
            if (currentRow == 0) {
                movingDown = true;
            }
            // Change direction to up when at the last row.
            else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            // Append the character from the rail matrix to the decrypted string.
            decryptedText.append(railMatrix[currentRow][currentColumn]);
            currentColumn++; // Move to the next column.

            // Move to the next row based on the direction.
            currentRow += movingDown ? 1 : -1;
        }

        return decryptedText.toString();
    }
}