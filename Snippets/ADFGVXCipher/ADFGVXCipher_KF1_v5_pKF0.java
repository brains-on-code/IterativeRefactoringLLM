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
        initializePolybiusMappings();
    }

    private static void initializePolybiusMappings() {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                char value = POLYBIUS_SQUARE[row][col];
                String coords = "" + COORDS[row] + COORDS[col];
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
    public String encrypt(String plaintext, String key) {
        String normalizedText = normalizePlaintext(plaintext);
        StringBuilder coordinates = new StringBuilder(normalizedText.length() * 2);

        for (char character : normalizedText.toCharArray()) {
            coordinates.append(CHAR_TO_COORDS.get(character));
        }

        return transposeEncrypt(coordinates.toString(), key);
    }

    /**
     * Decrypts the given ciphertext using the ADFGVX cipher with the provided key.
     *
     * @param ciphertext the text to decrypt
     * @param key        the transposition key
     * @return the decrypted plaintext
     */
    public String decrypt(String ciphertext, String key) {
        String coordinates = transposeDecrypt(ciphertext, key);
        StringBuilder plaintext = new StringBuilder(coordinates.length() / 2);

        for (int i = 0; i < coordinates.length(); i += 2) {
            String pair = coordinates.substring(i, i + 2);
            plaintext.append(COORDS_TO_CHAR.get(pair));
        }

        return plaintext.toString();
    }

    /**
     * Columnar transposition encryption.
     *
     * @param text the text to transpose
     * @param key  the transposition key
     * @return transposed text
     */
    private String transposeEncrypt(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] grid = createFilledGrid(rowCount, columnCount, PADDING_CHAR);
        fillGridRowWise(grid, text);

        StringBuilder result = new StringBuilder(text.length());
        int[] columnOrder = getColumnOrder(key);

        for (int col : columnOrder) {
            for (char[] row : grid) {
                if (row[col] != PADDING_CHAR) {
                    result.append(row[col]);
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
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] grid = new char[rowCount][columnCount];
        int[] columnOrder = getColumnOrder(key);

        int textIndex = 0;
        for (int col : columnOrder) {
            for (int row = 0; row < rowCount; row++) {
                if (textIndex < text.length()) {
                    grid[row][col] = text.charAt(textIndex++);
                } else {
                    grid[row][col] = PADDING_CHAR;
                }
            }
        }

        StringBuilder result = new StringBuilder(text.length());
        for (char[] row : grid) {
            for (char character : row) {
                if (character != PADDING_CHAR) {
                    result.append(character);
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

    /**
     * Returns the column order for the given key.
     * Handles duplicate characters by assigning increasing indices.
     */
    private int[] getColumnOrder(String key) {
        int length = key.length();
        KeyChar[] keyChars = new KeyChar[length];

        for (int i = 0; i < length; i++) {
            keyChars[i] = new KeyChar(key.charAt(i), i);
        }

        Arrays.sort(keyChars, (a, b) -> {
            int charCompare = Character.compare(a.character, b.character);
            return (charCompare != 0) ? charCompare : Integer.compare(a.index, b.index);
        });

        int[] order = new int[length];
        for (int i = 0; i < length; i++) {
            order[i] = keyChars[i].index;
        }

        return order;
    }

    /** Helper class to keep track of character and its original index. */
    private static class KeyChar {
        final char character;
        final int index;

        KeyChar(char character, int index) {
            this.character = character;
            this.index = index;
        }
    }
}