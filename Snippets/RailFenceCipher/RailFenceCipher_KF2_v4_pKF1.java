package com.thealgorithms.ciphers;

import java.util.Arrays;

public class RailFenceCipher {

    public String encrypt(String plainText, int numberOfRails) {

        if (numberOfRails == 1 || numberOfRails >= plainText.length()) {
            return plainText;
        }

        boolean isMovingDown = true;
        char[][] railFence = new char[numberOfRails][plainText.length()];

        for (int railIndex = 0; railIndex < numberOfRails; railIndex++) {
            Arrays.fill(railFence[railIndex], '\n');
        }

        int currentRailIndex = 0;
        int currentColumnIndex = 0;
        int plainTextIndex = 0;

        while (currentColumnIndex < plainText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == numberOfRails - 1) {
                isMovingDown = false;
            }

            railFence[currentRailIndex][currentColumnIndex] = plainText.charAt(plainTextIndex);
            currentColumnIndex++;

            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
            plainTextIndex++;
        }

        StringBuilder encryptedTextBuilder = new StringBuilder();
        for (char[] railRow : railFence) {
            for (char character : railRow) {
                if (character != '\n') {
                    encryptedTextBuilder.append(character);
                }
            }
        }
        return encryptedTextBuilder.toString();
    }

    public String decrypt(String cipherText, int numberOfRails) {

        if (numberOfRails == 1 || numberOfRails >= cipherText.length()) {
            return cipherText;
        }

        boolean isMovingDown = true;
        char[][] railFence = new char[numberOfRails][cipherText.length()];

        int currentRailIndex = 0;
        int currentColumnIndex = 0;

        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == numberOfRails - 1) {
                isMovingDown = false;
            }

            railFence[currentRailIndex][currentColumnIndex] = '*';
            currentColumnIndex++;

            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
        }

        int cipherTextIndex = 0;
        for (int railIndex = 0; railIndex < numberOfRails; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railFence[railIndex][columnIndex] == '*') {
                    railFence[railIndex][columnIndex] = cipherText.charAt(cipherTextIndex++);
                }
            }
        }

        StringBuilder decryptedTextBuilder = new StringBuilder();
        currentRailIndex = 0;
        currentColumnIndex = 0;

        while (currentColumnIndex < cipherText.length()) {
            if (currentRailIndex == 0) {
                isMovingDown = true;
            } else if (currentRailIndex == numberOfRails - 1) {
                isMovingDown = false;
            }

            decryptedTextBuilder.append(railFence[currentRailIndex][currentColumnIndex]);
            currentColumnIndex++;

            currentRailIndex = isMovingDown ? currentRailIndex + 1 : currentRailIndex - 1;
        }

        return decryptedTextBuilder.toString();
    }
}