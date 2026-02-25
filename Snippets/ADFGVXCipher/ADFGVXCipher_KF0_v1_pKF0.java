package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The ADFGVX cipher is a fractionating transposition cipher that was used by
 * the German Army during World War I. It combines a Polybius square substitution
 * with a columnar transposition to enhance encryption strength.
 *
 * <p>The name "ADFGVX" refers to the six letters (A, D, F, G, V, X) used as row and
 * column labels in the Polybius square. This cipher was designed to secure
 * communication and create complex, hard-to-break ciphertexts.
 *
 * <p>Learn more: <a href="https://en.wikipedia.org/wiki/ADFGVX_cipher">ADFGVX Cipher - Wikipedia</a>.
 *
 * <p>Example usage:
 * <pre>
 * ADFGVXCipher cipher = new ADFGVXCipher();
 * String encrypted = cipher.encrypt("attack at 1200am", "PRIVACY");
 * String decrypted = cipher.decrypt(encrypted, "PRIVACY");
 * </pre>
 *
 * @author bennybebo
 */
public class ADFGVXCipher {

    private static final char PADDING_CHAR = '_';

    // Constants used in the Polybius square
    private static final char[] POLYBIUS_LETTERS = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    // Maps for fast substitution lookups
    private static final Map<String, Character> POLYBIUS_MAP = new HashMap<>();
    private static final Map<Character, String> REVERSE_POLYBIUS_MAP = new HashMap<>();

    // Static block to initialize the lookup tables from the Polybius square
    static {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                char rowLabel = POLYBIUS_LETTERS[row];
                char colLabel = POLYBIUS_LETTERS[col];
                char value = POLYBIUS_SQUARE[row][col];

                String key = "" + rowLabel + colLabel;
                POLYBIUS_MAP.put(key, value);
                REVERSE_POLYBIUS_MAP.put(value, key);
            }
        }
    }

    /**
     * Encrypts a given plaintext using the ADFGVX cipher with the provided keyword.
     * Steps:
     * 1. Substitute each letter in the plaintext with a pair of ADFGVX letters.
     * 2. Perform a columnar transposition on the fractionated text using the keyword.
     *
     * @param plaintext The message to be encrypted (can contain letters and digits).
     * @param key       The keyword for columnar transposition.
     * @return The encrypted message as ciphertext.
     */
    public String encrypt(String plaintext, String key) {
        String sanitizedPlaintext = sanitizePlaintext(plaintext);
        StringBuilder fractionatedText = new StringBuilder();

        for (char c : sanitizedPlaintext.toCharArray()) {
            String substitution = REVERSE_POLYBIUS_MAP.get(c);
            if (substitution != null) {
                fractionatedText.append(substitution);
            }
        }

        return columnarTransposition(fractionatedText.toString(), key);
    }

    /**
     * Decrypts a given ciphertext using the ADFGVX cipher with the provided keyword.
     * Steps:
     * 1. Reverse the columnar transposition performed during encryption.
     * 2. Substitute each pair of ADFGVX letters with the corresponding plaintext letter.
     *
     * @param ciphertext The encrypted message.
     * @param key        The keyword used during encryption.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext, String key) {
        String fractionatedText = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < fractionatedText.length(); i += 2) {
            String pair = fractionatedText.substring(i, i + 2);
            Character decodedChar = POLYBIUS_MAP.get(pair);
            if (decodedChar != null) {
                plaintext.append(decodedChar);
            }
        }

        return plaintext.toString();
    }

    /**
     * Performs columnar transposition during encryption.
     *
     * @param text The fractionated text to be transposed.
     * @param key  The keyword for columnar transposition.
     * @return The transposed text.
     */
    private String columnarTransposition(String text, String key) {
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) text.length() / numColumns);

        char[][] table = createFilledTable(numRows, numColumns, PADDING_CHAR);
        fillTableRowWise(table, text, numColumns);

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        StringBuilder ciphertext = new StringBuilder();
        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            appendColumn(table, columnIndex, ciphertext);
        }

        return ciphertext.toString();
    }

    /**
     * Reverses the columnar transposition during decryption.
     *
     * @param ciphertext The transposed text to be reversed.
     * @param key        The keyword used during encryption.
     * @return The reversed text.
     */
    private String reverseColumnarTransposition(String ciphertext, String key) {
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numColumns);

        char[][] table = new char[numRows][numColumns];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int index = 0;
        for (char keyChar : sortedKey) {
            int columnIndex = key.indexOf(keyChar);
            for (int row = 0; row < numRows; row++) {
                if (index < ciphertext.length()) {
                    table[row][columnIndex] = ciphertext.charAt(index++);
                } else {
                    table[row][columnIndex] = PADDING_CHAR;
                }
            }
        }

        StringBuilder fractionatedText = new StringBuilder();
        for (char[] row : table) {
            for (char cell : row) {
                if (cell != PADDING_CHAR) {
                    fractionatedText.append(cell);
                }
            }
        }

        return fractionatedText.toString();
    }

    private String sanitizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private char[][] createFilledTable(int numRows, int numColumns, char fillChar) {
        char[][] table = new char[numRows][numColumns];
        for (char[] row : table) {
            Arrays.fill(row, fillChar);
        }
        return table;
    }

    private void fillTableRowWise(char[][] table, String text, int numColumns) {
        for (int i = 0; i < text.length(); i++) {
            int row = i / numColumns;
            int col = i % numColumns;
            table[row][col] = text.charAt(i);
        }
    }

    private void appendColumn(char[][] table, int columnIndex, StringBuilder builder) {
        for (char[] row : table) {
            char value = row[columnIndex];
            if (value != PADDING_CHAR) {
                builder.append(value);
            }
        }
    }
}