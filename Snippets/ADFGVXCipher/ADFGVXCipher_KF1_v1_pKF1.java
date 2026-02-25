package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ADFGVX cipher implementation (fractionation + columnar transposition).
 */
public class Class1 {

    // ADFGVX row/column labels
    private static final char[] COORDINATE_LABELS = {'A', 'D', 'F', 'G', 'V', 'X'};

    // ADFGVX 6x6 substitution square
    private static final char[][] SUBSTITUTION_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    // Map from coordinate pair (e.g. "AD") to character
    private static final Map<String, Character> COORDINATE_TO_CHAR = new HashMap<>();

    // Map from character to coordinate pair (e.g. 'N' -> "AA")
    private static final Map<Character, String> CHAR_TO_COORDINATE = new HashMap<>();

    static {
        for (int row = 0; row < SUBSTITUTION_SQUARE.length; row++) {
            for (int col = 0; col < SUBSTITUTION_SQUARE[row].length; col++) {
                String coordinate = "" + COORDINATE_LABELS[row] + COORDINATE_LABELS[col];
                char value = SUBSTITUTION_SQUARE[row][col];
                COORDINATE_TO_CHAR.put(coordinate, value);
                CHAR_TO_COORDINATE.put(value, coordinate);
            }
        }
    }

    public String method1(String plaintext, String key) {
        String normalizedPlaintext =
            plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");

        StringBuilder fractionated = new StringBuilder();
        for (char ch : normalizedPlaintext.toCharArray()) {
            fractionated.append(CHAR_TO_COORDINATE.get(ch));
        }

        return encryptColumnarTransposition(fractionated.toString(), key);
    }

    public String method2(String ciphertext, String key) {
        String unfractionated = decryptColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < unfractionated.length(); i += 2) {
            String coordinatePair = unfractionated.substring(i, i + 2);
            plaintext.append(COORDINATE_TO_CHAR.get(coordinatePair));
        }

        return plaintext.toString();
    }

    private String encryptColumnarTransposition(String text, String key) {
        int rows = (int) Math.ceil((double) text.length() / key.length());
        char[][] grid = new char[rows][key.length()];

        for (char[] row : grid) {
            Arrays.fill(row, '_');
        }

        for (int i = 0; i < text.length(); i++) {
            grid[i / key.length()][i % key.length()] = text.charAt(i);
        }

        StringBuilder result = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char sortedKeyChar : sortedKey) {
            int colIndex = key.indexOf(sortedKeyChar);
            for (char[] row : grid) {
                if (row[colIndex] != '_') {
                    result.append(row[colIndex]);
                }
            }
        }

        return result.toString();
    }

    private String decryptColumnarTransposition(String text, String key) {
        int rows = (int) Math.ceil((double) text.length() / key.length());
        char[][] grid = new char[rows][key.length()];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int textIndex = 0;
        for (char sortedKeyChar : sortedKey) {
            int colIndex = key.indexOf(sortedKeyChar);
            for (int row = 0; row < rows; row++) {
                if (textIndex < text.length()) {
                    grid[row][colIndex] = text.charAt(textIndex++);
                } else {
                    grid[row][colIndex] = '_';
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (char[] row : grid) {
            for (char ch : row) {
                if (ch != '_') {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }
}