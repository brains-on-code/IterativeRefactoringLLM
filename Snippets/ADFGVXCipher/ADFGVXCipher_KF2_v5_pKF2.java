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
        String normalizedPlaintext = normalizePlaintext(plaintext);
        StringBuilder fractionatedText = new StringBuilder();

        for (char character : normalizedPlaintext.toCharArray()) {
            fractionatedText.append(REVERSE_POLYBIUS_MAP.get(character));
        }

        return columnarTransposition(fractionatedText.toString(), key);
    }

    public String decrypt(String ciphertext, String key) {
        String fractionatedText = reverseColumnarTransposition(ciphertext, key);
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < fractionatedText.length(); i += 2) {
            String pair = fractionatedText.substring(i, i + 2);
            plaintext.append(POLYBIUS_MAP.get(pair));
        }

        return plaintext.toString();
    }

    private String normalizePlaintext(String plaintext) {
        return plaintext.toUpperCase().replaceAll("[^A-Z0-9]", "");
    }

    private String columnarTransposition(String text, String key) {
        int keyLength = key.length();
        int numRows = (int) Math.ceil((double) text.length() / keyLength);
        char[][] table = new char[numRows][keyLength];

        fillTableWithPadding(table, '_');
        fillTableRowWise(table, text);

        StringBuilder ciphertext = new StringBuilder();
        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        boolean[] usedColumns = new boolean[keyLength];

        for (char keyChar : sortedKey) {
            int column = findNextColumnIndex(key, keyChar, usedColumns);
            usedColumns[column] = true;

            for (char[] row : table) {
                if (row[column] != '_') {
                    ciphertext.append(row[column]);
                }
            }
        }

        return ciphertext.toString();
    }

    private String reverseColumnarTransposition(String ciphertext, String key) {
        int keyLength = key.length();
        int numRows = (int) Math.ceil((double) ciphertext.length() / keyLength);
        char[][] table = new char[numRows][keyLength];

        char[] sortedKey = key.toCharArray();
        Arrays.sort(sortedKey);

        int[] columnHeights = calculateColumnHeights(ciphertext.length(), keyLength);
        boolean[] usedColumns = new boolean[keyLength];

        int index = 0;
        for (char keyChar : sortedKey) {
            int column = findNextColumnIndex(key, keyChar, usedColumns);
            usedColumns[column] = true;

            for (int row = 0; row < columnHeights[column]; row++) {
                table[row][column] = ciphertext.charAt(index++);
            }
        }

        StringBuilder fractionatedText = new StringBuilder();
        for (char[] row : table) {
            for (char cell : row) {
                if (cell != 0 && cell != '_') {
                    fractionatedText.append(cell);
                }
            }
        }

        return fractionatedText.toString();
    }

    private int[] calculateColumnHeights(int textLength, int keyLength) {
        int[] columnHeights = new int[keyLength];
        int baseHeight = textLength / keyLength;
        int extraChars = textLength % keyLength;

        Arrays.fill(columnHeights, baseHeight);
        for (int i = 0; i < extraChars; i++) {
            columnHeights[i]++;
        }

        return columnHeights;
    }

    private int findNextColumnIndex(String key, char keyChar, boolean[] usedColumns) {
        int index = -1;
        while (true) {
            index = key.indexOf(keyChar, index + 1);
            if (index == -1 || !usedColumns[index]) {
                break;
            }
        }
        return index;
    }

    private void fillTableWithPadding(char[][] table, char paddingChar) {
        for (char[] row : table) {
            Arrays.fill(row, paddingChar);
        }
    }

    private void fillTableRowWise(char[][] table, String text) {
        int keyLength = table[0].length;
        for (int i = 0; i < text.length(); i++) {
            int row = i / keyLength;
            int col = i % keyLength;
            table[row][col] = text.charAt(i);
        }
    }
}