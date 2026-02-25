package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ADFGVXCipher {

    private static final char[] POLYBIUS_LABELS = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    private static final Map<String, Character> COORDINATE_TO_CHAR = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_COORDINATE = new HashMap<>();

    static {
        for (int rowIndex = 0; rowIndex < POLYBIUS_SQUARE.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < POLYBIUS_SQUARE[rowIndex].length; columnIndex++) {
                String coordinate =
                    "" + POLYBIUS_LABELS[rowIndex] + POLYBIUS_LABELS[columnIndex];
                char symbol = POLYBIUS_SQUARE[rowIndex][columnIndex];
                COORDINATE_TO_CHAR.put(coordinate, symbol);
                CHAR_TO_COORDINATE.put(symbol, coordinate);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder coordinateSequence = new StringBuilder();

        for (char symbol : normalizedPlaintext.toCharArray()) {
            coordinateSequence.append(CHAR_TO_COORDINATE.get(symbol));
        }

        return applyColumnarTransposition(coordinateSequence.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String coordinateSequence = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int index = 0; index < coordinateSequence.length(); index += 2) {
            String coordinatePair = coordinateSequence.substring(index, index + 2);
            plaintext.append(COORDINATE_TO_CHAR.get(coordinatePair));
        }

        return plaintext.toString();
    }

    private String applyColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] transpositionMatrix = new char[rowCount][columnCount];
        for (char[] row : transpositionMatrix) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int rowIndex = index / columnCount;
            int columnIndex = index % columnCount;
            transpositionMatrix[rowIndex][columnIndex] = text.charAt(index);
        }

        StringBuilder ciphertext = new StringBuilder();
        char[] sortedKeyChars = key.toCharArray();
        Arrays.sort(sortedKeyChars);

        for (char keyChar : sortedKeyChars) {
            int columnIndex = key.indexOf(keyChar);
            for (char[] row : transpositionMatrix) {
                if (row[columnIndex] != '_') {
                    ciphertext.append(row[columnIndex]);
                }
            }
        }

        return ciphertext.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) ciphertext.length() / columnCount);

        char[][] transpositionMatrix = new char[rowCount][columnCount];

        char[] sortedKeyChars = key.toCharArray();
        Arrays.sort(sortedKeyChars);

        int ciphertextIndex = 0;
        for (char keyChar : sortedKeyChars) {
            int columnIndex = key.indexOf(keyChar);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (ciphertextIndex < ciphertext.length()) {
                    transpositionMatrix[rowIndex][columnIndex] = ciphertext.charAt(ciphertextIndex++);
                } else {
                    transpositionMatrix[rowIndex][columnIndex] = '_';
                }
            }
        }

        StringBuilder coordinateSequence = new StringBuilder();
        for (char[] row : transpositionMatrix) {
            for (char cell : row) {
                if (cell != '_') {
                    coordinateSequence.append(cell);
                }
            }
        }

        return coordinateSequence.toString();
    }
}