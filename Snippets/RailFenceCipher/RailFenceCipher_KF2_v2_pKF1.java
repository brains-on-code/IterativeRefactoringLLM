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

        int currentRail = 0;
        int currentColumn = 0;
        int textPosition = 0;

        while (currentColumn < plainText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == numberOfRails - 1) {
                isMovingDown = false;
            }

            railFence[currentRail][currentColumn] = plainText.charAt(textPosition);
            currentColumn++;

            currentRail = isMovingDown ? currentRail + 1 : currentRail - 1;
            textPosition++;
        }

        StringBuilder encryptedText = new StringBuilder();
        for (char[] railRow : railFence) {
            for (char character : railRow) {
                if (character != '\n') {
                    encryptedText.append(character);
                }
            }
        }
        return encryptedText.toString();
    }

    public String decrypt(String cipherText, int numberOfRails) {

        if (numberOfRails == 1 || numberOfRails >= cipherText.length()) {
            return cipherText;
        }

        boolean isMovingDown = true;
        char[][] railFence = new char[numberOfRails][cipherText.length()];

        int currentRail = 0;
        int currentColumn = 0;

        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == numberOfRails - 1) {
                isMovingDown = false;
            }

            railFence[currentRail][currentColumn] = '*';
            currentColumn++;

            currentRail = isMovingDown ? currentRail + 1 : currentRail - 1;
        }

        int cipherPosition = 0;
        for (int railIndex = 0; railIndex < numberOfRails; railIndex++) {
            for (int columnIndex = 0; columnIndex < cipherText.length(); columnIndex++) {
                if (railFence[railIndex][columnIndex] == '*') {
                    railFence[railIndex][columnIndex] = cipherText.charAt(cipherPosition++);
                }
            }
        }

        StringBuilder decryptedText = new StringBuilder();
        currentRail = 0;
        currentColumn = 0;

        while (currentColumn < cipherText.length()) {
            if (currentRail == 0) {
                isMovingDown = true;
            } else if (currentRail == numberOfRails - 1) {
                isMovingDown = false;
            }

            decryptedText.append(railFence[currentRail][currentColumn]);
            currentColumn++;

            currentRail = isMovingDown ? currentRail + 1 : currentRail - 1;
        }

        return decryptedText.toString();
    }
}