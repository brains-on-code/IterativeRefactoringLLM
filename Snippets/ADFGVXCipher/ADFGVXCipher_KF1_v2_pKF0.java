package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the ADFGVX cipher.
 *
 * This cipher uses:
 * 1. A 6x6 substitution square (Polybius square) with coordinates A, D, F, G, V, X.
 * 2. A columnar transposition based on a keyword.
 */
public class Class1 {

    /** Coordinate labels for the Polybius square. */
    private static final char[] COORDS = {'A', 'D', 'F', 'G', 'V', 'X'};

    /** Polybius square used for substitution. */
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    /** Map from coordinate pair (e.g. "AD") to character. */
    private static final Map<String, Character> COORDS_TO_CHAR = new HashMap<>();

    /** Map from character to coordinate pair (e.g. 'A' -> "AD"). */
    private static final Map<Character, String> CHAR_TO_COORDS = new HashMap<>();

    /** Padding character used in transposition grid. */
    private static final char PADDING_CHAR = '_';

    static {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                String coords = "" + COORDS[row] + COORDS[col];
                char value = POLYBIUS_SQUARE[row][col];
                COORDS_TO_CHAR.put(coords, value);
                CHAR_TO_COORDS.put(value, coords);
            }
        }
    }

    /**
     * Encrypts the given plaintext using the ADFGVX cipher with the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the transposition key
     * @return the encrypted ciphertext
     */
    public String method1(String plaintext, String key) {
        String normalized = normalizePlaintext(plaintext);
        StringBuilder coordsBuilder = new StringBuilder();

        for (char ch : normalized.toCharArray()) {
            coordsBuilder.append(CHAR_TO_COORDS.get(ch));
        }

        return transposeEncrypt(coordsBuilder.toString(), key);
    }

    /**
     * Decrypts the given ciphertext using the ADFGVX cipher with the provided key.
     *
     * @param ciphertext the text to decrypt
     * @param key        the transposition key
     * @return the decrypted plaintext
     */
    public String method2(String ciphertext, String key) {
        String coords = transposeDecrypt(ciphertext, key);

        StringBuilder plaintextBuilder = new StringBuilder();
        for (int i = 0; i < coords.length(); i += 2) {
            String pair = coords.substring(i, i + 2);
            plaintextBuilder.append(COORDS_TO_CHAR.get(pair));
        }

        return plaintextBuilder.toString();
    }

    /**
     * Columnar transposition encryption.
     *
     * @param text the text to transpose
     * @param key  the transposition key
     * @return transposed text
     */
    private String transposeEncrypt(String text, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] grid = createFilledGrid(rows, cols, PADDING_CHAR);
        fillGridRowWise(grid, text);

        StringBuilder result = new StringBuilder();
        char[] sortedKey = getSortedKey(key);

        for (char keyChar : sortedKey) {
            int colIndex = key.indexOf(keyChar);
            for (char[] row : grid) {
                if (row[colIndex] != PADDING_CHAR) {
                    result.append(row[colIndex]);
                }
            }
        }

        return result.toString();
    }

    /**
     * Columnar transposition decryption.
     *
     * @param text the transposed text
     * @param key  the transposition key
     * @return original text before transposition
     */
    private String transposeDecrypt(String text, String key) {
        int cols = key.length();
        int rows = (int) Math.ceil((double) text.length() / cols);

        char[][] grid = new char[rows][cols];
        char[] sortedKey = getSortedKey(key);

        int textIndex = 0;
        for (char keyChar : sortedKey) {
            int colIndex = key.indexOf(keyChar);
            for (int row = 0; row < rows; row++) {
                if (textIndex < text.length()) {
                    grid[row][colIndex] = text.charAt(textIndex++);
                } else {
                    grid[row][colIndex] = PADDING_CHAR;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (char[] row : grid) {
            for (char ch : row) {
                if (ch != PADDING_CHAR) {
                    result.append(ch);
                }
            }
        }

        return result.toString();
    }

    /** Normalizes plaintext by uppercasing and removing non-alphanumeric characters. */
    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    /** Creates a grid with the given dimensions, filled with the specified character. */
    private char[][] createFilledGrid(int rows, int cols, char fillChar) {
        char[][] grid = new char[rows][cols];
        for (char[] row : grid) {
            Arrays.fill(row, fillChar);
        }
        return grid;
    }

    /** Fills the grid row-wise with the given text. */
    private void fillGridRowWise(char[][] grid, String text) {
        int cols = grid[0].length;
        for (int i = 0; i < text.length(); i++) {
            grid[i / cols][i % cols] = text.charAt(i);
        }
    }

    /** Returns a sorted character array of the key. */
    private char[] getSortedKey(String key) {
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);
        return sortedKey;
    }
}