package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ADFGVXCipher {

    private static final char PADDING_CHAR = '_';

    private static final char[] POLYBIUS_LETTERS = {'A', 'D', 'F', 'G', 'V', 'X'};

    private static final char[][] POLYBIUS_SQUARE = {
        {'N', 'A', '1', 'C', '3', 'H'},
        {'8', 'T', 'B', '2', 'O', 'M'},
        {'E', '5', 'W', 'R', 'P', 'D'},
        {'4', 'F', '6', 'G', '7', 'I'},
        {'9', 'J', '0', 'K', 'L', 'Q'},
        {'S', 'U', 'V', 'X', 'Y', 'Z'}
    };

    private static final Map<String, Character> POLYBIUS_MAP = new HashMap<>();
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

    public String encrypt(String plaintext, String key) {
        String normalizedText = normalizePlaintext(plaintext);
        StringBuilder fractionatedText = new StringBuilder();

        for (char c : normalizedText.toCharArray()) {
            String polybiusPair = REVERSE_POLYBIUS_MAP.get(c);
            if (polybiusPair != null) {
                fractionatedText.append(polybiusPair);
            }
        }

        return columnarTransposition(fractionatedText.toString(), key);
    }

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

    private String columnarTransposition(String text, String key) {
        int keyLength = key.length();
        int numRows = (int) Math.ceil((double) text.length() / keyLength);
        char[][] table = createFilledTable(numRows, keyLength, PADDING_CHAR);

        for (int i = 0; i < text.length(); i++) {
            int row = i / keyLength;
            int col = i % keyLength;
            table[row][col] = text.charAt(i);
        }

        StringBuilder ciphertext = new StringBuilder();
        int[] columnOrder = getColumnOrder(key);

        for (int columnIndex : columnOrder) {
            for (char[] row : table) {
                if (row[columnIndex] != PADDING_CHAR) {
                    ciphertext.append(row[columnIndex]);
                }
            }
        }

        return ciphertext.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int keyLength = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / keyLength);
        char[][] table = createFilledTable(numRows, keyLength, PADDING_CHAR);

        int[] columnOrder = getColumnOrder(key);
        int cipherIndex = 0;

        for (int columnIndex : columnOrder) {
            for (int row = 0; row < numRows; row++) {
                if (cipherIndex < ciphertext.length()) {
                    table[row][columnIndex] = ciphertext.charAt(cipherIndex++);
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

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private char[][] createFilledTable(int rows, int cols, char fillChar) {
        char[][] table = new char[rows][cols];
        for (char[] row : table) {
            Arrays.fill(row, fillChar);
        }
        return table;
    }

    private int[] getColumnOrder(String key) {
        int length = key.length();
        CharacterWithIndex[] keyChars = new CharacterWithIndex[length];

        for (int i = 0; i < length; i++) {
            keyChars[i] = new CharacterWithIndex(key.charAt(i), i);
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

    private static class CharacterWithIndex {
        final char character;
        final int index;

        CharacterWithIndex(char character, int index) {
            this.character = character;
            this.index = index;
        }
    }
}