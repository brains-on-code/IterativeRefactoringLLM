package com.thealgorithms.ciphers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ADFGVXCipher {

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

    private static final char PADDING_CHAR = '_';

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
        String sanitized = sanitizePlaintext(plaintext);
        String fractionatedText = fractionateText(sanitized);
        return columnarTransposition(fractionatedText, key);
    }

    public String decrypt(String ciphertext, String key) {
        String fractionatedText = reverseColumnarTransposition(ciphertext, key);
        return defractionateText(fractionatedText);
    }

    private String sanitizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private String fractionateText(String text) {
        StringBuilder fractionatedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            fractionatedText.append(REVERSE_POLYBIUS_MAP.get(c));
        }
        return fractionatedText.toString();
    }

    private String defractionateText(String fractionatedText) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < fractionatedText.length(); i += 2) {
            String pair = fractionatedText.substring(i, i + 2);
            plaintext.append(POLYBIUS_MAP.get(pair));
        }
        return plaintext.toString();
    }

    private String columnarTransposition(String text, String key) {
        int numCols = key.length();
        int numRows = (int) Math.ceil((double) text.length() / numCols);

        char[][] table = createFilledTable(numRows, numCols, PADDING_CHAR);
        fillTableRowWise(table, text);

        StringBuilder ciphertext = new StringBuilder();
        int[] columnOrder = getColumnOrder(key);

        for (int colIndex : columnOrder) {
            appendColumn(table, colIndex, ciphertext);
        }

        return ciphertext.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int numCols = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / numCols);

        char[][] table = new char[numRows][numCols];
        int[] columnOrder = getColumnOrder(key);

        int index = 0;
        for (int colIndex : columnOrder) {
            for (int row = 0; row < numRows; row++) {
                if (index < ciphertext.length()) {
                    table[row][colIndex] = ciphertext.charAt(index++);
                } else {
                    table[row][colIndex] = PADDING_CHAR;
                }
            }
        }

        return readTableRowWise(table, PADDING_CHAR);
    }

    private char[][] createFilledTable(int numRows, int numCols, char fillChar) {
        char[][] table = new char[numRows][numCols];
        for (char[] row : table) {
            Arrays.fill(row, fillChar);
        }
        return table;
    }

    private void fillTableRowWise(char[][] table, String text) {
        int numCols = table[0].length;
        for (int i = 0; i < text.length(); i++) {
            int row = i / numCols;
            int col = i % numCols;
            table[row][col] = text.charAt(i);
        }
    }

    private int[] getColumnOrder(String key) {
        int length = key.length();
        KeyChar[] keyChars = new KeyChar[length];

        for (int i = 0; i < length; i++) {
            keyChars[i] = new KeyChar(key.charAt(i), i);
        }

        Arrays.sort(keyChars, (a, b) -> {
            int cmp = Character.compare(a.character, b.character);
            return (cmp != 0) ? cmp : Integer.compare(a.index, b.index);
        });

        int[] order = new int[length];
        for (int i = 0; i < length; i++) {
            order[i] = keyChars[i].index;
        }
        return order;
    }

    private void appendColumn(char[][] table, int column, StringBuilder builder) {
        for (char[] row : table) {
            if (row[column] != PADDING_CHAR) {
                builder.append(row[column]);
            }
        }
    }

    private String readTableRowWise(char[][] table, char paddingChar) {
        StringBuilder result = new StringBuilder();
        for (char[] row : table) {
            for (char cell : row) {
                if (cell != paddingChar) {
                    result.append(cell);
                }
            }
        }
        return result.toString();
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