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
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                String coordinate =
                    "" + POLYBIUS_LABELS[row] + POLYBIUS_LABELS[col];
                char symbol = POLYBIUS_SQUARE[row][col];
                COORDINATE_TO_CHAR.put(coordinate, symbol);
                CHAR_TO_COORDINATE.put(symbol, coordinate);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder coordinateSequence = new StringBuilder();

        for (char ch : normalizedPlaintext.toCharArray()) {
            coordinateSequence.append(CHAR_TO_COORDINATE.get(ch));
        }

        return applyColumnarTransposition(coordinateSequence.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String coordinateSequence = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintextBuilder = new StringBuilder();
        for (int i = 0; i < coordinateSequence.length(); i += 2) {
            String coordinatePair = coordinateSequence.substring(i, i + 2);
            plaintextBuilder.append(COORDINATE_TO_CHAR.get(coordinatePair));
        }

        return plaintextBuilder.toString();
    }

    private String applyColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] grid = new char[rowCount][columnCount];
        for (char[] row : grid) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int row = index / columnCount;
            int col = index % columnCount;
            grid[row][col] = text.charAt(index);
        }

        StringBuilder ciphertextBuilder = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            for (char[] row : grid) {
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

        char[][] grid = new char[rowCount][columnCount];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int cipherIndex = 0;
        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            for (int row = 0; row < rowCount; row++) {
                if (cipherIndex < ciphertext.length()) {
                    grid[row][columnIndex] = ciphertext.charAt(cipherIndex++);
                } else {
                    grid[row][columnIndex] = '_';
                }
            }
        }

        StringBuilder coordinateSequenceBuilder = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell != '_') {
                    coordinateSequenceBuilder.append(cell);
                }
            }
        }

        return coordinateSequenceBuilder.toString();
    }
}