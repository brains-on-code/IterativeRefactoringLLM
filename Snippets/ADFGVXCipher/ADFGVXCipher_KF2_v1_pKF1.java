package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ADFGVXCipher {

    private static final char[] POLYBIUS_COORDINATES = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    private static final Map<String, Character> COORDINATE_TO_CHAR_MAP = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_COORDINATE_MAP = new HashMap<>();

    static {
        for (int rowIndex = 0; rowIndex < POLYBIUS_SQUARE.length; rowIndex++) {
            for (int colIndex = 0; colIndex < POLYBIUS_SQUARE[rowIndex].length; colIndex++) {
                String coordinateKey =
                    "" + POLYBIUS_COORDINATES[rowIndex] + POLYBIUS_COORDINATES[colIndex];
                char squareChar = POLYBIUS_SQUARE[rowIndex][colIndex];
                COORDINATE_TO_CHAR_MAP.put(coordinateKey, squareChar);
                CHAR_TO_COORDINATE_MAP.put(squareChar, coordinateKey);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder fractionatedPlaintext = new StringBuilder();

        for (char currentChar : normalizedPlaintext.toCharArray()) {
            fractionatedPlaintext.append(CHAR_TO_COORDINATE_MAP.get(currentChar));
        }

        return applyColumnarTransposition(fractionatedPlaintext.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String fractionatedCiphertext = reverseColumnarTransposition(ciphertext, key);

        StringBuilder decryptedPlaintext = new StringBuilder();
        for (int index = 0; index < fractionatedCiphertext.length(); index += 2) {
            String coordinatePair = fractionatedCiphertext.substring(index, index + 2);
            decryptedPlaintext.append(COORDINATE_TO_CHAR_MAP.get(coordinatePair));
        }

        return decryptedPlaintext.toString();
    }

    private String applyColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] transpositionTable = new char[rowCount][columnCount];
        for (char[] row : transpositionTable) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int rowIndex = index / columnCount;
            int colIndex = index % columnCount;
            transpositionTable[rowIndex][colIndex] = text.charAt(index);
        }

        StringBuilder ciphertextBuilder = new StringBuilder();
        char[] sortedKeyChars = key.toCharArray();
        Arrays.sort(sortedKeyChars);

        for (char sortedKeyChar : sortedKeyChars) {
            int columnIndex = key.indexOf(sortedKeyChar);
            for (char[] row : transpositionTable) {
                if (row[columnIndex] != '_') {
                    ciphertextBuilder.append(row[columnIndex]);
                }
            }
        }

        return ciphertextBuilder.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) ciphertext.length() / columnCount);

        char[][] transpositionTable = new char[rowCount][columnCount];

        char[] sortedKeyChars = key.toCharArray();
        Arrays.sort(sortedKeyChars);

        int cipherIndex = 0;
        for (char sortedKeyChar : sortedKeyChars) {
            int columnIndex = key.indexOf(sortedKeyChar);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (cipherIndex < ciphertext.length()) {
                    transpositionTable[rowIndex][columnIndex] = ciphertext.charAt(cipherIndex++);
                } else {
                    transpositionTable[rowIndex][columnIndex] = '_';
                }
            }
        }

        StringBuilder fractionatedTextBuilder = new StringBuilder();
        for (char[] row : transpositionTable) {
            for (char cell : row) {
                if (cell != '_') {
                    fractionatedTextBuilder.append(cell);
                }
            }
        }

        return fractionatedTextBuilder.toString();
    }
}