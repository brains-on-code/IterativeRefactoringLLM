package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * ADFGVX cipher implementation (fractionation + columnar transposition).
 */
public class AdfgvxCipher {

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
    private static final Map<String, Character> COORDINATE_TO_CHARACTER = new HashMap<>();

    // Map from character to coordinate pair (e.g. 'N' -> "AA")
    private static final Map<Character, String> CHARACTER_TO_COORDINATE = new HashMap<>();

    static {
        for (int rowIndex = 0; rowIndex < SUBSTITUTION_SQUARE.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < SUBSTITUTION_SQUARE[rowIndex].length; columnIndex++) {
                String coordinatePair =
                    "" + COORDINATE_LABELS[rowIndex] + COORDINATE_LABELS[columnIndex];
                char mappedCharacter = SUBSTITUTION_SQUARE[rowIndex][columnIndex];
                COORDINATE_TO_CHARACTER.put(coordinatePair, mappedCharacter);
                CHARACTER_TO_COORDINATE.put(mappedCharacter, coordinatePair);
            }
        }
    }

    public String encrypt(String plaintext, String key) {
        String normalizedPlaintext = plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");

        StringBuilder fractionatedPlaintext = new StringBuilder();
        for (char character : normalizedPlaintext.toCharArray()) {
            fractionatedPlaintext.append(CHARACTER_TO_COORDINATE.get(character));
        }

        return encryptColumnarTransposition(fractionatedPlaintext.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String unfractionatedText = decryptColumnarTransposition(ciphertext, key);

        StringBuilder plaintext = new StringBuilder();
        for (int index = 0; index < unfractionatedText.length(); index += 2) {
            String coordinatePair = unfractionatedText.substring(index, index + 2);
            plaintext.append(COORDINATE_TO_CHARACTER.get(coordinatePair));
        }

        return plaintext.toString();
    }

    private String encryptColumnarTransposition(String text, String key) {
        int rowCount = (int) Math.ceil((double) text.length() / key.length());
        int columnCount = key.length();
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
            int columnIndex = key.indexOf(sortedKeyCharacter);
            for (char[] row : transpositionGrid) {
                if (row[columnIndex] != '_') {
                    ciphertext.append(row[columnIndex]);
                }
            }
        }

        return ciphertext.toString();
    }

    private String decryptColumnarTransposition(String text, String key) {
        int rowCount = (int) Math.ceil((double) text.length() / key.length());
        int columnCount = key.length();
        char[][] transpositionGrid = new char[rowCount][columnCount];

        char[] sortedKeyCharacters = key.toCharArray();
        Arrays.sort(sortedKeyCharacters);

        int textIndex = 0;
        for (char sortedKeyCharacter : sortedKeyCharacters) {
            int columnIndex = key.indexOf(sortedKeyCharacter);
            for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
                if (textIndex < text.length()) {
                    transpositionGrid[rowIndex][columnIndex] = text.charAt(textIndex++);
                } else {
                    transpositionGrid[rowIndex][columnIndex] = '_';
                }
            }
        }

        StringBuilder plaintextCoordinates = new StringBuilder();
        for (char[] row : transpositionGrid) {
            for (char character : row) {
                if (character != '_') {
                    plaintextCoordinates.append(character);
                }
            }
        }

        return plaintextCoordinates.toString();
    }
}