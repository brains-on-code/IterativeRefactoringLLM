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
    private static final char[] POLYBIUS_LABELS = {'A', 'D', 'F', 'G', 'V', 'X'};
    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    // Maps for fast substitution lookups
    private static final Map<String, Character> COORDINATE_TO_CHARACTER = new HashMap<>();
    private static final Map<Character, String> CHARACTER_TO_COORDINATE = new HashMap<>();

    // Static block to initialize the lookup tables from the Polybius square
    static {
        for (int rowIndex = 0; rowIndex < POLYBIUS_SQUARE.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < POLYBIUS_SQUARE[rowIndex].length; columnIndex++) {
                String coordinatePair =
                    "" + POLYBIUS_LABELS[rowIndex] + POLYBIUS_LABELS[columnIndex];
                char squareCharacter = POLYBIUS_SQUARE[rowIndex][columnIndex];

                COORDINATE_TO_CHARACTER.put(coordinatePair, squareCharacter);
                CHARACTER_TO_COORDINATE.put(squareCharacter, coordinatePair);
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
        StringBuilder fractionatedText = new StringBuilder();

        for (char character : sanitizedPlaintext.toCharArray()) {
            fractionatedText.append(CHARACTER_TO_COORDINATE.get(character));
        }

        return performColumnarTransposition(fractionatedText.toString(), key);
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

        StringBuilder plaintext = new StringBuilder();
        for (int index = 0; index < fractionatedText.length(); index += 2) {
            String coordinatePair = fractionatedText.substring(index, index + 2);
            plaintext.append(COORDINATE_TO_CHARACTER.get(coordinatePair));
        }

        return plaintext.toString();
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

        char[][] transpositionGrid = new char[rowCount][columnCount];
        for (char[] row : transpositionGrid) {
            Arrays.fill(row, '_');
        }

        for (int index = 0; index < text.length(); index++) {
            int rowIndex = index / columnCount;
            int columnIndex = index % columnCount;
            transpositionGrid[rowIndex][columnIndex] = text.charAt(index);
        }

        StringBuilder ciphertext = new StringBuilder();
        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        for (char sortedKeyCharacter : sortedKeyCharacters) {
            int originalColumnIndex = key.indexOf(sortedKeyCharacter);
            for (char[] row : transpositionGrid) {
                char cellValue = row[originalColumnIndex];
                if (cellValue != '_') {
                    ciphertext.append(cellValue);
                }
            }
        }

        return ciphertext.toString();
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

        char[][] transpositionGrid = new char[rowCount][columnCount];

        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        int ciphertextIndex = 0;
        for (char sortedKeyCharacter : sortedKeyCharacters) {
            int originalColumnIndex = key.indexOf(sortedKeyCharacter);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (ciphertextIndex < ciphertext.length()) {
                    transpositionGrid[rowIndex][originalColumnIndex] =
                        ciphertext.charAt(ciphertextIndex++);
                } else {
                    transpositionGrid[rowIndex][originalColumnIndex] = '_';
                }
            }
        }

        StringBuilder fractionatedText = new StringBuilder();
        for (char[] row : transpositionGrid) {
            for (char cellValue : row) {
                if (cellValue != '_') {
                    fractionatedText.append(cellValue);
                }
            }
        }

        return fractionatedText.toString();
    }
}