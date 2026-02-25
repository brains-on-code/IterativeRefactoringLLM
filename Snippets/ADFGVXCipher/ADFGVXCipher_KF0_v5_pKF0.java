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
                char rowLabel = POLYBIUS_LETTERS[row];
                char colLabel = POLYBIUS_LETTERS[col];
                char value = POLYBIUS_SQUARE[row][col];

                String key = "" + rowLabel + colLabel;
                POLYBIUS_MAP.put(key, value);
                REVERSE_POLYBIUS_MAP.put(value, key);
            }
        }
    }

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
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) text.length() / numColumns);

        char[][] table = createFilledTable(numRows, numColumns, PADDING_CHAR);
        fillTableRowWise(table, text, numColumns);

        int[] columnOrder = buildColumnOrder(key);

        StringBuilder ciphertext = new StringBuilder();
        for (int columnIndex : columnOrder) {
            appendColumn(table, columnIndex, ciphertext);
        }

        return ciphertext.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int numColumns = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numColumns);

        char[][] table = new char[numRows][numColumns];
        int[] columnOrder = buildColumnOrder(key);

        int index = 0;
        for (int columnIndex : columnOrder) {
            for (int row = 0; row < numRows; row++) {
                table[row][columnIndex] =
                    (index < ciphertext.length()) ? ciphertext.charAt(index++) : PADDING_CHAR;
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

    private int[] buildColumnOrder(String key) {
        int length = key.length();
        KeyChar[] keyChars = new KeyChar[length];

        for (int i = 0; i < length; i++) {
            keyChars[i] = new KeyChar(key.charAt(i), i);
        }

        Arrays.sort(
            keyChars,
            (a, b) -> {
                int cmp = Character.compare(a.character, b.character);
                return (cmp != 0) ? cmp : Integer.compare(a.index, b.index);
            }
        );

        int[] order = new int[length];
        for (int i = 0; i < length; i++) {
            order[i] = keyChars[i].index;
        }

        return order;
    }

    private static class KeyChar {
        final char character;
        final int index;

        KeyChar(char character, int index) {
            this.character = character;
            this.index = index;
        }
    }
}