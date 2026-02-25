package com.thealgorithms.ciphers;

import java.util.Arrays;

public class RailFenceCipher {

    public String encrypt(String plainText, int railCount) {

        if (railCount == 1 || railCount >= plainText.length()) {
            return plainText;
        }

        boolean movingDown = true;
        char[][] railMatrix = new char[railCount][plainText.length()];

        for (int rail = 0; rail < railCount; rail++) {
            Arrays.fill(railMatrix[rail], '\n');
        }

        int currentRow = 0;
        int currentColumn = 0;
        int textIndex = 0;

        while (currentColumn < plainText.length()) {
            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            railMatrix[currentRow][currentColumn] = plainText.charAt(textIndex);
            currentColumn++;

            currentRow = movingDown ? currentRow + 1 : currentRow - 1;
            textIndex++;
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

    public String decrypt(String cipherText, int railCount) {

        if (railCount == 1 || railCount >= cipherText.length()) {
            return cipherText;
        }

        boolean movingDown = true;
        char[][] railMatrix = new char[railCount][cipherText.length()];

        int currentRow = 0;
        int currentColumn = 0;

        while (currentColumn < cipherText.length()) {
            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            railMatrix[currentRow][currentColumn] = '*';
            currentColumn++;

            currentRow = movingDown ? currentRow + 1 : currentRow - 1;
        }

        int cipherIndex = 0;
        for (int rail = 0; rail < railCount; rail++) {
            for (int column = 0; column < cipherText.length(); column++) {
                if (railMatrix[rail][column] == '*') {
                    railMatrix[rail][column] = cipherText.charAt(cipherIndex++);
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        currentRow = 0;
        currentColumn = 0;

        while (currentColumn < cipherText.length()) {
            if (currentRow == 0) {
                movingDown = true;
            } else if (currentRow == railCount - 1) {
                movingDown = false;
            }

            decryptedText.append(railMatrix[currentRow][currentColumn]);
            currentColumn++;

            currentRow = movingDown ? currentRow + 1 : currentRow - 1;
        }

        return decryptedText.toString();
    }
}