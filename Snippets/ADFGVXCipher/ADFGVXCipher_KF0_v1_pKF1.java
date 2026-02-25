package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * The ADFGVX cipher is a fractionating transposition cipher that was used by
 * the German Army during World War I. It combines a **Polybius square substitution**
 * with a **columnar transposition** to enhance encryption strength.
 * <p>
 * The name "ADFGVX" refers to the six letters (A, D, F, G, V, X) used as row and
 * column labels in the Polybius square. This cipher was designed to secure
 * communication and create complex, hard-to-break ciphertexts.
 * <p>
 * Learn more: <a href="https://en.wikipedia.org/wiki/ADFGVX_cipher">ADFGVX Cipher - Wikipedia</a>.
 * <p>
 * Example usage:
 * <pre>
 * ADFGVXCipher cipher = new ADFGVXCipher();
 * String encrypted = cipher.encrypt("attack at 1200am", "PRIVACY");
 * String decrypted = cipher.decrypt(encrypted, "PRIVACY");
 * </pre>
 *
 * @author bennybebo
 */
public class ADFGVXCipher {

    // Constants used in the Polybius square
    private static final char[] POLYBIUS_COORDINATES = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    // Maps for fast substitution lookups
    private static final Map<String, Character> COORDINATE_TO_CHAR_MAP = new HashMap<>();
    private static final Map<Character, String> CHAR_TO_COORDINATE_MAP = new HashMap<>();

    // Static block to initialize the lookup tables from the Polybius square
    static {
        for (int rowIndex = 0; rowIndex < POLYBIUS_SQUARE.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < POLYBIUS_SQUARE[rowIndex].length; columnIndex++) {
                String coordinatePair =
                    "" + POLYBIUS_COORDINATES[rowIndex] + POLYBIUS_COORDINATES[columnIndex];
                char squareChar = POLYBIUS_SQUARE[rowIndex][columnIndex];

                COORDINATE_TO_CHAR_MAP.put(coordinatePair, squareChar);
                CHAR_TO_COORDINATE_MAP.put(squareChar, coordinatePair);
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
        String sanitizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
        StringBuilder fractionatedTextBuilder = new StringBuilder();

        for (char currentChar : sanitizedPlaintext.toCharArray()) {
            fractionatedTextBuilder.append(CHAR_TO_COORDINATE_MAP.get(currentChar));
        }

        return performColumnarTransposition(fractionatedTextBuilder.toString(), key);
    }

    /**
     * Decrypts a given ciphertext using the ADFGVX cipher with the provided keyword.
     * Steps:
     * 1. Reverse the columnar transposition performed during encryption.
     * 2. Substitute each pair of ADFGVX letters with the corresponding plaintext letter.
     * The resulting text is the decrypted message.
     *
     * @param ciphertext The encrypted message.
     * @param key        The keyword used during encryption.
     * @return The decrypted plaintext message.
     */
    public String decrypt(String ciphertext, String key) {
        String fractionatedText = reverseColumnarTransposition(ciphertext, key);

        StringBuilder plaintextBuilder = new StringBuilder();
        for (int index = 0; index < fractionatedText.length(); index += 2) {
            String coordinatePair = fractionatedText.substring(index, index + 2);
            plaintextBuilder.append(COORDINATE_TO_CHAR_MAP.get(coordinatePair));
        }

        return plaintextBuilder.toString();
    }

    /**
     * Helper method: Performs columnar transposition during encryption
     *
     * @param text The fractionated text to be transposed
     * @param key  The keyword for columnar transposition
     * @return The transposed text
     */
    private String performColumnarTransposition(String text, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) text.length() / columnCount);

        char[][] transpositionTable = new char[rowCount][columnCount];
        for (char[] row : transpositionTable) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int rowIndex = index / columnCount;
            int columnIndex = index % columnCount;
            transpositionTable[rowIndex][columnIndex] = text.charAt(index);
        }

        StringBuilder ciphertextBuilder = new StringBuilder();
        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        for (char sortedKeyChar : sortedKeyCharacters) {
            int originalColumnIndex = key.indexOf(sortedKeyChar);
            for (char[] row : transpositionTable) {
                char cellValue = row[originalColumnIndex];
                if (cellValue != '_') {
                    ciphertextBuilder.append(cellValue);
                }
            }
        }

        return ciphertextBuilder.toString();
    }

    /**
     * Helper method: Reverses the columnar transposition during decryption
     *
     * @param ciphertext The transposed text to be reversed
     * @param key        The keyword used during encryption
     * @return The reversed text
     */
    private String reverseColumnarTransposition(String ciphertext, String key) {
        int columnCount = key.length();
        int rowCount = (int) Math.ceil((double) ciphertext.length() / columnCount);

        char[][] transpositionTable = new char[rowCount][columnCount];

        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        int ciphertextIndex = 0;
        for (char sortedKeyChar : sortedKeyCharacters) {
            int originalColumnIndex = key.indexOf(sortedKeyChar);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (ciphertextIndex < ciphertext.length()) {
                    transpositionTable[rowIndex][originalColumnIndex] =
                        ciphertext.charAt(ciphertextIndex++);
                } else {
                    transpositionTable[rowIndex][originalColumnIndex] = '_';
                }
            }
        }

        StringBuilder fractionatedTextBuilder = new StringBuilder();
        for (char[] row : transpositionTable) {
            for (char cellValue : row) {
                if (cellValue != '_') {
                    fractionatedTextBuilder.append(cellValue);
                }
            }
        }

        return fractionatedTextBuilder.toString();
    }
}