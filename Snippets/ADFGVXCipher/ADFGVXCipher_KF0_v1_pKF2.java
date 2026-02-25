package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ADFGVX cipher implementation.
 *
 * <p>This cipher combines a Polybius square substitution with a columnar
 * transposition. It operates on the characters A–Z and 0–9.</p>
 *
 * <p>Example:</p>
 * <pre>
 * ADFGVXCipher cipher = new ADFGVXCipher();
 * String encrypted = cipher.encrypt("attack at 1200am", "PRIVACY");
 * String decrypted = cipher.decrypt(encrypted, "PRIVACY");
 * </pre>
 */
public class ADFGVXCipher {

    /** Labels used for rows and columns in the Polybius square. */
    private static final char[] POLYBIUS_LETTERS = {'A', 'D', 'F', 'G', 'V', 'X'};

    /** Polybius square used for substitution. */
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    /** Maps a pair of ADFGVX letters (e.g., "AD") to the corresponding character. */
    private static final Map<String, Character> POLYBIUS_MAP = new HashMap<>();

    /** Maps a character to its pair of ADFGVX letters (e.g., 'N' -> "AA"). */
    private static final Map<Character, String> REVERSE_POLYBIUS_MAP = new HashMap<>();

    static {
        for (int row = 0; row < POLYBIUS_SQUARE.length; row++) {
            for (int col = 0; col < POLYBIUS_SQUARE[row].length; col++) {
                String key = "" + POLYBIUS_LETTERS[row] + POLYBIUS_LETTERS[col];
                char value = POLYBIUS_SQUARE[row][col];
                POLYBIUS_MAP.put(key, value);
                REVERSE_POLYBIUS_MAP.put(value, key);
            }
        }
    }

    /**
     * Encrypts plaintext using the ADFGVX cipher.
     *
     * <ol>
     *   <li>Sanitize plaintext (keep only A–Z and 0–9, convert to uppercase).</li>
     *   <li>Substitute each character with a pair of ADFGVX letters.</li>
     *   <li>Apply columnar transposition using the key.</li>
     * </ol>
     *
     * @param plaintext the message to encrypt
     * @param key       the keyword for columnar transposition
     * @return ciphertext
     */
    public String encrypt(String plaintext, String key) {
        String sanitized = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder fractionatedText = new StringBuilder();

        for (char c : sanitized.toCharArray()) {
            fractionatedText.append(REVERSE_POLYBIUS_MAP.get(c));
        }

        return columnarTransposition(fractionatedText.toString(), key);
    }

    /**
     * Decrypts ciphertext using the ADFGVX cipher.
     *
     * <ol>
     *   <li>Reverse the columnar transposition using the key.</li>
     *   <li>Convert each pair of ADFGVX letters back to the original character.</li>
     * </ol>
     *
     * @param ciphertext the encrypted message
     * @param key        the keyword used during encryption
     * @return decrypted plaintext
     */
    public String decrypt(String ciphertext, String key) {
        String fractionatedText = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < fractionatedText.length(); i += 2) {
            String pair = fractionatedText.substring(i, i + 2);
            plaintext.append(POLYBIUS_MAP.get(pair));
        }

        return plaintext.toString();
    }

    /**
     * Performs columnar transposition on the given text.
     *
     * <p>Text is written row-wise into a table with {@code key.length()} columns,
     * then read column-wise according to the alphabetical order of the key.</p>
     *
     * @param text the text to transpose
     * @param key  the keyword defining column order
     * @return transposed text
     */
    private String columnarTransposition(String text, String key) {
        int numCols = key.length();
        int numRows = (int) Math.ceil((double) text.length() / numCols);

        char[][] table = new char[numRows][numCols];
        for (char[] row : table) {
            Arrays.fill(row, '_');
        }

        for (int i = 0; i < text.length(); i++) {
            int row = i / numCols;
            int col = i % numCols;
            table[row][col] = text.charAt(i);
        }

        StringBuilder ciphertext = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        for (char keyChar : sortedKey) {
            int column = key.indexOf(keyChar);
            for (char[] row : table) {
                if (row[column] != '_') {
                    ciphertext.append(row[column]);
                }
            }
        }

        return ciphertext.toString();
    }

    /**
     * Reverses the columnar transposition.
     *
     * <p>Ciphertext is written column-wise into a table according to the
     * alphabetical order of the key, then read row-wise.</p>
     *
     * @param ciphertext the text to reverse-transpose
     * @param key        the keyword defining column order
     * @return original row-wise text
     */
    private String reverseColumnarTransposition(String ciphertext, String key) {
        int numCols = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numCols);

        char[][] table = new char[numRows][numCols];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int index = 0;
        for (char keyChar : sortedKey) {
            int column = key.indexOf(keyChar);
            for (int row = 0; row < numRows; row++) {
                if (index < ciphertext.length()) {
                    table[row][column] = ciphertext.charAt(index++);
                } else {
                    table[row][column] = '_';
                }
            }
        }

        StringBuilder fractionatedText = new StringBuilder();
        for (char[] row : table) {
            for (char cell : row) {
                if (cell != '_') {
                    fractionatedText.append(cell);
                }
            }
        }

        return fractionatedText.toString();
    }
}