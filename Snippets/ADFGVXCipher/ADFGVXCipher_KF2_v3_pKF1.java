package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ADFGVXCipher {

    private static final char[] POLYBIUS_AXES = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_GRID = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    private static final Map<String, Character> COORDINATE_TO_SYMBOL = new HashMap<>();
    private static final Map<Character, String> SYMBOL_TO_COORDINATE = new HashMap<>();

    static {
        for (int rowIndex = 0; rowIndex < POLYBIUS_GRID.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < POLYBIUS_GRID[rowIndex].length; columnIndex++) {
                String coordinate =
                    "" + POLYBIUS_AXES[rowIndex] + POLYBIUS_AXES[columnIndex];
                char symbol = POLYBIUS_GRID[rowIndex][columnIndex];
                COORDINATE_TO_SYMBOL.put(coordinate, symbol);
                SYMBOL_TO_COORDINATE.put(symbol, coordinate);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder coordinateSequenceBuilder = new StringBuilder();

        for (char symbol : normalizedPlaintext.toCharArray()) {
            coordinateSequenceBuilder.append(SYMBOL_TO_COORDINATE.get(symbol));
        }

        return applyColumnarTransposition(coordinateSequenceBuilder.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String coordinateSequence = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintextBuilder = new StringBuilder();
        for (int index = 0; index < coordinateSequence.length(); index += 2) {
            String coordinatePair = coordinateSequence.substring(index, index + 2);
            plaintextBuilder.append(COORDINATE_TO_SYMBOL.get(coordinatePair));
        }

        return plaintextBuilder.toString();
    }

    private String applyColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] transpositionGrid = new char[rowCount][columnCount];
        for (char[] row : transpositionGrid) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int rowIndex = index / columnCount;
            int columnIndex = index % columnCount;
            transpositionGrid[rowIndex][columnIndex] = text.charAt(index);
        }

        StringBuilder ciphertextBuilder = new StringBuilder();
        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        for (char keyCharacter : sortedKeyCharacters) {
            int columnIndex = key.indexOf(keyCharacter);
            for (char[] row : transpositionGrid) {
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

        char[][] transpositionGrid = new char[rowCount][columnCount];

        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        int ciphertextIndex = 0;
        for (char keyCharacter : sortedKeyCharacters) {
            int columnIndex = key.indexOf(keyCharacter);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (ciphertextIndex < ciphertext.length()) {
                    transpositionGrid[rowIndex][columnIndex] = ciphertext.charAt(ciphertextIndex++);
                } else {
                    transpositionGrid[rowIndex][columnIndex] = '_';
                }
            }
        }

        StringBuilder coordinateSequenceBuilder = new StringBuilder();
        for (char[] row : transpositionGrid) {
            for (char cell : row) {
                if (cell != '_') {
                    coordinateSequenceBuilder.append(cell);
                }
            }
        }

        return coordinateSequenceBuilder.toString();
    }
}