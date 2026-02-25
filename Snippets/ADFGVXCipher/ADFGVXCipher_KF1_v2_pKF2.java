package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of the ADFGVX cipher.
 *
 * This class provides methods to encrypt and decrypt text using the ADFGVX
 * fractionating cipher combined with a columnar transposition.
 */
public class Class1 {

    /** Row/column labels used in the Polybius square. */
    private static final char[] COORDINATE_LABELS = {'A', 'D', 'F', 'G', 'V', 'X'};

    /** Polybius square used for fractionation. */
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    /** Map from coordinate pair (e.g. "AD") to character in the Polybius square. */
    private static final Map<String, Character> COORDINATES_TO_CHAR = new HashMap<>();

    /** Map from character to coordinate pair (e.g. 'A' -> "AD") in the Polybius square. */
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
     * Encrypts the given plaintext using the ADFGVX cipher with the provided key.
     *
     * @param plaintext the text to encrypt
     * @param key       the key used for the columnar transposition
     * @return the encrypted ciphertext
     */
    public String encrypt(String plaintext, String key) {
        String normalized = normalizePlaintext(plaintext);
        StringBuilder fractionated = new StringBuilder();

        for (char ch : normalized.toCharArray()) {
            fractionated.append(CHAR_TO_COORDINATES.get(ch));
        }

        return applyColumnarTransposition(fractionated.toString(), key);
    }

    /**
     * Decrypts the given ciphertext using the ADFGVX cipher with the provided key.
     *
     * @param ciphertext the text to decrypt
     * @param key        the key used for the columnar transposition
     * @return the decrypted plaintext
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
     * Applies columnar transposition to the fractionated text using the given key.
     *
     * @param fractionated the text consisting of coordinate pairs
     * @param key          the key used for the columnar transposition
     * @return the transposed ciphertext
     */
    private String applyColumnarTransposition(String fractionated, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) fractionated.length() / columns);
        char[][] grid = new char[rows][columns];

        fillGridWithPadding(grid, '_');

        for (int i = 0; i < fractionated.length(); i++) {
            grid[i / columns][i % columns] = fractionated.charAt(i);
        }

        StringBuilder result = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char keyChar : sortedKey) {
            int colIndex = key.indexOf(keyChar);
            for (char[] row : grid) {
                if (row[colIndex] != '_') {
                    result.append(row[colIndex]);
                }
            }
        }

        return result.toString();
    }

    /**
     * Reverses the columnar transposition for the given ciphertext and key.
     *
     * @param ciphertext the transposed ciphertext
     * @param key        the key used for the columnar transposition
     * @return the reordered fractionated text (sequence of coordinate pairs)
     */
    private String reverseColumnarTransposition(String ciphertext, String key) {
        int columns = key.length();
        int rows = (int) Math.ceil((double) ciphertext.length() / columns);
        char[][] grid = new char[rows][columns];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int index = 0;
        for (char keyChar : sortedKey) {
            int colIndex = key.indexOf(keyChar);
            for (int row = 0; row < rows; row++) {
                if (index < ciphertext.length()) {
                    grid[row][colIndex] = ciphertext.charAt(index++);
                } else {
                    grid[row][colIndex] = '_';
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
     * Normalizes plaintext by converting to uppercase and removing non-alphanumeric characters.
     *
     * @param plaintext the input text
     * @return normalized text containing only A-Z and 0-9
     */
    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    /**
     * Fills the given 2D grid with the specified padding character.
     *
     * @param grid    the 2D character array to fill
     * @param padding the character used for padding
     */
    private void fillGridWithPadding(char[][] grid, char padding) {
        for (char[] row : grid) {
            Arrays.fill(row, padding);
        }
    }
}