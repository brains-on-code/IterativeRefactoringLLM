package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the ADFGVX cipher.
 *
 * This cipher uses:
 * 1. A Polybius square to map characters to coordinate pairs.
 * 2. A columnar transposition based on a key.
 */
public class Class1 {

    private static final char[] COORDINATE_LABELS = {'A', 'D', 'F', 'G', 'V', 'X'};

    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    private static final Map<String, Character> COORDINATES_TO_CHAR = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_COORDINATES = new HashMap<>();

    static {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                String coordinates = "" + COORDINATE_LABELS[row] + COORDINATE_LABELS[col];
                char value = POLYBIUS_SQUARE[row][col];
                COORDINATES_TO_CHAR.put(coordinates, value);
                CHAR_TO_COORDINATES.put(value, coordinates);
            }
        }
    }

    /**
     * Encrypts plaintext using the ADFGVX cipher and the given key.
     *
     * @param plaintext the input text to encrypt
     * @param key       the columnar transposition key
     * @return encrypted text
     */
    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = normalizePlaintext(plaintext);
        StringBuilder fractionated = new StringBuilder();

        for (char ch : normalizedPlaintext.toCharArray()) {
            fractionated.append(CHAR_TO_COORDINATES.get(ch));
        }

        return applyColumnarTransposition(fractionated.toString(), key);
    }

    /**
     * Decrypts ciphertext using the ADFGVX cipher and the given key.
     *
     * @param ciphertext the input text to decrypt
     * @param key        the columnar transposition key
     * @return decrypted plaintext
     */
    public String decrypt(String ciphertext, String key) {
        String reordered = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < reordered.length(); i += 2) {
            String coordinates = reordered.substring(i, i + 2);
            plaintext.append(COORDINATES_TO_CHAR.get(coordinates));
        }

        return plaintext.toString();
    }

    /**
     * Applies columnar transposition to the given text using the key.
     *
     * @param text the text to transpose
     * @param key  the transposition key
     * @return transposed text
     */
    private String applyColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);
        char[][] grid = new char[rowCount][columnCount];

        fillGridWithPadding(grid, '_');

        for (int i = 0; i < text.length(); i++) {
            grid[i / columnCount][i % columnCount] = text.charAt(i);
        }

        StringBuilder result = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            for (char[] row : grid) {
                if (row[columnIndex] != '_') {
                    result.append(row[columnIndex]);
                }
            }
        }

        return result.toString();
    }

    /**
     * Reverses the columnar transposition applied with the given key.
     *
     * @param ciphertext the transposed text
     * @param key        the transposition key
     * @return text restored to row-wise order
     */
    private String reverseColumnarTransposition(String ciphertext, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) ciphertext.length() / columnCount);
        char[][] grid = new char[rowCount][columnCount];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int index = 0;
        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            for (int row = 0; row < rowCount; row++) {
                if (index < ciphertext.length()) {
                    grid[row][columnIndex] = ciphertext.charAt(index++);
                } else {
                    grid[row][columnIndex] = '_';
                }
            }
        }

        StringBuilder reordered = new StringBuilder();
        for (char[] row : grid) {
            for (char ch : row) {
                if (ch != '_') {
                    reordered.append(ch);
                }
            }
        }

        return reordered.toString();
    }

    /**
     * Normalizes plaintext by converting to uppercase and removing
     * all non-alphanumeric characters.
     *
     * @param plaintext the input text
     * @return normalized text
     */
    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    /**
     * Fills the entire 2D grid with the given padding character.
     *
     * @param grid    the grid to fill
     * @param padding the padding character
     */
    private void fillGridWithPadding(char[][] grid, char padding) {
        for (char[] row : grid) {
            Arrays.fill(row, padding);
        }
    }
}